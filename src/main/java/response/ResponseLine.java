package response;

import request.ProtocolVersion;

import java.util.Objects;

/**
 * HTTP/1.1 200 OK
 * Content-Type: text/html
 * Set-Cookie: logined=true; Path=/
 */

public class ResponseLine {

    private final ProtocolVersion protocolVersion;
    private final StatusCode statusCode;

    public ResponseLine(ProtocolVersion protocolVersion, StatusCode statusCode) {
        this.protocolVersion = protocolVersion;
        this.statusCode = statusCode;
    }

    public String getProtocol() {
        return protocolVersion.getProtocol();
    }

    public String getVersion() {
        return protocolVersion.getVersion();
    }

    public String getStatusCode() {
        return statusCode.name();
    }

    public String value() {
        return protocolVersion.getValue() + " " + statusCode.message();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResponseLine that = (ResponseLine) o;
        return Objects.equals(protocolVersion, that.protocolVersion) && statusCode == that.statusCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocolVersion, statusCode);
    }
}
