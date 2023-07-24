package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 *
 * @return 이  null 이면 실패
 *
 * 로그인 아이디를 던져서 회원이 있는지 없는지 찾아야 한다
 * Optional.get() 을 하면 Optional 안에있는게 꺼내져 나온다  만약에 없으면 예외가 터진다
 *
 * 만약 member 의 페스워드가 입력한 password 와 같으면  return member 만약 다르면 return null 을 반환한다
 *
 *         Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
 *         Member member = findMemberOptional.get();
 *         if (member.getPassword().equals(password) ){
 *             return member;
 *         }else {
 *             return null;
 *         }
 *
 *         //Optional 안에 들어있는 Member 가 password 랑 같은가 같으면 쓰고 다르면 null 을 반환해
 *         Optional<Member> byLoginId = memberRepository.findByLoginId(loginId);
 *         return byLoginId.filter(m->m.getPassword().equals(password))
 */

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId,String password){

        //findByLoginId 에 리턴된 결과를 바로 .찍어서 가져다 쓰는것
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

}
