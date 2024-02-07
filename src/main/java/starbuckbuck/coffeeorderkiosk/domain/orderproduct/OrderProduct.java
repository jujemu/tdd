package starbuckbuck.coffeeorderkiosk.domain.orderproduct;

import jakarta.persistence.*;
import lombok.Getter;
import starbuckbuck.coffeeorderkiosk.domain.order.Orders;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;

@Getter
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
