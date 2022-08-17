package response;

import org.apache.coyote.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.ProtocolVersion;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @DisplayName("정상 응답 상태 코드와 응답 본문을 가진 HttpResponse 객체를 생성한다")
    @Test
    void response_ok_with_body() {
        ResponseLine responseLine = new ResponseLine(new ProtocolVersion("HTTP/1.1"), StatusCode.OK);

        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.add("Content-Type", "text/html");
        responseHeader.add("Content-Length", "39");

        ResponseBody responseBody = new ResponseBody("<html><body>Hello, World!</body></html>");

        HttpResponse response = new HttpResponse(responseLine, responseHeader, responseBody);
        assertThat(response).isEqualTo(new HttpResponse(responseLine, responseHeader, responseBody));
    }

    @DisplayName("페이지 이동 상태 코드와 응답 본문이 없는 HttpResponse 객체를 생성한다")
    @Test
    void response_found_without_body() {
        ResponseLine responseLine = new ResponseLine(new ProtocolVersion("HTTP/1.1"), StatusCode.FOUND);

        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.add("Location", "/index.html");

        ResponseBody responseBody  = ResponseBody.EMPTY_RESPONSE_BODY;
        HttpResponse expected = new HttpResponse(responseLine, responseHeader, responseBody);

        HttpResponse actual = HttpResponse.redirect("/index.html");

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("응답 헤더에 쿠키를 추가한다")
    @Test
    void add_cookie_to_response_headers() {
        ResponseLine responseLine = new ResponseLine(new ProtocolVersion("HTTP/1.1"), StatusCode.FOUND);
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.add("Location", "/index.html");
        responseHeader.add("Set-Cookie", "logined=true; Path=/");
        ResponseBody responseBody = ResponseBody.EMPTY_RESPONSE_BODY;

        HttpResponse expected = new HttpResponse(responseLine, responseHeader, responseBody);
        HttpResponse actual = HttpResponse.redirect("/index.html")
            .addCookie("logined", "true");

        assertThat(actual).isEqualTo(expected);
    }
}