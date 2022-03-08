### WEB - SpringBoot 게시판 
**SpringBoot**를 통해 구현한 게시판입니다. <br/><br/>
**Spring Security**를 사용하여 로그인 및 로그아웃, 패스워드 암호화 그리고 권한 설정을 진행 <br/>
일반적인 게시판에 존재하는 **공지사항(CRUD), 게시글(CRUD), 이미지 첨부/조회, 댓글(CRUD)** 등의 기능을 구현 <br/>
게시판 관리를 위한 **관리자 페이지** 구현 <br/>

---

> 개발환경
### ✔ FrontEnd
- Thymeleaf
- Bootstrap v5.1.3
- JQuery v3.3.1

### ✔ BackEnd
- Language : Java 11
- Framework : SpringBoot v2.5.4
- DB : Mysql 8.0
- Repository : Mybatis v2.1.4
- Gradle

---

> 실행방법 <br/>

### 0. 기본 정보
- 서버 포트: 8080, Mysql 포트: 3306 <br>
- 이미지 파일 업로드 시 파일이 저장되는 서버 경로는 C:/Temp로 지정돼있습니다. <br>
- 수정을 위해서는 저장소를 clone 후 다음 java파일의 saveFile 메서드의 path를 수정하면 됩니다.
```
Board/board/src/main/java/com/min/board/service/FileService.java
```
### 1. clone
```
> git clone https://github.com/wmdwjddyd6/Board.git
```

### 2. application.properties 수정
- Database 관련 정보와 Password 변경에 쓰일 GmailSMTP설정을 변경해줘야 합니다. (보안상 누락)
- Board/board/src/main/resources/application.properties 파일의 해당 부분을 수정하면 됩니다.
```
# DataBase
spring.datasource.username=root
spring.datasource.password=Password

# GoogleMail
spring.mail.username=구글SMTP 비밀번호를 발급받은 google 계정 (예 : exam@gmail.com)
spring.mail.password=발급받은 비밀번호
```

### 3. DB 설정 (Mysql 다운로드 가정)
- Board/board/src/main/resources/db/mysql/schema.sql 실행 
  - Database테이블 생성 및 최초 관리자 계정 생성 (username: admin, password: 123456)<br/><br/>

### 4. Build
```
> cd board
> ./gradlew build
```

### 5. 생성된 jar 실행
```
> cd Board\board\build\libs
> java -jar board-0.0.1-SNAPSHOT.jar
```

### 6. 접속
- 웹 브라우저로 localhost:8080에 접속하여 테스트 가능합니다.

---

> 구현 기능
## 관리자 메뉴 (Role : Admin)
- 회원관리(RD) 
  - 회원 리스팅
  - 회원 게시물 조회
  - 계정 삭제

- 게시글 관리(RD) 
  - 게시글 조회
  - 회원 게시물 조회
  - 게시물 삭제

- 공지사항 관리(CRUD)
  - 공지사항 리스팅
  - 조회
  - 수정
  - 삭제

- 계정 생성(C) 
  - 관리자 계정 생성 
<br/><br/>

## 회원메뉴 (메인 홈페이지)
- 공지사항(R)
  - 공지사항 리스팅, 조회(첨부 이미지 파일 조회)
  - 게시글 조회

- 게시판(CRUD)
  - 게시글 리스팅, 조회(첨부 이미지 파일 조회)
  - 게시글 작성
  - 게시글 수정
  - 게시글 삭제

- 댓글(CRUD)
  - 작성
  - 조회
  - 수정
  - 삭제 
<br/><br/>

## 회원 개인메뉴
- 글관리(RD) 
  - 글 리스팅
  - 글 조회
  - 글 삭제

- 댓글관리(R)
  - 댓글 리스팅
  - 댓글 달린 게시글 조회

- 휴지통(RUD)
  - 임시 삭제된 게시글 리스팅
  - 조회
  - 복원
  - [영구]삭제

- 회원탈퇴(D)
- 비밀번호 변경(U) 
<br/><br/>

## 계정
- 로그인
- 로그아웃
- 회원가입(C)
- 아이디 찾기(R)
- 비밀번호 찾기(RU)
  - 이메일을 통해 임시 비밀번호 발급(U)
