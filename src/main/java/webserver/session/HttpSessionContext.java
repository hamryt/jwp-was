package webserver.session;

import java.util.HashMap;
import java.util.Map;

/**
 * 전역적으로 세션을 관리하는 클래스
 */
public class HttpSessionContext {

    private static final Map<String, HttpSession> SESSION_CONTEXT = new HashMap<>();

    private HttpSessionContext() {
        throw new AssertionError();
    }

    public static void add(HttpSession session) {
        SESSION_CONTEXT.put(session.getId(), session);
    }

    public static HttpSession get(String id) {
        return SESSION_CONTEXT.get(id);
    }

    public static boolean has(String sessionId) {
        return SESSION_CONTEXT.containsKey(sessionId);
    }
}
