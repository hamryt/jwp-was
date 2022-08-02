package request;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserBinderTest {
    @DisplayName("UserBinder user 반환 성공")
    @Test
    void getUser_success() {
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        RequestBody requestBody = new RequestBody(body);

        User user = UserBinder.from(requestBody.getParameters());

        assertThat(user).isEqualTo(new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net"));
    }
}