package starbuckbuck.coffeeorderkiosk.domain.order.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import starbuckbuck.coffeeorderkiosk.domain.order.Order;
import starbuckbuck.coffeeorderkiosk.domain.order.persistence.OrderRepository;
import starbuckbuck.coffeeorderkiosk.domain.order.presentation.OrderCreateRequest;
import starbuckbuck.coffeeorderkiosk.domain.product.Product;
import starbuckbuck.coffeeorderkiosk.domain.product.persistence.ProductRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime now) {
        Map<String, Integer> productNumberCounter = request.getProductNumberCounter();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumberCounter.keySet());
        Map<String, Product> productNumberToProduct = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));
        List<Product> duplicateProducts = productNumberCounter.entrySet().stream()
                .flatMap(entry ->
                        Collections.nCopies(
                                entry.getValue(),
                                productNumberToProduct.get(entry.getKey())
                        ).stream())
                .toList();

        Order order = new Order(duplicateProducts, now);
        Order savedOrder = orderRepository.save(order);

        return new OrderResponse(savedOrder, now);
    }

}
