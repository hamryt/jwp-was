package response;

import request.ProtocolVersion;

import javax.print.attribute.standard.PDLOverrideSupported;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * HTTP/1.1 200 OK
 * Content-Type: text/html
 * Set-Cookie: logined=true; Path=/
 * <p>
 * <p>
 * HTTP/1.1 302 Found
 * Location: http://www.iana.org/domains/example/
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

    public void write(DataOutputStream dos) throws IOException {
        writeResponseLine(dos);
        writeHeaders(dos);
        writeBody(dos);
        dos.flush();
    }

    private void writeResponseLine(DataOutputStream dos) throws IOException {
        dos.writeBytes(responseLine.value() + "\r\n");
    }

    private void writeHeaders(DataOutputStream dos) throws IOException {
        for (String header : responseHeader.getHeaders().keySet()) {
            dos.writeBytes(header + ": " + responseHeader.get(header) + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void writeBody(DataOutputStream dos) throws IOException {
        dos.write(responseBody.getBytes(), 0, responseBody.getBytes().length);
    }



    public static HttpResponse redirect(String location) {
        ResponseHeader headers = new ResponseHeader();
        headers.add("Location", location);

        return new HttpResponse(new ResponseLine(new ProtocolVersion("HTTP/1.1"), StatusCode.FOUND), headers, ResponseBody.EMPTY_RESPONSE_BODY);
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
