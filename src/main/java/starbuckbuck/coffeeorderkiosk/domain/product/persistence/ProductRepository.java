package starbuckbuck.coffeeorderkiosk.domain.product.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> productSellingStatuses);

    List<Product> findAllByProductNumberIn(Set<String> productNumbers);
}
