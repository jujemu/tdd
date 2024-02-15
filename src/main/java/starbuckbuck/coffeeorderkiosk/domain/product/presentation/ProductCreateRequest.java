package starbuckbuck.coffeeorderkiosk.domain.product.presentation;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductSellingStatus;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    private String name;
    private Integer price;
    private ProductType type;
    private ProductSellingStatus sellingStatus;

    public ProductCreateRequest(String name, Integer price, ProductType type, ProductSellingStatus sellingStatus) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.sellingStatus = sellingStatus;
    }

    public Product toEntity(String productNumber) {
        return Product.builder()
                .productNumber(productNumber)
                .name(name)
                .price(price)
                .type(type)
                .sellingStatus(sellingStatus)
                .build();
    }
}
