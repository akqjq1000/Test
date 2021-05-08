package iducs.springboot.ab200412000.comsoblog.service;

import iducs.springboot.ab200412000.comsoblog.domain.Member;
import iducs.springboot.ab200412000.comsoblog.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    private MemberRepository memberRepository;
    @Autowired
    public MemberServiceImpl(MemberRepository spring) { // MemberRepository 유형의 등록된 객체를 Spring Framework이 자동 주입
        this.memberRepository = spring;
    }

    @Override
    public Member getMember(long id) {
        return null;
    }

    @Override
    public Member getMemberByEmail(String email) {
        Member member = new Member();
        member.setEmail(email);
        return memberRepository.readByEmail(member);
    }

    @Override
    public List<Member> getMembers() {
        return null;
    }

    @Override
    public List<Member> getMembersByPage(int index, int size) {
        return null;
    }

    @Override
    public int postMember(Member member) {
        return 0;
    }

    @Override
    public int putMember(Member member) {
        return 0;
    }

    @Override
    public int deleteMember(Member member) {
        return 0;
    }
}
