package webserver.handler;

import db.DataBase;
import model.User;
import request.HttpRequest;
import request.UserBinder;
import response.HttpResponse;
import webserver.session.HttpSession;

import java.util.Optional;

public class LoginController implements Controller {
    @Override
    public HttpResponse handle(HttpRequest request) {
        User user = UserBinder.from(request.getRequestBody().getParameters());

        boolean loginSuccess = login(user);

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("logined", loginSuccess);

        return HttpResponse.redirect(getRedirectLocation(loginSuccess));
    }

    private boolean login(User User) {
        return Optional.ofNullable(DataBase.findUserById(User.getUserId()))
            .map(it -> it.getPassword().equals(User.getPassword()))
            .orElse(false);
    }

    private String getRedirectLocation(boolean loginSuccess) {
        if (loginSuccess) {
            return "/index.html";
        }

        return "/user/login_failed.html";
    }
}
