package starbuckbuck.coffeeorderkiosk.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductSellingStatus {

    SELLING("판매중"),
    PAUSE("판매 보류"),
    STOP("판매 중지");

    private final String text;

    public static List<ProductSellingStatus> forDisplayInKiosk() {
        return List.of(SELLING, PAUSE);
    }
}
