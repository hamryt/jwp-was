package webserver.handler;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;
import request.HttpRequestParser;
import request.ProtocolVersion;
import response.HttpResponse;
import response.ResponseHeader;
import response.ResponseLine;
import response.StatusCode;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static response.ResponseBody.EMPTY_RESPONSE_BODY;

class LoginControllerTest {
    private final LoginController controller = new LoginController();

    @BeforeEach
    void setUp() {
        DataBase.addUser(new User("admin", "pass", "administrator", "admin@email.com"));
    }

    @DisplayName("로그인 성공 후 페이지 이동 응답 객체를 반환한다")
    @Test
    void after_login_success_return_redirect_response() throws IOException {
        String httpRequestMessage = "POST /user/login HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Content-Length: 26\r\n"
            + "\r\n"
            + "userId=admin&password=pass\n\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        HttpResponse actual = controller.handle(httpRequest);

        HttpResponse expected = loginResponse("/index.html", "true");

        assertThat(actual).isEqualTo(expected);
    }

    private static HttpResponse loginResponse(String location, String logined) {
        ResponseHeader headers = new ResponseHeader();
        headers.add("Location", location);
        headers.add("Set-Cookie", "logined=" + logined + "; Path=/");
        return new HttpResponse(new ResponseLine(new ProtocolVersion("HTTP/1.1"), StatusCode.FOUND), headers, EMPTY_RESPONSE_BODY);
    }

    private BufferedReader getBufferedReader(final String httpRequestMessage) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestMessage.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}