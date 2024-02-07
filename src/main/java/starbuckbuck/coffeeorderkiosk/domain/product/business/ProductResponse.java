package starbuckbuck.coffeeorderkiosk.domain.product.business;

import lombok.Builder;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductSellingStatus;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductType;

public class ProductResponse {

    private Long id;
    private String productNumber;
    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private Integer price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, Integer price) {
        this.id = id;
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productNumber(builder().productNumber)
                .sellingStatus(product.getSellingStatus())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
