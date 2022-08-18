package webserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SessionAttribute {

    private final Map<String, Object> attribute = new HashMap<>();

    public void set(String name, String value) {
        attribute.put(name, value);
    }

    public Object get(String name) {
        return attribute.get(name);
    }

    public void remove(String name) {
        attribute.remove(name);
    }

    public void invalidate() {
        attribute.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionAttribute that = (SessionAttribute) o;
        return Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute);
    }
}
