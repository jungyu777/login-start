package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    //폼을 보여주는 GET방식 메소드
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "login/loginForm";
    }
    //실제 로그인 처리가 되는 로직을 보여주는 메소드
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,HttpServletResponse response){
        if (bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        //로그인맴버가 null 이면 회원을 못찾거나 또는 아이디 페스워드가 안맞는다
        if (loginMember == null){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가  맞지 않습니다.");
            return "login/loginForm";
        }
        /**
         * loginMember.= String 타입이 들어가야한다
         *  getId() = long 타입
         *  그래서  String.valueOf(loginMember.getId()) 로 문자로 변환해준다
         */
        //로그인 성공 처리

        //쿠키에 시간 정보를 주지 않으면  세션쿠키(브라우저 종료시 모두 종료)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        //쿠키를 생성한걸을 서버에서 Http 응답에서  보낼떄 리스판스 넣어서 보내주어야 한다. 그래서 HttpServletResponse  response 추가해준다
        response.addCookie(idCookie);
        return "redirect:/";
    }

    /**
     *  쿠키 를 지우는 방법 쿠키의 시간을 다 없애 버리면된다
     */
    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
