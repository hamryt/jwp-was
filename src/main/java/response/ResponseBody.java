package response;

public class ResponseBody {

    public static final ResponseBody EMPTY_RESPONSE_BODY = new ResponseBody("");

    private final String body;

    public ResponseBody(String body) {
        this.body = body;
    }

    public byte[] getBytes() {
        return body.getBytes();
    }
}
