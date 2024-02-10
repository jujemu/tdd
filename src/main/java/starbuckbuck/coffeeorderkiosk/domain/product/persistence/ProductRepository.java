package starbuckbuck.coffeeorderkiosk.domain.product.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductSellingStatus;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> productSellingStatuses);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
