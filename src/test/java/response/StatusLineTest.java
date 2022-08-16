package response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.ProtocolVersion;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class StatusLineTest {

    @DisplayName("프로토콜 파싱")
    @Test
    void protocol_http() {
        StatusLine statusLine = new StatusLine(new ProtocolVersion("HTTP/1.1"), StatusCode.OK);

        assertThat(statusLine.getProtocol()).isEqualTo("HTTP");
    }

    @DisplayName("version 1.1")
    @Test
    void version() {
        StatusLine statusLine = new StatusLine(new ProtocolVersion("HTTP/1.1"), StatusCode.OK);

        assertThat(statusLine.getVersion()).isEqualTo("1.1");
    }

    @DisplayName("정상 응답")
    @Test
    void status_ok() {
        StatusLine statusLine = new StatusLine(new ProtocolVersion("HTTP/1.1"), StatusCode.OK);

        assertThat(statusLine.getStatusCode()).isEqualTo("OK");
    }
}