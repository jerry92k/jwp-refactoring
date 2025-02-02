package kitchenpos.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.order.exception.CompleteOrderChangeStateException;
import java.util.Arrays;
import java.util.List;

import kitchenpos.menu.MenuTestFixtures;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import kitchenpos.common.vo.Quantity;

class OrderTest {

    @DisplayName("주문 생성")
    @Test
    void constructor() {
        //given
        Long orderTableId = 1L;
        List<OrderLineItem> orderLineItems = Arrays.asList(
            new OrderLineItem(MenuTestFixtures.서비스군만두.getId(), new Quantity(5L))
        );

        //when
        Order order = new Order(orderTableId, orderLineItems);

        //then
        assertThat(order.getOrderTableId()).isEqualTo(orderTableId);
    }

    @DisplayName("주문 상태 변경")
    @Test
    void changeOrderStatus() {
        //given
        Long orderTableId = 1L;
        List<OrderLineItem> orderLineItems = Arrays.asList(
            new OrderLineItem(MenuTestFixtures.서비스군만두.getId(), new Quantity(5L))
        );
        Order order = new Order(orderTableId, orderLineItems);

        //when
        order.changeOrderStatus(OrderStatus.MEAL);

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.MEAL);
    }

    @DisplayName("계산완료 상태에서는 주문상태를 변경할 수 없다.")
    @Test
    void changeOrderStatus_exception() {
        //given
        Long orderTableId = 1L;
        List<OrderLineItem> orderLineItems = Arrays.asList(
            new OrderLineItem(MenuTestFixtures.서비스군만두.getId(), new Quantity(5L))
        );
        Order order = new Order(orderTableId, orderLineItems);
        order.changeOrderStatus(OrderStatus.COMPLETION);

        //when, then
        assertThatThrownBy(() -> order.changeOrderStatus(OrderStatus.MEAL))
            .isInstanceOf(CompleteOrderChangeStateException.class);
    }
}
