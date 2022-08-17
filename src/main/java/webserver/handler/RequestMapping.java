package webserver.handler;

import request.RequestLine;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private RequestMapping() {
        throw new AssertionError();
    }

    private static final Map<RequestLine, Controller> CONTROLLERS = new HashMap<>();

    private static final String HTTP_1_1 = "HTTP/1.1";

    static {
        CONTROLLERS.put(RequestLine.parse("POST /user/create HTTP/1.1"), new CreateUserController());
        CONTROLLERS.put(RequestLine.parse("POST /user/login HTTP/1.1"), new LoginController());
        CONTROLLERS.put(RequestLine.parse("GET /users/list.html HTTP/1.1"), new UserListController());
    }

    public static Controller map(RequestLine requestLine) {
        if (CONTROLLERS.containsKey(requestLine)) {
            return CONTROLLERS.get(requestLine);
        }

        return new DefaultController();
    }
}
