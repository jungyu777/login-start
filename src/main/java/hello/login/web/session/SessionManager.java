package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "MY_SESSION_ID";
    /**
     * 동시에 여러오쳥 여러 쓰레드가 접근하게 되면 동시성 이슈떄문에  ConcurrentHashMap 을 사용해야한다
     */
    private Map<String, Objects> sessionStore = new ConcurrentHashMap<>();
    /**
     * 세션 생성
     * sessionId 생성 (임의의 추정 불가능한 랜덤값)
     * 세션 저장소에 sessionId 와 보관할 값 저장
     * sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
     */
    public void createSession(Objects value, HttpServletResponse response){
        //세션 id를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId,value);

        //쿠키생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);


    }

    /**
     * 세션조회
     *
     */
    public Objects getSession(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(SESSION_COOKIE_NAME)){
                return sessionStore.get(cookie.getValue());
            }
        }
        return null;
    }
    public Cookie findCookie(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
    }



}
