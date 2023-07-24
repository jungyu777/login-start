package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

   // @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * 로그인이 되면 로그인하라는 화면을 보여주어야 하고
     * 로그인이 안되면  로그인되어있는 화면을 보여주어야 한다
     * 그러기 위해선 먼져 쿠키를 받아야 한다
     * 스프링이 제공하는 @CookieValue (name = "memberId",required = false)
     * required = false 를 넣어주는 이유는 로그인 안한 사용자도 들어와야 하기 떄문이다
     * 쿠키값은 String 이지만  스프링이 알아서 컨버팅해준다
     */
    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId",required = false)Long memberId, Model model){
        if (memberId == null){
            return "home";
        }
        //로그인에 성공한 사용자  쿠키가 있는자  쿠키가 정상적으로 회원을 찾을수 있는지보는 로직
        Member loginMember = memberRepository.findById(memberId);
        //만약 DB에 없을수도 있기떄문에
        if (loginMember ==null ){
            return "home";
        }
        //정보가 있다면  model.addAttribute 에 담은 다음에 loginHome 으로가버렷
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
}