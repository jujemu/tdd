package starbuckbuck.coffeeorderkiosk.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderTest {

    @DisplayName("상품 목록에서 상품을 선택하여 주문할 수 있다.")
    @Test
    void createOrder() throws Exception {
        //given
            
        //when
        
        //then
    }
    
    @DisplayName("운영시간 외에는 주문할 수 없다.")
    @Test
    void shouldThrowExceptionWhenOutsideBusinessHours() throws Exception {
        //given
            
        //when
        
        //then
    }
    
    @DisplayName("주문에는 최소 한 개 이상의 상품을 골라야한다.")
    @Test
    void shouldThrowExceptionWithNoItems() throws Exception {
        //given
            
        //when
        
        //then
    }
}