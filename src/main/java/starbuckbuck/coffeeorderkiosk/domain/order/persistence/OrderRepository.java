package starbuckbuck.coffeeorderkiosk.domain.order.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import starbuckbuck.coffeeorderkiosk.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
}
