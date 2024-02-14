package starbuckbuck.coffeeorderkiosk.domain.service;

import org.springframework.stereotype.Component;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductType;

import java.util.List;

import static starbuckbuck.coffeeorderkiosk.domain.product.ProductType.BAKERY;
import static starbuckbuck.coffeeorderkiosk.domain.product.ProductType.BOTTLE;

@Component
public class StockManager {

    public boolean isStockManaged(Product product) {
        return List.of(BAKERY, BOTTLE).contains(product.getType());
    }
}
