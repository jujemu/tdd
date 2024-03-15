# BDD Mockito

```java
BDDMockito.given(tokenProvider.generateToken(anyLong(), anyString(), any(Duration.class)))
                .willReturn(token); // Mockito.when().thenReturn
```
