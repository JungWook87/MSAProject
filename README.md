# MSA 프로젝트
Spring Boot, Security, JWT, JPA, REST API를 학습하기 위한 개인 프로젝트

<br>

### 버전
<br>
JDK : 17 <br>
Boot : 3.2.2 <br>
Security : 6.2.1 <br>
jwt : 0.11.5 <br>
db : mariaDB <br>

### 개발 진행
- 24.02.23 <br>
  EurekaServer, gateway API, board, userService 생성 및 세팅 <br>
  Security 적용, mustache 사용하여 테스트 <br>
  <br>
- 24.02.27 <br>
  jwt 적용 <br>
  SecurityConfig 세팅 <br>
  LoginFilter 구현 <br>
  <br>
- 24.02.29 <br>
  jwt 검증 클래스 구현 <br>
  <br>
- 24.03.04
  jwt 토큰 발급 및 검증 <br>
  LoginFilter에 JWTUtil 의존성 주입 후 토큰 생성 <br>
  JWTFilter 클래스 생성 후 토큰 검증 <br>
  포스트맨으로 테스트 <br>
- 24.03.05
  jwt 세션 사용해보기
  CORS 개념 및 설정하기
  CorsMvcConfig 클래스 생성
- 24.03.07
  다중 토큰 발급(ATK , RTK)
  LoginFilter, JWTUtil 클래스 수정

