package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStroe = new ConcurrentHashMap<>();

    /**
     * session create
     */
    public void createSession(Object value, HttpServletResponse response) {

        // 세션 id를 생성하고 값을 session에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStroe.put(sessionId, value);

        //쿠키 생성
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    /**
     * find session
     */
    public Object getSession(HttpServletRequest request) {
        Cookie cookie = findCookie(request,SESSION_COOKIE_NAME);
        if (cookie == null) {
            return null;
        }else{
          return sessionStroe.get(cookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

    /**
     * session 만료
     */
    public void expire(HttpServletRequest request) {
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);
        if (cookie != null) {
            sessionStroe.remove(cookie.getValue());
        }
    }
}
