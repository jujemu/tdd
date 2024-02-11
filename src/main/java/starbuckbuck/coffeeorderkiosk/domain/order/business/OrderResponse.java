package starbuckbuck.coffeeorderkiosk.domain.order.business;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import starbuckbuck.coffeeorderkiosk.domain.order.Order;
import starbuckbuck.coffeeorderkiosk.domain.order.OrderStatus;
import starbuckbuck.coffeeorderkiosk.domain.product.business.ProductResponse;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderResponse {

    private Long orderId;
    private OrderStatus orderStatus;
    private LocalDateTime registeredDateTime;
    private Integer totalPrice;
    private List<ProductResponse> productResponses;

    public OrderResponse(Order order, LocalDateTime registeredDateTime) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.totalPrice = order.calculateTotalPrice();
        this.productResponses = order.getOrderProducts().stream()
                .map(orderProduct ->
                        ProductResponse.of(orderProduct.getProduct()))
                .toList();
        this.registeredDateTime = registeredDateTime;
    }
}
