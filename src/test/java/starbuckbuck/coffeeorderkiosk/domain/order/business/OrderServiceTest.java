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
import starbuckbuck.coffeeorderkiosk.domain.stock.Stock;
import starbuckbuck.coffeeorderkiosk.domain.stock.repository.StockRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static starbuckbuck.coffeeorderkiosk.domain.product.ProductType.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockRepository stockRepository;

    @DisplayName("상품 목록을 받아 주문을 생성하면 INIT 상태를 가진 주문이 생성된다.")
    @Test
    void createOrder() throws Exception {
        //given
        Product product1 = createProduct("001", BAKERY, "아메리카노", 5000);
        Product product2 = createProduct("002", BOTTLE, "카페라떼", 6000);
        Product product3 = createProduct("003", HANDMADE, "팥빙수", 8000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = new Stock("001", 2);
        Stock stock2 = new Stock("002", 2);
        stockRepository.saveAll(List.of(stock1, stock2));

        Map<String, Integer> productNumberCounter = new HashMap<>();
        productNumberCounter.put("001", 2);
        productNumberCounter.put("003", 1);
        OrderCreateRequest request = new OrderCreateRequest(productNumberCounter);

        //when
        LocalDateTime create = LocalDateTime.of(2023, 7, 1, 15, 0);
        OrderResponse response = orderService.createOrder(request, create);

        //then
        assertThat(response.getOrderId()).isNotNull();
        assertThat(response)
                .extracting("registeredDateTime", "totalPrice")
                .contains(create, 18000);
        assertThat(response.getProductResponses()).hasSize(3)
                .extracting("productNumber", "name")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노"),
                        tuple("001", "아메리카노"),
                        tuple("003", "팥빙수")
                );

        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 0),
                        tuple("002", 2)
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