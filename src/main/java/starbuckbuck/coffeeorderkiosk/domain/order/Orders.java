package starbuckbuck.coffeeorderkiosk.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import starbuckbuck.coffeeorderkiosk.domain.orderproduct.OrderProduct;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    public Orders(List<OrderProduct> orderProducts, LocalTime create) {
        LocalTime open = LocalTime.of(8, 0);
        LocalTime closed = LocalTime.of(22, 0);
        if (create.isBefore(open) || create.isAfter(closed)) {
            throw new IllegalArgumentException("운영시간이 아닙니다.");
        }

        if (orderProducts == null || orderProducts.isEmpty()) {
            throw new IllegalArgumentException("한 개 이상 주문해야합니다.");
        }

        this.orderProducts = orderProducts;
    }

    public Integer calculateTotalPrice() {
        return this.orderProducts.stream()
                .mapToInt(orderProducts ->
                        orderProducts.getProduct().getPrice()).sum();
    }
}
