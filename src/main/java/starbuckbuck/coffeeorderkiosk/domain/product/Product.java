package starbuckbuck.coffeeorderkiosk.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import starbuckbuck.coffeeorderkiosk.domain.BaseEntity;

@Getter
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productNumber;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;
    private String name;
    private Integer price;

    public Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, Integer price) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }
}
