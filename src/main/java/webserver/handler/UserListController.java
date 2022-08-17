package webserver.handler;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;

import java.util.Collection;
import java.util.Map;

public class UserListController implements Controller {

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (isLogin(request)) {
            Collection<User> users = DataBase.findAll();
            Map<String, Object> params = Map.of("users", users);

            return HttpResponse.ok("user/list", params);
        }

        return null;
    }

    private static boolean isLogin(HttpRequest request) {
        return request.getRequestHeader().hasCookie("logined=true");
    }
}
