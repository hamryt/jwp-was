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

### 기능 요구사항 6
* 접근하고 있는 사용자가 "로그인" 상태일 경우 /user/list로 접근했을 때 사용자 목록을 출력한다
* 로그인하지 않은 상태라면 로그인 페이지로 이동한다

# 3 단계 - HTTP 웹 서버 리팩토링
### was 요구사항
* 여러 가지 역할을 가지는 코드가 혼재되어 있어 각각의 역할을 분리해 재사용 가능하도록 개선한다.
* HTTP 요청/응답 처리 기능은 개발자가 신경쓰지 않아도 재사용이 가능한 구조가 되도록 한다.

### 코드 리팩토링 요구사항
* 리팩토링할 부분을 찾았다면 도움없이 (힌트를 보지 않고) 리팩토링을 진행한다.

### 기능 목록
* HTTP 요청을 처리하는 객체를 생성한다. (HTTP Request)
  * 요청한 내용을 InputStream으로 읽어 요청과 관련된 모든 내용을 담고 있는다. 
    * RequestLine
    * RequestHeader
    * RequestBody
* HTTP 응답을 처리하는 객체를 생성한다
  * 요청에 응답하기 위한 내용을 생성하여 OutputStream으로 출력한다
    * StatusLine
    * ResponseHeader
    * ResponseBody
  * URI에 따라 비즈니스 로직을 처리할 수 있는 객체를 생성한다.
    * HttpRequest, RequestLine의 내용으로 요청을 수행할 객체를 찾는다
    * 요청을 수행할 객체가 비즈니스 로직을 처리할 객체에게 책임을 위임한다.
    * 요청을 수행할 객체가 비즈니스 로직의 처리 결과를 응답 결과에 추가한다.

# 4 단계 - 세션 구현하기

### 요구사항
* 서블릿에서 지원하는 HttpSession API의 일부를 지원한다.
* 세션은 클라이언트와 서버 간에 상태 값을 공유하기 위해 고유한 아이디를 활용하고, 이 고유한 아이디는 쿠키를 활용해 공유한다.

### 기능 목록
* HttpSession
  * String getId()
    * UUID.randomUUID()로 고우한 아이디를 생성한다.
  * void setAttribute(String name, Object value)
    * 세션에 저장된 값을 저장한다
  * Object getAttribute(String name)
    * 세션에 저장된 값을 읽어온다.
  * void removeAttribute(String name)
    * 세션에 저장된 값을 삭제한다
  * void invalidate()
    * 세션을 삭제한다.
  * 서버 최초 접근 시 세션을 생성한다
    * 클라이언트 요청 헤더에 세션 쿠키가 없으면 최초 접근이라고 판단한다
    * 클라이언트 요청 헤더에 세션 쿠키가 있지만 서버 내부의 쿠키의 값에 해당하는 세션이 없으면 최초 접근이라고 판단한다
  * 클라이언트 요청에 해당하는 세션이 서버에 존재하는 경우(최초 생성된 경우 포함) 세션의 아이디를 쿠키에 추가하여 응답한다.
  * 로그인 시 세션에 로그인 정보를 저장한다
  * 사용자 목록 조회 시 세션에 저장된 로그인 여부에 따라 분기 처리한다