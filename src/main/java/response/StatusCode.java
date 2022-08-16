package response;

/**
 * HTTP/1.1 200 OK
 * Content-Type: text/html
 * Set-Cookie: logined=true; Path=/
 */

public enum StatusCode {
    OK(200, "OK"),
    FOUND(302, "Found");

    private final int code;
    private final String reasonPhrase;

    StatusCode(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public String message() {
        return code + " " + reasonPhrase;
    }
}
