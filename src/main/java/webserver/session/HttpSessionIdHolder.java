package webserver.session;

/**
 * 세션을 생성하고 세셩 ID를 관리하는 클래스
 */
public class HttpSessionIdHolder {

    private final ThreadLocal<String> sessionId = new ThreadLocal<>();
    private final SessionIdGenerator sessionIdGenerator;

    public HttpSessionIdHolder(SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }

    public void generate(String sessionId) {
        if (HttpSessionContext.has(sessionId)) {
            this.sessionId.set(sessionId);
        }

        createSession();
    }

    private void createSession() {
        if (sessionId.get() == null) {
            HttpSession httpSession = new HttpSession(sessionIdGenerator);
            HttpSessionContext.add(httpSession);
            this.sessionId.set(httpSession.getId());
        }
    }

}
