package kitchenpos.order.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {

    @ExceptionHandler(DuplicateOrderLineItemsException.class)
    public ResponseEntity handleDuplicateOrderLineItemsException(
        DuplicateOrderLineItemsException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ClosedTableOrderException.class)
    public ResponseEntity handleClosedTableOrderException(
        ClosedTableOrderException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(CompleteOrderChangeStateException.class)
    public ResponseEntity handleCompleteOrderChangeStateException(
        CompleteOrderChangeStateException e) {
        return ResponseEntity.badRequest().build();
    }
}
