package com.idu.shop.service.member;

import com.idu.shop.domain.member.Member;
import com.idu.shop.repository.member.MemberRepository;
import com.idu.shop.service.ServiceContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService extends ServiceContainer {
    MemberRepository repository;
    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }
    public List<Member> readList() {return repository.readList();}
    public Member login(Member member) {return repository.login(member);}
    public Member read(Member member) {return repository.read(member);}
    public Member readByEmail(String email) {return repository.readByEmail(email);}
    public Member findEmail(Member member) {return repository.findEmail(member);}
    public boolean insert(Member member) {return repository.insert(member);}
    public boolean update(Member member) {return repository.update(member);}
    public boolean delete(Member member) {return repository.delete(member);}
    public boolean updatePw(Member member) {return repository.updatePw(member);}
    public int getId(String email) {return repository.getId(email);}
}
