package starbuckbuck.coffeeorderkiosk.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import starbuckbuck.coffeeorderkiosk.domain.orderproduct.OrderProduct;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static starbuckbuck.coffeeorderkiosk.domain.order.OrderStatus.INIT;

@ActiveProfiles("test")
@SpringBootTest
class OrderTest {

    @DisplayName("상품 목록에서 상품을 선택하여 주문할 수 있다. 생성된 주문은 INIT 상태와 총가격을 갖는다.")
    @Test
    void createOrder() throws Exception {
        //given
        LocalDateTime create = LocalDateTime.of(2023, 7, 1, 14, 0);
        List<Product> products = List.of(
                createProduct("001", "아메리카노", 5000),
                createProduct("002", "카페라떼", 6000));

        //when
        Order order = new Order(products, create);

        //then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(INIT);
        assertThat(order.calculateTotalPrice()).isEqualTo(11000);

        List<Product> orderedProducts = order.getOrderProducts().stream()
                .map(OrderProduct::getProduct).toList();
        assertThat(orderedProducts).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 5000),
                        tuple("002", 6000)
                );
    }
    
    @DisplayName("운영시간 외에는 주문할 수 없다.")
    @Test
    void shouldThrowExceptionWhenOutsideBusinessHours() throws Exception {
        //given
        LocalDateTime create = LocalDateTime.of(2023, 7, 1, 23, 0);
        List<Product> products = List.of(
                createProduct("001", "아메리카노", 5000),
                createProduct("002", "카페라떼", 6000));

        //when //then
        assertThatThrownBy(() -> new Order(products, create))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("운영시간이 아닙니다.");
    }
    
    @DisplayName("주문에는 최소 한 개 이상의 상품을 골라야한다.")
    @Test
    void shouldThrowExceptionWithNoItems() throws Exception {
        //given
        LocalDateTime create = LocalDateTime.of(2023, 7, 1, 15, 0);
        List<Product> products = Collections.emptyList();

        //when //then
        assertThatThrownBy(() -> new Order(products, create))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("한 개 이상 주문해야합니다.");
    }

    private Product createProduct(String productNumber, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(ProductType.HANDMADE)
                .name(name)
                .price(price)
                .build();
    }
}