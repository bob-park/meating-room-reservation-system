# Meeting Room Reservation System Back-End


## Environment

### DB

* `mariadb`
  * version : 10.6
  
### Service Module

#### API Docs
* `Spring Rest Docs`

#### Discovery
* `Netflix Eureka` 

#### `String Cloud`
* Config Server / Client
* Resilience4j
* openfeign
* Sleuth
* Zipkin

#### Etc
* Spring Security
* JPA
* queryDSL
* `JWT`


## 모듈 구성

### m2rs-core
하위 모듈에서 공통으로 사용하는 `utils` 또는 `class` 를 모은 모듈


### m2rs-discovery-service
Service Module 을 Discovery Cluster 를 구성하여 관리하는 모듈


### m2rs-config-service
Service Module 에서 사용하는 `Configuration` 을 관리하는 모듈


### m2rs-db-migration
Service Module 이 시작하기전 DB Migration 을 진행하는 모듈

#### 상세
* DB 테이블 생성
* default data insert
* 위와 같은 DB Migration 작업

#### 주의 
다른 서비스 모듈보다 먼저 실행되어야 함

### m2rs-api-gateway-service
Service Module 를 외부로 노출시키는 역할 및 Discovery Service 와 연계하여 분산 처리하는 모듈

#### 상세
* Discovery Client 를 조회하여 router 에 등록된 service module 로 load balence 진행
* 기본적으로 'Authentication' 이 필요한 요청은 service module 로 요청을 보내기 전 검증을 진행 

### m2rs-user-service
사용자 관련 및 `Authentication`, `Authorization` 을 담당하는 모듈

#### 상세
* Authentication
* Authorization 
  * 접근 가능한 resource 및 권한 확인

### m2rs-meeting-room-service
회의실 관련 서비스를 담당하는 모듈



