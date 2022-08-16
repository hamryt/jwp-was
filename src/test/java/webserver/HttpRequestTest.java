package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private RestTemplate restTemplate;

    @BeforeEach
    void init() {
        restTemplate = new RestTemplate();
    }

    @Test
    void request_resttemplate() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("회원가입 요청")
    @Test
    void create_user() {
        final ResponseEntity<String> 회원가입_요청 = 회원가입_요청("admin", "password");

        assertThat(회원가입_요청.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(회원가입_요청.getHeaders().get("Location")).containsExactly("/index.html");

    }

    private ResponseEntity<String> 회원가입_요청(final String userId, final String password) {
        final String createUserParams = String.format("userId=%s&password=%s&name=관리자&email=admin@email.com", userId, password);

        return restTemplate.postForEntity("http://localhost:8080/user/create", createUserParams, String.class);
    }
}
