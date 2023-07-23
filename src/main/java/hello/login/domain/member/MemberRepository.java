package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long,Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용

    //저장
    public Member save(Member member){
        member.setId(++sequence);
        log.info("save : member={} ", member);
        store.put(member.getId(),member); //저장소에 저장 아이디로 찾고 member를 집어 넣어준다
        return member;
    }

    public Member findById(Long id){
        return store.get(id); //반환하면 Map 이기 떄문에 key 넣으면 value 가 바로 튀어 나온다
    }
    //로그인 아이디 찾는 메소드
    public Optional<Member> findByLoginId(String loginId){
        return findAll().stream() // list 를 stream 으로 바꾼다
                .filter(m->m.getLoginId().equals(loginId))
                //거기서 filter 로  m->m.getLoginId().equals(loginId) 이조건에 만족하는애만  다음단계로 넘어간다
                .findFirst();
    }

    /**
     *
     * Optional 이란 Optional이란 통이 있고 그 안에 회원객체가 있을수도 없을수도 있다
     * 그냥 껍데기 통이 하나 있다고 생각하자
     * 값이 null 로 반환해야 되는 상황에서는 Optional.empty() 로 반환한다
     */

    //하나를 전체 찾는 메소드
    public List<Member> findAll(){
       return new ArrayList<>(store.values());//Member가 List로 변환된다
    }
    //테스트할떄 필요한 초기화 메소드
    public void clearStore(){
        store.clear();
    }

}
