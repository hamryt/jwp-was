package request;

import webserver.session.HttpSession;
import webserver.session.HttpSessionContext;
import webserver.session.HttpSessionIdHolder;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public HttpSession getSession() {
        return HttpSessionContext.get(HttpSessionIdHolder.getSessionId());
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }
}
