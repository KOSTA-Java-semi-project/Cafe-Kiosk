# KOSTA_KIOSK

카페 무인 키오스크를 모사한 콘솔 기반 Java 애플리케이션입니다. 회원가입/로그인, 메뉴 주문, 결제, 스탬프 적립 및 쿠폰 자동 발급, 관리자(매니저)의 메뉴·카테고리 관리 기능을 제공합니다.

## 주요 기능

- **회원**: 전화번호 기반 회원가입/로그인, 비회원 주문
- **메뉴 주문**: 카테고리별 메뉴 조회, 옵션(사이즈/샷/얼음/시럽) 선택 후 장바구니 담기
- **결제**: 쿠폰 사용 또는 즉시 결제, 결제 시 스탬프 적립
- **쿠폰**: 스탬프 10개 적립 시 쿠폰 자동 발급, 보유 쿠폰으로 결제 시 할인 적용
- **관리자(매니저)**: 로그인 후 카테고리/메뉴 등록·수정·품절 처리

## 기술 스택

- Java 21 (순수 JDK, 빌드 도구 없이 Eclipse 프로젝트로 관리)
- JDBC + MySQL Connector/J
- MySQL (AWS RDS)

## 프로젝트 구조

```
src/kosta/kiosk
├── bench       # 성능 비교용 벤치마크 (독립 실행 클래스)
├── controller
├── exception
├── model
│   ├── dao
│   ├── dto
│   └── service
├── session
├── util
└── view
```

DB 스키마는 [`schema/DDL.sql`](schema/DDL.sql)에서 관리하고, 샘플 데이터는 [`schema/seed.sql`](schema/seed.sql)에 있습니다.

## 시작하기

1. 이 저장소를 Eclipse에 Java 프로젝트로 import 합니다. (`JavaSE-21`, MySQL JDBC 드라이버를 User Library로 클래스패스에 추가)
2. MySQL에 `schema/DDL.sql`을 실행해 스키마를 생성하고, 필요하면 `schema/seed.sql`로 샘플 데이터를 넣습니다.
3. `resources/dbInfo.properties` 파일을 아래 형식으로 직접 생성합니다. (`.gitignore`에 포함되어 있어 저장소에는 없습니다.)

   ```properties
   driverName=com.mysql.cj.jdbc.Driver
   url=jdbc:mysql://<host>:3306/Kosta_kiosk
   userName=<db-user>
   password=<db-password>
   ```

4. `StartView`를 Java Application으로 실행합니다.

## 커밋 컨벤션

커밋 메시지는 `[타입] 내용` 형식으로 작성합니다.

| 타입 | 설명 |
| --- | --- |
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | 문서 수정 |
| `style` | 코드 포맷팅, 세미콜론 등 (로직 변경 없음) |
| `refactor` | 기능 변화 없는 코드 리팩토링 |
| `test` | 테스트 코드 추가/수정 |
| `chore` | 빌드 설정, 패키지 매니저 등 기타 작업 |

예시:

```
[feat] 회원 로그인 기능 추가
[fix] 세션 만료 시 예외 처리 오류 수정
[chore] 초기 프로젝트 구조 설정
```
