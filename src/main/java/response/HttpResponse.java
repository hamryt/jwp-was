package response;

import java.util.Objects;

/**
 * HTTP/1.1 200 OK
 * Content-Type: text/html
 * Set-Cookie: logined=true; Path=/
 */

public class HttpResponse {

    private final ResponseLine responseLine;
    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;

    public HttpResponse(ResponseLine responseLine, ResponseHeader responseHeader, ResponseBody responseBody) {
        this.responseLine = responseLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HttpResponse that = (HttpResponse) o;
        return Objects.equals(responseLine, that.responseLine) && Objects.equals(responseHeader, that.responseHeader)
            && Objects.equals(responseBody, that.responseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseLine, responseHeader, responseBody);
    }
}
