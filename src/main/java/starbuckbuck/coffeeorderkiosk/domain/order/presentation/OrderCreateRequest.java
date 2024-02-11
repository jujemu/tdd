package starbuckbuck.coffeeorderkiosk.domain.order.presentation;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class OrderCreateRequest {

    private Map<String, Integer> productNumberCounter;

    public OrderCreateRequest(Map<String, Integer> productNumbers) {
        this.productNumberCounter = productNumbers;
    }
}
