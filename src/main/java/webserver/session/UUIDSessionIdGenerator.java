package webserver.session;

import javax.websocket.Session;
import java.util.UUID;

public class UUIDSessionIdGenerator implements SessionIdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
