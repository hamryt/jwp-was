package webserver.handler;

import request.HttpRequest;
import request.RequestLine;
import response.HttpResponse;

public class DefaultController implements Controller {

    @Override
    public HttpResponse handle(HttpRequest request) {
        RequestLine requestLine = request.getRequestLine();

        return HttpResponse.ok(requestLine.getPath().getName());
    }
}
