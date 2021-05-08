package iducs.springboot.ab200412000.comsoblog.repository;

import iducs.springboot.ab200412000.comsoblog.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepositorylmpl implements MemberRepository {
    /*
    @Autowired
    MemberRepository memberRepository; // DI : 의존성 주입
    */
    //DB 가져올 데이터 객체
    Member root = new Member(Long.getLong("3"), "induk@induk.ac.kr", "cometrue", "induk", "9507620", "nowon");
    Member user = new Member(Long.getLong("4"), "user@induk.ac.kr", "cometrue", "user", "9507638", "wolgye");

    // DB 사용을 위한 ataSource 객체를 주입해야함.

    @Override
    public int create(Member member) {
        return 0;
    }

    @Override
    public Member readById(Member member) {
        return null;
    }

    @Override
    public Member readByEmail(Member member) {
        if(member.getEmail().equals(root.getEmail()))
            return root;
        else if(member.getEmail().equals(user.getEmail()))
            return user;
        else
            return null;
    }

    @Override
    public List<Member> readMembers() {
        return null;
    }

    @Override
    public int update(Member member) {
        return 0;
    }

    @Override
    public int delete(Member member) {
        return 0;
    }

}
