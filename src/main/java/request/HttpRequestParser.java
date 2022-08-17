package request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestParser {

    private HttpRequestParser() {
        throw new AssertionError();
    }

    public static HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        RequestLine requestLine = new RequestLine(bufferedReader.readLine());
        RequestHeader requestHeader = parseHeader(bufferedReader);
        RequestBody requestBody = parseBody(requestHeader, bufferedReader);

        return new HttpRequest(requestLine, requestHeader, requestBody);
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
