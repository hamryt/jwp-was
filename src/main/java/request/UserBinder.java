package request;

import model.User;

public class UserBinder {

    private UserBinder() {
        throw new AssertionError();
    }

    public static User from(RequestLine requestLine) {
        Path path = requestLine.getPath();
        HttpMethod httpMethod = requestLine.getHttpMethod();

        if (!validateRequestLine(path, httpMethod)) return null;

        String userId = path.getQueryValue("userId");
        String password = path.getQueryValue("password");
        String name = path.getQueryValue("name");
        String email = path.getQueryValue("email");

        return new User(userId, password, name, email);
    }

    private static Boolean validateRequestLine(Path path, HttpMethod httpMethod) {
        if (!httpMethod.isGet()) return false;
        if (!path.isUser()) return false;
        return path.isCreate();
    }
}
