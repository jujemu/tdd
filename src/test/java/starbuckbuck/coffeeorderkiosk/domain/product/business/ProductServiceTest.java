package starbuckbuck.coffeeorderkiosk.domain.product.business;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductSellingStatus;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductType;
import starbuckbuck.coffeeorderkiosk.domain.product.persistence.ProductRepository;
import starbuckbuck.coffeeorderkiosk.domain.product.presentation.ProductCreateRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static starbuckbuck.coffeeorderkiosk.domain.product.ProductSellingStatus.SELLING;
import static starbuckbuck.coffeeorderkiosk.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @DisplayName("관리자는 타입과 상태, 이름, 가격으로 상품을 등록할 수 있다.")
    @Test
    void createProduct() throws Exception {
        //given
        Product product1 = createProduct("001", HANDMADE, "아메리카노", 5000);
        Product product2 = createProduct("002", HANDMADE, "카푸치노", 6000);
        productRepository.saveAll(List.of(product1, product2));

        ProductCreateRequest request = new ProductCreateRequest("아사이", 6000, HANDMADE, SELLING);

        //when
        ProductResponse response = productService.createProduct(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .contains("003", "아사이", 6000, HANDMADE, SELLING);
    }

    @DisplayName("어떤 상품도 등록되어 있지 않으면, 상품 번호는 001 이다.")
    @Test
    void createProductWhenNoProduct() throws Exception {
        //given
        ProductCreateRequest request = new ProductCreateRequest("아사이", 6000, HANDMADE, SELLING);

        //when
        ProductResponse response = productService.createProduct(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
                .extracting("productNumber", "name", "price", "sellingStatus", "type")
                .contains("001", "아사이", 6000, HANDMADE, SELLING);
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