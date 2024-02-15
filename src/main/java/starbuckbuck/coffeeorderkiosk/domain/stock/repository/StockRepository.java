package starbuckbuck.coffeeorderkiosk.domain.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import starbuckbuck.coffeeorderkiosk.domain.stock.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductNumber(String productNumber);
}
