package starbuckbuck.coffeeorderkiosk.domain.order.business;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import starbuckbuck.coffeeorderkiosk.domain.order.OrderStatus;
import starbuckbuck.coffeeorderkiosk.domain.product.business.ProductResponse;

import java.time.LocalTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderResponse {

    private Long orderId;
    private OrderStatus orderStatus;
    private LocalTime registeredDateTime;
    private Integer totalPrice;
    private List<ProductResponse> productResponses;

    public OrderResponse(Long orderId, OrderStatus orderStatus, LocalTime registeredDateTime, Integer totalPrice, List<ProductResponse> orderProducts1) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.registeredDateTime = registeredDateTime;
        this.totalPrice = totalPrice;
        this.productResponses = orderProducts1;
    }
}
