package starbuckbuck.coffeeorderkiosk.domain.order.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import starbuckbuck.coffeeorderkiosk.domain.order.business.OrderService;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public void createOrder(OrderCreateRequest request) {
        orderService.createOrder(request);
    }
}
