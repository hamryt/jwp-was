package webserver.handler;

import db.DataBase;
import model.User;
import request.HttpRequest;
import request.UserBinder;
import response.HttpResponse;

public class CreateUserController implements Controller{

    @Override
    public HttpResponse handle(HttpRequest request) {
        User user = UserBinder.from(request.getRequestBody().getParameters());

        DataBase.addUser(user);

        return HttpResponse.redirect("/index.html");
    }
}
