package request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * GET /index.html HTTP/1.1
 * Host: localhost:8080
 * Connection: keep-alive
 * Accept:
 * Cookie:logined=true
 */

public class RequestHeader {

    private static final String HOST = "Host";
    private static final String CONNECTION = "Connection";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";
    private static final String COOKIE = "Cookie";

    private static final String HEADER_DELIMITER = ": ";

    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader() {
    }

    public void add(String header) {
        if (header == null || header.isBlank()) {
            return;
        }

        String[] keyAndValue = header.split(HEADER_DELIMITER);
        if (keyAndValue.length == 1) {
            return;
        }

        headers.put(keyAndValue[0].trim(), keyAndValue[1].trim());
    }

    public String getHost() {
        return headers.get(HOST);
    }

    public String getConnection() {
        return headers.get(CONNECTION);
    }

    public int getContentLength() {
        return Integer.parseInt(headers.get(CONTENT_LENGTH));
    }

    public String getContentType() {
        return headers.get(CONTENT_TYPE);
    }

    public String getAccept() {
        return headers.get(ACCEPT);
    }

    public String get(String key) {
        return headers.get(key);
    }

    public boolean hasRequestBody() {
        return headers.containsKey(CONTENT_LENGTH) && getContentLength() > 0;
    }

    public boolean hasCookie(String cookieName) {
        return headers.containsKey(COOKIE) &&
            Arrays.stream(getCookies())
                .filter(cookie -> cookie.startsWith(cookieName))
                .anyMatch(cookie -> cookie.split("=").length == 2);
    }

    private String getCookie(String cookieName) {
        if (!hasCookie(cookieName)) {
            return null;
        }

        return Arrays.stream(getCookies())
            .filter(cookie -> cookie.startsWith(cookieName))
            .findFirst()
            .map(cookie -> cookie.split("=")[1])
            .orElse(null);
    }

    private String[] getCookies() {
        return headers.get(COOKIE).split(";");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequestHeader that = (RequestHeader) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}
