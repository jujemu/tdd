package starbuckbuck.coffeeorderkiosk.domain.product.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductSellingStatus;
import starbuckbuck.coffeeorderkiosk.domain.product.persistence.ProductRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /*
    ProductSellingStatus가 SELLING, PAUSE 목록을 가져온다.
     */
    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplayInKiosk());

        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
