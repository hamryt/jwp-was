package request;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST, PUT, PATCH, DELETE;

    public static HttpMethod from(String request) {
        return Arrays.stream(values())
            .filter(it -> it.name().equals(request))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 HttpMethod 요청입니다."));
    }

    public Boolean isGet() {
        return this.equals(GET);
    }

    public Boolean isPost() {
        return this.equals(POST);
    }


}
