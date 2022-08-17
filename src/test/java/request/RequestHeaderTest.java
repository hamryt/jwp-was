package request;

import org.apache.coyote.Request;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestHeaderTest {
    @DisplayName("헤더의 key로 value를 찾아올 수 있다")
    @Test
    void addHeader_success() {
        RequestHeader requestHeader = new RequestHeader();

        requestHeader.add("Host: localhost:8080");

        assertThat(requestHeader.get("Host")).isEqualTo("localhost:8080");
    }

    @DisplayName("빈 문자열은 헤더에 추가할 수 없다")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @ValueSource(strings = " ")
    void addEmptyHeader_fail(String emptyHeader) {
        RequestHeader requestHeader = new RequestHeader();

        requestHeader.add(emptyHeader);

        assertThat(requestHeader).isEqualTo(new RequestHeader());
    }

    @DisplayName("value가 없는 헤더는 추가할 수 없다")
    @Test
    void addNoHeader_fail() {
        RequestHeader requestHeader = new RequestHeader();

        requestHeader.add("Hot: ");

        assertThat(requestHeader).isEqualTo(new RequestHeader());
    }

    @DisplayName("등록된 헤더들을 조회할 수 있다")
    @Test
    void add_headers_success() {
        final RequestHeader requestHeader = new RequestHeader();

        requestHeader.add("Host: localhost:8080");
        requestHeader.add("Connection: keep-alive");
        requestHeader.add("Content-Length: 59");
        requestHeader.add("Content-Type: application/x-www-form-urlencoded");
        requestHeader.add("Accept: */*");

        assertAll(
            () -> Assertions.assertThat(requestHeader.getHost()).isEqualTo("localhost:8080"),
            () -> Assertions.assertThat(requestHeader.getConnection()).isEqualTo("keep-alive"),
            () -> Assertions.assertThat(requestHeader.getContentLength()).isEqualTo(59),
            () -> Assertions.assertThat(requestHeader.getContentType()).isEqualTo("application/x-www-form-urlencoded"),
            () -> Assertions.assertThat(requestHeader.getAccept()).isEqualTo("*/*")
        );
    }

    @DisplayName("쿠키가 존재하는지 확인할 수 있다")
    @Test
    void has_cookie() {
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.add("Cookie: cookie=value");

        assertThat(requestHeader.hasCookie("cookie=value")).isTrue();
    }
}