package starbuckbuck.coffeeorderkiosk.domain.product.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.ProductSellingStatus;
import starbuckbuck.coffeeorderkiosk.domain.product.persistence.ProductRepository;
import starbuckbuck.coffeeorderkiosk.domain.product.presentation.ProductCreateRequest;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        String nextProductNumber = getNextProductNumber();
        Product product = request.toEntity(nextProductNumber);
        productRepository.save(product);

        return ProductResponse.of(product);
    }

    private String getNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber().orElse("000");
        int nextProductNumberInt = Integer.parseInt(latestProductNumber);
        return String.format("%03d", nextProductNumberInt+1);
    }
}
