package webserver.handler;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.ContentType;
import request.HttpRequest;
import request.HttpRequestParser;
import request.ProtocolVersion;
import response.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class UserListControllerTest {
    private final UserListController controller = new UserListController();

    @BeforeEach
    void setUp() {
        DataBase.addUser(new User("admin", "pass", "administrator", "admin@email.com"));
    }

    @DisplayName("로그인 상태에서 사용자 목록 조회시 사용자 목록 페이지 이동 응답 객체를 반환한다")
    @Test
    void after_login_access_user_list_return_ok_response() throws IOException {
        // given
        String httpRequestMessage = "GET /user/list.html HTTP/1.1\r\n"
            + "Host: localhost:8080\r\n"
            + "Connection: keep-alive\r\n"
            + "Cookie: logined=true\r\n"
            + "\r\n";

        BufferedReader bufferedReader = getBufferedReader(httpRequestMessage);
        HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);

        //when
        HttpResponse actual = controller.handle(httpRequest);

        //then
        String responseBody = "\n"
            + "<tr>\n"
            + "  <td>1</td>\n"
            + "  <td>administrator</td>\n"
            + "</tr>\n"
            + "\n";

        ResponseHeader headers = new ResponseHeader();
        headers.add("Content-Type", ContentType.HTML.getMediaType());
        headers.add("Content-Length", String.valueOf(responseBody.getBytes().length));
        HttpResponse expected = new HttpResponse(new ResponseLine(new ProtocolVersion("HTTP/1.1"), StatusCode.OK), headers, new ResponseBody(responseBody));

        assertThat(actual).isEqualTo(expected);

    }

    private BufferedReader getBufferedReader(String httpRequestMessage) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(httpRequestMessage.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}