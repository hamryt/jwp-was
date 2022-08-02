package request;

import model.User;

public class UserBinder {

    private UserBinder() {
        throw new AssertionError();
    }

    public static User from(Path path) {

        if (!path.isUser()) return null;

        String userId = path.getQueryValue("userId");
        String password = path.getQueryValue("password");
        String name = path.getQueryValue("name");
        String email = path.getQueryValue("email");

        return new User(userId, password, name, email);
    }
}
