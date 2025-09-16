# 📝 게시판 프로젝트 (Board Project)

## 📌 프로젝트 개요
Spring Boot 3.2.5, JPA, JSP, Spring Security를 기반으로 구현한 **게시판 웹 애플리케이션**입니다.  
회원, 비회원, 관리자에 따라 권한을 다르게 설정하였으며, 게시글과 댓글/대댓글 CRUD 기능 및 게시글 숨기기 기능을 지원합니다.  

---

## ⚙️ 기술 스택
- **Back-End**: Java 17, Spring Boot 3.2.5, Spring Security, JPA (Hibernate)
- **Front-End**: JSP, JSTL, HTML/CSS, JavaScript
- **Database**: MySQL
- **Build Tool**: Gradle
- **Server**: Tomcat (내장)
- **ETC**: Lombok

---

## 👥 사용자 권한
| 권한      | 설명                                                                 |
|-----------|----------------------------------------------------------------------|
| **비회원** | 게시글 목록 / 상세 조회 가능<br>숨김 처리된 글, 댓글, 대댓글은 조회 불가 |
| **회원**   | 게시글 CRUD 가능<br>댓글/대댓글 CRUD 가능<br>자신이 쓴 글과 댓글만 수정 가능 |
| **관리자** | 모든 회원 조회 가능<br>모든 회원이 작성한 게시글/댓글/대댓글 조회 가능<br>부적절한 게시글 숨기기 기능 사용 가능 |

---

## ✨ 주요 기능
### 1. 게시글
- 회원: 게시글 CRUD 가능
- 비회원: 게시글 조회만 가능
- 관리자: 모든 게시글 열람 + 숨기기 기능 / 모든 회원 조회 가능

### 2. 댓글 & 대댓글
- 회원: CRUD 가능 (본인 작성한 것만 수정/삭제 가능)
- 비회원: 조회만 가능
- 관리자: 모든 댓글/대댓글 열람 + 숨기기 기능

### 3. 숨기기 기능
- 관리자가 특정 게시글 **숨김 처리**
- 회원/비회원 모두 숨김 처리된 글을 볼 수 없음

### 4. 마이페이지 기능
- 비회원 : 불가능
- 회원 : 자신이 작성한 글/댓글/대댓글 조회
- 관리자 : 관리자 페이지로 변경

## 🗂️ 프로젝트 구조
```
src
├─ main
│ ├─ java/com/example/board
│ │ ├─ config # Spring Security, NewCustomException
│ │ ├─ controller # 게시글/댓글/회원/관리자/홈/마이페이지 Controller
│ │ ├─ entity # JPA 엔티티 클래스/Role Enum
│ │ ├─ repository # JPA Repository 인터페이스
│ │ ├─ service # 비즈니스 로직
│ │ └─ dto # 요청/응답 DTO
│ └─ resources
│ │ ├─ application.yml
│ │ ├─ application-db.yml
│ │ └─ static/css
│ │ │ ├─ my-page.css
│ └─ webapp/WEB-INF/views
│ │ ├─ admin # user/userDetail jsp
│ │ ├─ auth # login/mypage/register jsp
│ │ ├─ board # edit-form/view/write-form jsp
│ │ ├─ common # 헤더
│ │ ├─ home.jsp
│ │ └─ index.jsp
└─ test/java/... # 테스트 코드
```
---
```yml
# application.yml
spring:  
  mvc:  
    view:  
      prefix: /WEB-INF/views/  
      suffix: .jsp  
  profiles:  
    config:  
      -db  
  
server:  
  port: 8080
```

---

## 🖼️ ERD 

User (회원)  
├─ id (PK)  
├─ username  
├─ password  
├─ role (USER / ADMIN)  
└─ boards (1:N)

Board (게시글)  
├─ id (PK)  
├─ title  
├─ content  
├─ createdAt  
├─ updatedAt  
├─ hidden (boolean)  
├─ author_id (FK → `User.id`)  
└─ comments (1:N)

Comment (댓글/대댓글)  
├─ id (PK)  
├─ content  
├─ createdAt  
├─ updatedAt  
├─ hidden (boolean)  
├─ author_id (FK → `User.id`)  
├─ board_id (FK → `Board.id`)  
└─ parent_id (FK → `Comment.id`, nullable → 대댓글 구조)

---

## 🚀 실행 방법
1. **프로젝트 클론**
   ```bash
   git clone https://github.com/gyocheol/new.git
   cd new
DB 설정
로컬 DB 사용으로 인해 공유 불가/아래 yml 참고

```yml
spring:  
  datasource:  
    url: jdbc:mysql://localhost:3306/DB_name?useSSL=false&serverTimezone=Asia/Seoul  
    username: "id"
    password: "pw"  
    driver-class-name: com.mysql.cj.jdbc.Driver  
  jpa:  
    hibernate:  
      ddl-auto: update  
    show-sql: true  
    properties:  
      hibernate:  
        format_sql: true
```
## 📌 향후 개선 예정
    
-   검색 및 페이징 기능
    
-   좋아요, 북마크 기능


# 노션에 정리하면서 진행 중
https://sky-haumea-606.notion.site/22950ab1f76c80829512eada950a616d?source=copy_link
