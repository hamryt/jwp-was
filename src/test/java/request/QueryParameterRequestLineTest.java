package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class QueryParameterRequestLineTest {
    @DisplayName("QueryParameters parsing 성공")
    @Test
    void createQueryParametersRequestLine_success() {
        String request = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";
        RequestLine requestLine = new RequestLine(request);

        assertAll(
            () -> assertThat(requestLine.getHttpMethod()).isEqualTo(HttpMethod.GET.name()),
            () -> assertThat(requestLine.getName()).isEqualTo("/users"),
            () -> assertThat(requestLine.getQueryValueOf("userId")).isEqualTo("javajigi"),
            () -> assertThat(requestLine.getQueryValueOf("password")).isEqualTo("password"),
            () -> assertThat(requestLine.getQueryValueOf("name")).isEqualTo("JaeSung"),
            () -> assertThat(requestLine.getProtocol()).isEqualTo("HTTP"),
            () -> assertThat(requestLine.getVersion()).isEqualTo("1.1")
        );
    }

    @DisplayName("user 도메인에 대한 요청 식별 성공")
    @Test
    void identifyUserRequestSuccess() {
        String query = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Path path = new Path(query);

        assertThat(path.isUser()).isTrue();
    }

    @DisplayName("user 도메인에 대한 요청 식별 실패")
    @Test
    void identifyUserRequestFail() {
        String query = "/team/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Path path = new Path(query);

        assertThat(path.isUser()).isFalse();
    }
}
