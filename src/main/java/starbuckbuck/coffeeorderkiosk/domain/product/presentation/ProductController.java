package starbuckbuck.coffeeorderkiosk.domain.product.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import starbuckbuck.coffeeorderkiosk.domain.product.business.ProductResponse;
import starbuckbuck.coffeeorderkiosk.domain.product.business.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/v1/products/selling")
    public List<ProductResponse> getSellingProducts() {
        return productService.getSellingProducts();
    }
}
