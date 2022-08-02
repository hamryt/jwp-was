package request;

import model.User;

import java.util.Map;

public class UserBinder {

    private UserBinder() {
        throw new AssertionError();
    }

    public static User from(Map<String, String> path) {

        String userId = path.get("userId");
        String password = path.get("password");
        String name = path.get("name");
        String email = path.get("email");

        return new User(userId, password, name, email);
    }
}
