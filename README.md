# TDD

## 테스트 코드

<a href="./test_code.md"> 테스트 코드에 대해서 </a>

## 프로젝트 소개

간단한 커피 주문 키오스크 개발에 tdd 적용

## 기능 요구사항

### 키오스크
 
- 상품 목록을 조회할 수 있다.
- 상품의 판매 상태
  - 판매중, 판매 보류, 판매 중지
- id, 상품 번호, 상품 타입, 판매 상태, 상품 이름, 가격
- 운영 시간(10~22) 외에는 주문할 수 없다.
- 주문할 상품 리스트를 받아 주문 생성하기
- 주문은 주문 상태, 주문 등록 시간을 가진다.
- 주문 목록 총 금액 계산하기

## 기능 요구사항 - 1차 추가

- 주문 생성 시 재고 확인 및 개수 차감
- 재고는 상품번호를 가진다.
- 재고와 관련 있는 상품 타입은 병, 베이커리

## 기능 요구사항 - 2차 추가

- 관리자는 키오스크로 제품을 등록할 수 있다.
- 제품 등록에는 타입, 상태, 이름, 가격을 필요로 한다.

# 회고

## 테스트 작성

### then 절

```java
    @DisplayName("상품 목록을 받아 주문을 생성하면 INIT 상태를 가진 주문이 생성된다.")
    @Test
    void createOrder() throws Exception {
        ...

        //then
        assertThat(response.getOrderId()).isNotNull();
        assertThat(response.getTotalPrice()).isEqualTo(18000);
        assertThat(response.getProductResponses()).hasSize(3)
                .extracting("productNumber", "name")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노"),
                        tuple("001", "아메리카노"),
                        tuple("003", "팥빙수")
                );
    }
```

- 생성된 order id 확인; notNull
  - 비즈니스 계층에서 검증해야할 부분에는 주문이 정상적으로 저장되었는지가 포함된다.
  - 영속 계층에 대한 검증이 끝났지만 서비스 계층에서 통합 테스트가 이루어져야 하는 점을 잊지 말자.
  - OrderRepository 사용해서 db를 조회하는 것보다는 persistence context 저장되었을 때,
  - id 값이 부여되는 점을 이용해서 검증하자.
- 리스트의 경우는 사이즈와 필드값을 추출하여 검증한다.

## 로직에서 어려웠던 점

### 주문받고 중복된 객체 생성

```java
public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime now) {
        Map<String, Integer> productNumberCounter = request.getProductNumberCounter();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumberCounter.keySet());
        Map<String, Product> productNumberToProduct = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));
        List<Product> duplicatedProducts = productNumberCounter.entrySet().stream()
                .flatMap(entry ->
                        Collections.nCopies(
                                entry.getValue(),
                                productNumberToProduct.get(entry.getKey())
                        ).stream())
                .toList();

        Order order = new Order(duplicatedProducts, now);
        Order savedOrder = orderRepository.save(order);

        return new OrderResponse(savedOrder, now);
    }
```

- 키오스크에서 상품 목록을 받아 주문을 생성한다.
- 상품 목록에 동일한 제품의 개수를 받는다.
- 상품의 중복된 개수만큼 OrderProduct 생성
  - numberOfProduct 컬럼을 추가할 수도 있지만 팀에서 결정된 사항
  - 카운터로 리스트를 만드는 과정에서 Map.entry, Collections.nCopies 이해했다.

### 제니릭 관련

```java
public static <T> ApiResponse<T> of(HttpStatus status, String message) {
    return new ApiResponse<>(status, message, null);
}

public static <T> ApiResponse<T> of(HttpStatus status, T data) {
    return new ApiResponse<>(status, status.name(), data);
}
```

ApiResponse 위처럼 정의되어 있다.

```java
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ApiResponse<?> bindException(BindException e) {
        return ApiResponse.of(
                BAD_REQUEST,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
    }
}
```

- 만약 예외 처리에서 ApiResponse.of 호출하면 어떤 메서드가 호출될지를, 어떻게 오버로딩을 처리할지가 궁금했다.
- 메세지를 전달하기 위해서 String 넣으면 위 메서드를 호출하고 그 외에는 제네릭 메서드를 호출한다.
