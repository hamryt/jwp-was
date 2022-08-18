package webserver.session;

import java.util.Objects;

public class SessionId {

    private final String id;

    public SessionId(SessionIdGenerator sessionIdGenerator) {
        this.id = sessionIdGenerator.generate();
    }

    public String get() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionId sessionId = (SessionId) o;
        return Objects.equals(id, sessionId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
