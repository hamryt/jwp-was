package response;

import java.util.Objects;

public class ResponseBody {

    public static final ResponseBody EMPTY_RESPONSE_BODY = new ResponseBody("");

    private final String body;

    public ResponseBody(String body) {
        this.body = body;
    }

    public byte[] getBytes() {
        return body.getBytes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseBody that = (ResponseBody) o;
        return Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}
