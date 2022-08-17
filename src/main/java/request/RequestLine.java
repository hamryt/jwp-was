package request;


import java.util.Objects;

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

    public RequestLine(HttpMethod httpMethod, Path path, ProtocolVersion protocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
    }

    public static RequestLine parse(String requestLine) {
        validate(requestLine);

        String[] tokens = requestLine.split(DELIMITER);

        return of(tokens[HTTP_METHOD_INDEX], tokens[PATH_INDEX], tokens[PROTOCOL_INDEX]);
    }

    public static RequestLine of(String httpMethod, String path, String protocol) {
        return new RequestLine(HttpMethod.from(httpMethod), new Path(path), new ProtocolVersion(protocol));
    }

    private static void validate(String requestLine) {
        if (requestLine == null || requestLine.isBlank()) {
            throw new IllegalArgumentException("빈 문자열은 파싱할 수 없습니다.");
        }
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return httpMethod == that.httpMethod && Objects.equals(path, that.path) && Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, path, protocol);
    }
}
