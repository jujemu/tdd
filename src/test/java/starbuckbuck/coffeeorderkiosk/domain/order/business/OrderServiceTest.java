package starbuckbuck.coffeeorderkiosk.domain.order.business;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import starbuckbuck.coffeeorderkiosk.domain.order.presentation.OrderCreateRequest;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductType;
import starbuckbuck.coffeeorderkiosk.domain.product.persistence.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static starbuckbuck.coffeeorderkiosk.domain.product.ProductType.HANDMADE;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @DisplayName("상품 목록을 받아 주문을 생성하면 INIT 상태를 가진 주문이 생성된다.")
    @Test
    void createOrder() throws Exception {
        //given
        Product product1 = createProduct("001", HANDMADE, "아메리카노", 5000);
        Product product2 = createProduct("002", HANDMADE, "카페라떼", 6000);
        Product product3 = createProduct("003", HANDMADE, "팥빙수", 8000);
        productRepository.saveAll(List.of(product1, product2, product3));

        List<String> productNumbers = List.of("001", "003");
        OrderCreateRequest request = new OrderCreateRequest(productNumbers);

        //when
        OrderResponse response = orderService.createOrder(request);

        //then
        assertThat(response.getOrderId()).isNotNull();
        assertThat(response.getTotalPrice()).isEqualTo(18000);
        assertThat(response.getProductResponses()).hasSize(3)
                .extracting("productNumber", "name")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노"),
                        tuple("001", "아메리카노"),
                        tuple("003", "팥빙수")
                );
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