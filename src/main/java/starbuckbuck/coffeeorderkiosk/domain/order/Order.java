package starbuckbuck.coffeeorderkiosk.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import starbuckbuck.coffeeorderkiosk.domain.BaseEntity;
import starbuckbuck.coffeeorderkiosk.domain.orderproduct.OrderProduct;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderProduct> orderProducts;

    public Order(List<Product> products, LocalDateTime createDateTime) {
        LocalTime createTime = createDateTime.toLocalTime();
        LocalTime open = LocalTime.of(8, 0);
        LocalTime closed = LocalTime.of(22, 0);
        if (createTime.isBefore(open) || createTime.isAfter(closed)) {
            throw new IllegalArgumentException("운영시간이 아닙니다.");
        }
        this.registeredDateTime = createDateTime;
        this.orderStatus = OrderStatus.INIT;

        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("한 개 이상 주문해야합니다.");
        }

        this.orderProducts = products.stream()
                .map(product -> new OrderProduct(this, product))
                .toList();
    }

    public Integer calculateTotalPrice() {
        return this.orderProducts.stream()
                .mapToInt(orderProducts ->
                        orderProducts.getProduct().getPrice()).sum();
    }
}
