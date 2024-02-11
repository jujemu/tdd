# 테스트 코드

## 테스트 코드 없는 프로젝트는

 - 경험과 감에 의해 검증한다.
 - 기능을 추가하면 기존에 있던 기능의 회귀 테스트를 사람이 해야한다.
 - 소프트웨어 품질을 낮춘다.
 - 테스터의 늦은 피드백을 받는다.

## 단위 테스트

 - 작은 코드 단위를 독립적으로 검증하는 테스트
 - 검증 속도가 빠르고 안정적이다.

## 테스트 라이브러리

- JUnit5: 단위 테스트를 위한 테스트 프레임워크
- AssertJ: 테스트 코드 작성을 원할하게 돕는 테스트 라이브러리. 풍부한 API, 메서드 체이닝 지원

## 테스트 케이스 세분화하기

 - 해피 케이스: 테스트하는 기능이 정상적으로 동작했을 때, 기대되는 결과값을 검증
 - 예외 케이스: 정상적으로 수행할 수 없는 입력, 상황에 대한 예외를 검증

___범위 조건이 있다면 경계값 테스트을 해보자___

## 테스트 코드를 테스트를 할 수 있는 코드와 아닌 코드로 구분하자

- 입력과 출력에 의존하지 않도록 한다.
- 같은 입력에는 언제나 같은 출력을 반환한다.
- 외부와 단절되도록 한다.

### 테스트하기 어려운 영역이란?

관측할 때마다 다른 값에 의존하는 코드. 예를 들면, 영업 시간에만 주문을 받기로 로직을 구현했을 때, 테스트를 영업 시간 외에 검증하면 예외가 발생되는 경우. 이럴 때는 주문을 생성하는 메서드에 매개 변수로 주문 시간을 받도록 하여, 외부에서 지정하도록 구현하면 테스트가 가능하도록 할 수 있다.
<br>

외부에 영향을 주는 코드. 메시지를 발송하거나 데이터베이스에 기록하는 것 등을 예로 들 수 있다.

## TDD

![](./images/tdd.jpg)

- 프로덕션 코드보다 테스트 코드를 먼저 작성함
- 당연히 구현부가 없으니 테스트가 실패함
- 테스트 컴파일은 실행될 수 있도록 깡통 로직은 만듬
- 테스트가 통과할 수 있도록 코드 구현
- 테스트 통과를 확인하면서 리팩토링을 반복함

### 피드백

_TDD의 핵심 가치_

기능 구현부터 하게 되면

- 테스트 자체를 누락할 수 있다.
- 성공 케이스만을 테스트할 수 있다.
- 잘못된 구현을 늦게 발견할 수 있다.

### 테스트부터 하게 되면,

- 복잡도가 낮은, 유연하며 유지보수가 쉬운 테스트 가능한 코드로 구현할 수 있다.
- 쉽게 발견하기 어려운 엣지 케이스를 놓치지 않게 해준다(경계값)
- 구현에 대한 빠른 피드백을 받을 수 있다.
- 과감한 리팩토링이 가능해진다.

## 테스트는 문서다

`어느 한 사람이 과거에 경험했던 고민의 결과물을 팀 차원으로 승격시켜서, 모두의 자산으로 공유할 수 있다.`

### DisplayName

Junit5에서 추가된 어노테이션

- 테스트 빌드를 인텔리제이로 하면 메서드 이름 대신에 DisplayName을 확인할 수 있다.
- 명사의 나열보다 문장으로; A이면 B이다.
- 테스트 행위에 대한 결과까지 표현하자.
- 도메인 용어를 사용하여 추상화된 내용을 담자; 메서드의 관점보다는 도메인 정책 관점으로
- 성공한다와 실패한다 같은 테스트의 현상을 중점으로 표현하지 않는다.

### BDD

- Given: 시나리오 진행에 필요한 모든 준비 과정
- When: 시나리오 진행
- Then: 결과 명시, 검증

어떤 환경에서, 어떤 행동을 진행했을 때, 어떤 상태 변화가 일어난다. -> DisplayName 명확하게 작성할 수 있다.

## 테스트 작성

### given 두 예시

```java
createProducts();

private void createProducts() {
        Product product1 = createProduct("00Ω1", HANDMADE, "아메리카노", 5000);

        Product product2= Product.builder()
                .productNumber("002")
                .type(HANDMADE)
                .sellingStatus(PAUSE)
                .name("카페라떼")
                .price(6000)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .type(HANDMADE)
                .sellingStatus(STOP)
                .name("팥빙수")
                .price(8000)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));
    }
```

위 처럼 테스트 메서드에 product 생성 부분을 메서드로 묶으면 공통된 부분을 간소화할 수 있지만  
어떤 product 생성되었는지, 테스트에 적합한 product를 생성할 수 없는 단점을 가지고 있다.

따라서 아래처럼 수정하기로 했다.

```java
Product product1 = createProduct("001", HANDMADE, "아메리카노", 5000);
Product product2 = createProduct("002", HANDMADE, "카페라떼", 6000);
Product product3 = createProduct("003", HANDMADE, "팥빙수", 8000);
productRepository.saveAll(List.of(product1, product2, product3));
```

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

## 테스트 메서드 충돌

- 여러 테스트 메서드를 실행하면 데이터를 저장하면서 서로 간섭할 수 있다.
- 테스트 메서드가 종료되면서 rollback 해야한다. 다음의 방법이 있다.
  - AfterEach annotation 메서드
  - 클래스에 Transactional annotation

## 컨트롤러 수동 테스트

인텔리제이에서 지원하는 http 문법을 사용해서 직접 테스트해볼 수 있다. 다음을 참고하자.

_https://sihyung92.oopy.io/etc/intellij/2_



