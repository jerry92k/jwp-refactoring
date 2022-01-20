package kitchenpos.ordertable.domain;

import kitchenpos.tablegroup.event.GroupEvent;
import kitchenpos.tablegroup.event.GroupInfo;
import kitchenpos.ordertable.exception.DuplicateTablesException;
import kitchenpos.ordertable.exception.IllegalGroupingTableStateException;
import kitchenpos.ordertable.exception.NotEnoughTablesException;
import kitchenpos.tablegroup.event.UngroupEvent;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class GroupingEventListener {

    private static final int MIN_NUMBER_OF_TABLES_IN_GROUP = 2;

    private final OrderTableRepository orderTableRepository;

    public GroupingEventListener(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    // AFTER_COMMIT 으로 처리할 경우 별도의 트렌젝셔널 만들어도 됨.
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleGroupTables(GroupEvent groupEvent) {
        GroupInfo groupInfo = groupEvent.getGroupInfo();
        List<Long> orderTableIds = groupInfo.getOrderTableIds();
        List<OrderTable> orderTables = orderTableRepository.findByIdIn(orderTableIds);
        validateAtLeastTwoDistinctTables(orderTables, orderTableIds);
        validateGroupingCondition(orderTables);

        groupTables(orderTables, groupInfo.getTableGroupId());
    }

    protected void groupTables(List<OrderTable> orderTables, Long tableGroupId) {
        orderTables.stream()
            .forEach(orderTable -> orderTable.groupIn(tableGroupId));
    }

    private void validateGroupingCondition(List<OrderTable> orderTables) {
        for (OrderTable orderTable : orderTables) {
            validateEmptyTable(orderTable);
            validateNotInAnyGroup(orderTable);
        }
    }

    private void validateAtLeastTwoDistinctTables(List<OrderTable> orderTables,
        List<Long> orderTableIds) {

        if (orderTables.size() != orderTableIds.size()) {
            throw new DuplicateTablesException();
        }

        if (orderTables.size() < MIN_NUMBER_OF_TABLES_IN_GROUP) {
            throw new NotEnoughTablesException();
        }
    }

    private void validateEmptyTable(OrderTable orderTable) {
        if (!orderTable.isOrderClose()) {
            throw new IllegalGroupingTableStateException();
        }
    }

    private void validateNotInAnyGroup(OrderTable orderTable) {
        if (orderTable.hasGroup()) {
            throw new IllegalGroupingTableStateException();
        }
    }

    @EventListener
    public void handleUnGroupTables(UngroupEvent ungroupEvent) {
        Long tableGroupId = ungroupEvent.getTableGroupId();
        ungroupTables(tableGroupId);
    }

    protected void ungroupTables(Long tableGroupId) {
        List<OrderTable> orderTables = orderTableRepository.findByTableGroupId(tableGroupId);
        orderTables.stream()
            .forEach(OrderTable::unGroup);
    }
}
