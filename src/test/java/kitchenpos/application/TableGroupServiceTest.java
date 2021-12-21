package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import kitchenpos.application.testfixtures.OrderTestFixtures;
import kitchenpos.application.testfixtures.TableGroupTestFixtures;
import kitchenpos.application.testfixtures.TableTestFixtures;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dao.TableGroupDao;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TableGroupServiceTest {

    @Mock
    private TableGroupDao tableGroupDao;

    @Mock
    private OrderTableDao orderTableDao;

    @Mock
    private OrderDao orderDao;

    @InjectMocks
    private TableGroupService tableGroupService;

    @DisplayName("테이블 그룹을 등록할 수 있다.")
    @Test
    void create() {
        //given
        LocalDateTime createdDate = LocalDateTime.now();
        List<OrderTable> orderTables = Arrays.asList(
            new OrderTable(1L, 6, true),
            new OrderTable(2L, 3, true));
        TableGroup tableGroup = new TableGroup(createdDate, orderTables);

        TableTestFixtures.주문테이블_특정_리스트_조회_모킹(orderTableDao, orderTables);
        TableGroupTestFixtures.테이블그룹_저장_결과_모킹(tableGroupDao, tableGroup);

        //when
        TableGroup savedTableGroup = tableGroupService.create(tableGroup);

        //then
        assertThat(savedTableGroup.getCreatedDate()).isEqualTo(tableGroup.getCreatedDate());
        assertThat(savedTableGroup.getOrderTables().size()).isEqualTo(
            tableGroup.getOrderTables().size());
    }

    @DisplayName("주문 테이블 리스트는 2개 이상이어야 한다.")
    @Test
    void create_exception1() {
        //given
        LocalDateTime createdDate = LocalDateTime.now();
        List<OrderTable> orderTables = Arrays.asList(new OrderTable(1L, 6, true));
        TableGroup tableGroup = new TableGroup(createdDate, orderTables);

        //when, then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("모든 주문 테이블은 빈 테이블이어야 한다.")
    @Test
    void create_exception2() {
        //given
        LocalDateTime createdDate = LocalDateTime.now();
        List<OrderTable> orderTables = Arrays.asList(
            new OrderTable(1L, 6, true),
            new OrderTable(2L, 3, false));
        TableGroup tableGroup = new TableGroup(createdDate, orderTables);

        TableTestFixtures.주문테이블_특정_리스트_조회_모킹(orderTableDao, orderTables);

        //when, then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("테이블 그룹에 속해있는 주문테이블이 포함되면 안된다.")
    @Test
    void create_exception3() {
        //given
        LocalDateTime createdDate = LocalDateTime.now();
        List<OrderTable> orderTables = Arrays.asList(
            new OrderTable(1L, 6, true),
            new OrderTable(2L, 5L, 3, true));
        TableGroup tableGroup = new TableGroup(createdDate, orderTables);

        TableTestFixtures.주문테이블_특정_리스트_조회_모킹(orderTableDao, orderTables);

        //when, then
        assertThatThrownBy(() -> tableGroupService.create(tableGroup))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("테이블 그룹을 해제할 수 있다.")
    @Test
    void ungroup() {
        //given
        LocalDateTime createdDate = LocalDateTime.now();
        List<OrderTable> orderTables = Arrays.asList(
            new OrderTable(1L, 1L, 6, true),
            new OrderTable(2L, 1L, 3, true));
        TableGroup tableGroup = new TableGroup(1L, createdDate, orderTables);
        TableTestFixtures.특정_테이블_그룹에_속하는_테이블리스트_조회_모킹(orderTableDao, orderTables);
        OrderTestFixtures.특정_테이블들이_특정상태인지_조회_모킹(orderDao, false);

        //when
        tableGroupService.ungroup(tableGroup.getId());

        //then
        List<OrderTable> ungroupedOrderTables = orderTableDao.findAllByTableGroupId(
            tableGroup.getId());
        ungroupedOrderTables.stream()
            .forEach(table -> assertThat(table.getTableGroupId()).isNull());
    }

    @DisplayName("조리, 식사 상태의 주문 테이블이 존재하는 경우 테이블 그룹을 해제할 수 없다.")
    @Test
    void ungroup_exception() {
        //given
        LocalDateTime createdDate = LocalDateTime.now();
        List<OrderTable> orderTables = Arrays.asList(
            new OrderTable(1L, 1L, 6, true),
            new OrderTable(2L, 1L, 3, true));
        TableGroup tableGroup = new TableGroup(1L, createdDate, orderTables);

        TableTestFixtures.특정_테이블_그룹에_속하는_테이블리스트_조회_모킹(orderTableDao, orderTables);
        OrderTestFixtures.특정_테이블들이_특정상태인지_조회_모킹(orderDao, true);

        //when, then
        assertThatThrownBy(() -> tableGroupService.ungroup(tableGroup.getId()))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
