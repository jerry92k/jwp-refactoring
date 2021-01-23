package kitchenpos.ui.order;

import kitchenpos.application.order.OrderService;
import kitchenpos.dto.order.OrderRequest;
import kitchenpos.dto.order.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class OrderRestController {
	private final OrderService orderService;

	public OrderRestController(final OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/api/orders")
	public ResponseEntity<OrderResponse> createOrder(@RequestBody final OrderRequest orderRequest) {
		final OrderResponse created = orderService.createOrder(orderRequest);
		final URI uri = URI.create("/api/orders/" + created.getId());
		return ResponseEntity.created(uri).body(created);
	}

	@GetMapping("/api/orders")
	public ResponseEntity<List<OrderResponse>> listOrders() {
		return ResponseEntity.ok().body(orderService.listOrders());
	}

	@PutMapping("/api/orders/{orderId}/order-status")
	public ResponseEntity<OrderResponse> changeOrderStatus(@PathVariable final Long orderId, @RequestBody final OrderRequest orders) {
		return ResponseEntity.ok(orderService.changeOrderStatus(orderId, orders));
	}
}
