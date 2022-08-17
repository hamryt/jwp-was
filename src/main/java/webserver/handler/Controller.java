package webserver.handler;

import request.HttpRequest;
import response.HttpResponse;

public interface Controller {

    HttpResponse handle(HttpRequest request);

}
