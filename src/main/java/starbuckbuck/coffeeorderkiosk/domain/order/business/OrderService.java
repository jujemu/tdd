package starbuckbuck.coffeeorderkiosk.domain.order.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Service;
import starbuckbuck.coffeeorderkiosk.domain.order.Order;
import starbuckbuck.coffeeorderkiosk.domain.order.persistence.OrderRepository;
import starbuckbuck.coffeeorderkiosk.domain.order.presentation.OrderCreateRequest;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.persistence.ProductRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest request) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

//        new Order
    return null;
    }

}
