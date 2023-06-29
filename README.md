# Satellite

REPO Backend Repository 입니다.

도서관 문서를 생성하기 위한 [Node.js 서버](https://github.com/EntryDSM/Barnacle)가 별도로 존재합니다.

**기술 스택**

- Framework: Spring Boot
- Web: netty 기반 webflux
- DB: MariaDB, MongoDB
- Test: mockk, Kotest

**아키텍처**

Hexagonal Architecture에 따라 POJO 도메인 모델과 비즈니스 로직은 `application module`로, 웹이나 인프라 구현에 의존하는 코드를 `infrastructure module`로 분리하였습니다.

- **satellite-application:** Core
- **satellite-infrastructure:** API, SPI

<img width="767" alt="image" src="https://github.com/EntryDSM/Satellite/assets/81006587/dfe6aa83-644b-473a-8374-570172aac25b">
