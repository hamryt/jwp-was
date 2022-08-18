package webserver.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {

    @DisplayName("세션을 생성한다")
    @Test
    void create_session() {
        HttpSession actual = new HttpSession(() -> "12345");

        HttpSession expected = new HttpSession(() -> "12345");

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("세션에 특정한 이름으로 객체를 저장할 수 있다")
    @Test
    void set_attribute() {
        HttpSession actual = new HttpSession(() -> "12345");
        actual.setAttribute("name", "value");

        HttpSession expected = new HttpSession(() -> "12345");
        expected.setAttribute("name", "value");

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("세션에 특정한 이름으로 객체를 불러올 수 있다")
    @Test
    void get_attribute() {
        HttpSession actual = new HttpSession(() -> "12345");
        actual.setAttribute("name", "value");

        assertThat(actual.getAttribute("name")).isEqualTo("value");
    }
}