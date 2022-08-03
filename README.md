## 웹 애플리케이션 서버

# 1 단계 - TDD 실습
## 기능 요구 사항
* HTTP 요청과 응답을 파싱해 원하는 값을 가져올 수 있는 API를 제공해야 한다.
* RequestLine을 파싱해 원하는 값을 가져올 수 있는 API를 제공해야 한다.
* HTTP GET 요청에 대한 RequestLine을 파싱한다.
* HTTP POST 요청에 대한 RequestLine을 파싱한다.
* HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
* HTTP method인 GET, POST를 enum으로 구현한다.

## 프로그래밍 요구사항
* 모든 로직에 단위 테스트를 구현한다.
* 자바 코드 컨벤션을 지키면서 프로그래밍한다.
* 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기(indent)만 한다.

# 2 단계 - HTTP 웹 서버 구현
### 기능 요구사항 1
* http://localhost:8080/index.html 로 접속했을 때 webapp 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.

### 기능 요구사항 2
* “회원가입” 메뉴를 클릭하면 http://localhost:8080/user/form.html 으로 이동하면서 회원가입할 수 있다. 
* 회원가입을 하면 다음과 같은 형태로 사용자가 입력한 값이 서버에 전달된다.
* /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
* HTML과 URL을 비교해 보고 사용자가 입력한 값을 파싱해 model.User 클래스에 저장한다.

### 기능 요구사항 3
* http://localhost:8080/user/form.html 파일의 form 태그 method를 get에서 post로 수정한 후 회원가입 기능이 정상적으로 동작하도록 구현한다.

### 기능 요구사항 4
* redirect 방식처럼 회원가입을 완료한 후 “index.html”로 이동해야 한다. 즉, 브라우저의 URL이 /index.html로 변경해야 한다.
* 응답 헤더의 status code를 302로 설정한다

### 기능 요구사항 5
* 로그인 메뉴를 클릭하면 localhost:8080/user/login.html으로 이동해 로그인할 수 있다. 
* 로그인이 성공하면 index.html로 이동한다
* 로그인이 실패하면 /user/login_failed.html로 이동한다
* 앞에서 회원가입한 사용자로 로그인할 수 있어야 한다.
* 로그인이 성공하면 cookie header 값이 logined=true
* 로그인이 실패하면 cookie header 값이 logined=false