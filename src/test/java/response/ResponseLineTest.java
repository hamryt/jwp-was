package response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.ProtocolVersion;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseLineTest {

    @DisplayName("프로토콜 파싱")
    @Test
    void protocol_http() {
        ResponseLine responseLine = new ResponseLine(new ProtocolVersion("HTTP/1.1"), StatusCode.OK);

        assertThat(responseLine.getProtocol()).isEqualTo("HTTP");
    }

    @DisplayName("version 1.1")
    @Test
    void version() {
        ResponseLine responseLine = new ResponseLine(new ProtocolVersion("HTTP/1.1"), StatusCode.OK);

        assertThat(responseLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("정상 응답")
    @Test
    void status_ok() {
        ResponseLine responseLine = new ResponseLine(new ProtocolVersion("HTTP/1.1"), StatusCode.OK);

        assertThat(responseLine.getStatusCode()).isEqualTo("OK");
    }
}