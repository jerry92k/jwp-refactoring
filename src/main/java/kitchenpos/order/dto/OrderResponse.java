package kitchenpos.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.ordertable.dto.OrderTableResponse;

public class OrderResponse {

    private Long id;
    private OrderTableResponse orderTable;
    private OrderStatus orderStatus;
    private LocalDateTime orderedTime;
    private List<OrderLineItemResponse> orderLineItems;

    private OrderResponse() {
    }

    public OrderResponse(Long id, OrderTableResponse orderTable,
        OrderStatus orderStatus, java.time.LocalDateTime orderedTime,
        List<OrderLineItemResponse> orderLineItems) {
        this.id = id;
        this.orderTable = orderTable;
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
        this.orderLineItems = orderLineItems;
    }

    public static List<OrderResponse> fromList(List<Order> orders) {
        return orders.stream()
            .map(OrderResponse::from)
            .collect(Collectors.toList());
    }

    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(),
            OrderTableResponse.from(order.getOrderTable()),
            order.getOrderStatus(), order.getCreatedDate(),
            OrderLineItemResponse.fromList(order.getOrderLineItemList()));
    }

    public Long getId() {
        return id;
    }

    public OrderTableResponse getOrderTable() {
        return orderTable;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public List<OrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderResponse that = (OrderResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(
            getOrderTable(), that.getOrderTable()) && getOrderStatus() == that.getOrderStatus()
            && Objects.equals(getOrderedTime(), that.getOrderedTime())
            && Objects.equals(getOrderLineItems(), that.getOrderLineItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderTable(), getOrderStatus(), getOrderedTime(),
            getOrderLineItems());
    }
}
