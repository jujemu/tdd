package starbuckbuck.coffeeorderkiosk.domain.order.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import starbuckbuck.coffeeorderkiosk.domain.order.persistence.OrderRepository;
import starbuckbuck.coffeeorderkiosk.domain.order.presentation.OrderCreateRequest;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request) {
        List<String> productNumbers = request.getProductNumbers();
        return null;
    }
}
