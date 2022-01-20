package kitchenpos.tablegroup.dto;

import java.util.List;

public class TableGroupRequest {

    private List<TableId> orderTables;

    private TableGroupRequest() {
    }

    public TableGroupRequest(List<TableId> orderTables) {
        this.orderTables = orderTables;
    }

    public List<TableId> getOrderTables() {
        return orderTables;
    }
}
