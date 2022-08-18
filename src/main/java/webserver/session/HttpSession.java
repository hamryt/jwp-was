package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpSession {

    private final String id;
    private final Map<String, Object> attribute = new HashMap<>();

    public HttpSession(SessionIdGenerator sessionIdGenerator) {
        this.id = sessionIdGenerator.generate();
    }

    public void setAttribute(String name, String value) {
        attribute.put(name, value);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
        attribute.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpSession that = (HttpSession) o;
        return Objects.equals(id, that.id) && Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attribute);
    }
}
