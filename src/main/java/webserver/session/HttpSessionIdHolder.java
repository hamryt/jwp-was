package webserver.session;

/**
 * 세션을 생성하고 세셩 ID를 관리하는 클래스
 */
public class HttpSessionIdHolder {

    private static final ThreadLocal<String> SESSION_ID = new ThreadLocal<>();

    public HttpSessionIdHolder() {
        throw new AssertionError();
    }

    public static void generate(String sessionId) {
        SESSION_ID.set(sessionId);
    }

    public static String getSessionId() {
        return SESSION_ID.get();
    }

    public static void invalidate() {
        SESSION_ID.remove();
    }
}
