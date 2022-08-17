package webserver.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("요청을 처리할 객체를 찾는다")
class RequestMappingTest {

    @DisplayName("회원가입이 요청을 처리할 객체를 찾는다")
    @Test
    void find_create_user_controller() {
        String requestMessage = "POST /user/create HTTP/1.1";

        RequestLine requestLine = RequestLine.parse(requestMessage);

        Controller actual = RequestMapping.map(requestLine);

        assertThat(actual).isInstanceOf(CreateUserController.class);
    }
}