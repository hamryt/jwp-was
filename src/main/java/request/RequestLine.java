package request;


public class RequestLine {

    private static final String DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;

    private final HttpMethod httpMethod;
    private final Path path;
    private final ProtocolVersion protocol;

    public RequestLine(String requestLine) {
        String[] splitRequestLine = requestLine.split(DELIMITER);
        httpMethod = HttpMethod.from(splitRequestLine[HTTP_METHOD_INDEX]);
        path = new Path(splitRequestLine[PATH_INDEX]);
        protocol = new ProtocolVersion(splitRequestLine[PROTOCOL_INDEX]);
    }

    public String getHttpMethod() {
        return httpMethod.name();
    }

    public String getName() {
        return path.getName();
    }

    public Path getPath() {
        return path;
    }

    public Boolean isUserRequest() {
        return path.isUser();
    }

    public String getQueryValueOf(String key) {
        return path.getQueryValue(key);
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }
}
