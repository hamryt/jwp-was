package request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestBodyTest {

    @DisplayName("파라미터를 Map으로 파싱한다")
    @Test
    void parseQuery() {
        RequestBody requestBody = new RequestBody("userId=dongho&password=password");

        assertAll(
            () -> assertThat(requestBody.get("userId")).isEqualTo("dongho"),
            () -> assertThat(requestBody.get("password")).isEqualTo("password")
        );
    }
}