package response;

/**
 * HTTP/1.1 200 OK
 * Content-Type: text/html
 * Set-Cookie: logined=true; Path=/
 */

public class HttpResponse {

    private final StatusLine statusLine;
    private final ResponseHeader responseHeader;
    private final ResponseBody responseBody;

    public HttpResponse(StatusLine statusLine, ResponseHeader responseHeader, ResponseBody responseBody) {
        this.statusLine = statusLine;
        this.responseHeader = responseHeader;
        this.responseBody = responseBody;
    }
}
