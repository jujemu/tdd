package starbuckbuck.coffeeorderkiosk.domain.order.presentation;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateRequest {

    private List<String> productNumbers;

    public OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
