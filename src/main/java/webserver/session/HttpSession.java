package webserver.session;

import java.util.Objects;

public class HttpSession {

    private final SessionId id;
    private final SessionAttribute attribute = new SessionAttribute();

    public HttpSession(SessionIdGenerator sessionIdGenerator) {
        this.id = new SessionId(sessionIdGenerator);
    }

    public void setAttribute(String name, String value) {
        attribute.set(name, value);
    }

    public Object getAttribute(String name) {
        return attribute.get(name);
    }

    public void removeAttribute(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
        attribute.invalidate();
    }

    public String getId() {
        return id.get();
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
