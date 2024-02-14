package starbuckbuck.coffeeorderkiosk.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductType;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class StockManagerTest {

    @Autowired
    StockManager stockManager;

    @DisplayName("병과 베이커리만 재고에서 관리한다.")
    @Test
    void isStockManaged() throws Exception {
        //given
        Product product = createProduct("001", ProductType.BOTTLE);

        //when
        boolean stockManaged = stockManager.isStockManaged(product);

        //then
        assertThat(stockManaged).isTrue();
    }

    @DisplayName("병과 베이커리가 아니면 재고에서 관리하지 않는다.")
    @Test
    void throwExceptionWhenNotStockManaged() throws Exception {
        //given
        Product product = createProduct("002", ProductType.HANDMADE);

        //when
        boolean stockManaged = stockManager.isStockManaged(product);

        //then
        assertThat(stockManaged).isFalse();
    }

    private Product createProduct(String productNumber, ProductType productType) {
        return Product.builder()
                .productNumber(productNumber)
                .type(productType)
                .name("test")
                .price(5000)
                .build();
    }
}