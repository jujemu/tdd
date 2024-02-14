package starbuckbuck.coffeeorderkiosk.domain.stock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockTest {

    @DisplayName("주문한 수량만큼 재고에서 꺼낸다.")
    @Test
    void removeQuantityFromStockWhenOrderCreated() throws Exception {
        //given
        String productNumber = "001";
        int quantity = 10;
        Stock stock = new Stock(productNumber, quantity);

        //when
        int orderedQuantity = 5;
        stock.removeFromStock(orderedQuantity);

        //then
        assertThat(stock.getQuantity()).isEqualTo(5);
    }

    @DisplayName("재고보다 많은 수량을 주문할 수 없다.")
    @Test
    void throwExceptionWhenOrderedQuantityMoreThanStock() throws Exception {
        //given
        String productNumber = "001";
        int quantity = 10;
        Stock stock = new Stock(productNumber, quantity);

        //when // then
        int orderedQuantity = 20;
        Assertions.assertThatThrownBy(() ->
                stock.removeFromStock(orderedQuantity)
                ).isInstanceOf(IllegalArgumentException.class).hasMessage("재고보다 많은 주문 수량입니다.");
    }

    private Product createProduct(String productNumber, ProductType productType, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(productType)
                .name(name)
                .price(price)
                .build();
    }
}