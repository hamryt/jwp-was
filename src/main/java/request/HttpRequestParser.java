package request;

import utils.IOUtils;
import webserver.session.HttpSession;
import webserver.session.HttpSessionContext;
import webserver.session.HttpSessionIdHolder;
import webserver.session.UUIDSessionIdGenerator;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestParser {

    private static final String SESSION_COOKIE_NAME = "JWP_SESSION_ID";

    private HttpRequestParser() {
        throw new AssertionError();
    }

    public static HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        RequestLine requestLine = new RequestLine(bufferedReader.readLine());
        RequestHeader requestHeader = parseHeader(bufferedReader);
        RequestBody requestBody = parseBody(requestHeader, bufferedReader);

        createSession(requestHeader.getCookie(SESSION_COOKIE_NAME));

        return new HttpRequest(requestLine, requestHeader, requestBody);
    }

    private static void createSession(String sessionId) {
        HttpSessionIdHolder.generate(getCookieSessionIdOrNewSessionId(sessionId));
    }

    private static String getCookieSessionIdOrNewSessionId(String sessionId) {
        if (!HttpSessionContext.has(sessionId)) {
            HttpSession httpSession = new HttpSession(new UUIDSessionIdGenerator());
            HttpSessionContext.add(httpSession);
            return httpSession.getId();
        }

        return sessionId;
    }

    private static RequestHeader parseHeader(BufferedReader bufferedReader) throws IOException {
        RequestHeader requestHeader = new RequestHeader();
        String header = bufferedReader.readLine();

        while (!header.isEmpty()) {
            requestHeader.add(header);
            header = bufferedReader.readLine();
        }

        return requestHeader;
    }

    private static RequestBody parseBody(RequestHeader requestHeader, BufferedReader bufferedReader) throws IOException {
        if (requestHeader.hasRequestBody()) {
            String body = IOUtils.readData(bufferedReader, requestHeader.getContentLength());

            return new RequestBody(body);
        }

        return new RequestBody("");
    }
}
