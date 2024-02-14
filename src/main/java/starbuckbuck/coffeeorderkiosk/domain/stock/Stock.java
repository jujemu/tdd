package starbuckbuck.coffeeorderkiosk.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import starbuckbuck.coffeeorderkiosk.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productNumber;
    private int quantity;

    public Stock(String productNumber, int quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    /*
    주문한 수량이 재고보다 많을 경우,

    1. 도메인에서 예외 발생
    2. false로 반환 후, 비즈니스 계층에서 처리

    이 구현들이 요구사항 문제일까, 도메인을 이해하지 못한 걸까.
    우선은 도메인에서 예외 발생으로 구현 -> 치명적인 이슈라고 판단
     */
    public boolean isOutOfStock(int orderedQuantity) {
        return this.quantity < orderedQuantity;
    }

    public void removeFromStock(int orderedQuantity) {
        if (isOutOfStock(orderedQuantity)) {
            throw new IllegalArgumentException("재고보다 많은 주문 수량입니다.");
        }

        this.quantity -= orderedQuantity;
    }
}
