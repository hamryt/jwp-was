package request;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserBinderTest {
    @DisplayName("UserBinder user 반환 성공")
    @Test
    void getUser() {
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        User user = UserBinder.from(requestLine);

        assertThat(user).isEqualTo(new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net"));
    }

    @DisplayName("UserBinder user 반환 실패")
    @Test
    void postUser() {
        String request = "POST /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        User user = UserBinder.from(requestLine);

        assertThat(user).isNull();
    }


}