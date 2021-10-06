package com.idu.shop.repository.member;

import com.idu.shop.domain.member.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {
    SqlSession sqlSession;

    @Autowired
    public MemberRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.MemberMapper";
    public List<Member> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public Member readByEmail(String email) {return sqlSession.selectOne(namespace + ".readByEmail", email);}
    public Member login(Member member) {return sqlSession.selectOne(namespace + ".login", member);}
    public Member read(Member member) {
        return sqlSession.selectOne(namespace + ".read", member);
    }
    public Member findEmail(Member member) { return sqlSession.selectOne(namespace + ".findEmail", member);}
    public boolean insert(Member member) {
        return sqlSession.insert(namespace + ".create", member) == 1;
    }
    public boolean update(Member member) {
        return sqlSession.update(namespace + ".update", member) == 1;
    }
    public boolean delete(Member member) {
        return sqlSession.delete(namespace + ".delete", member) == 1;
    }
    public boolean updatePw(Member member) { return sqlSession.update(namespace + ".updatePw", member) == 1;}
    public int getId(String email) {return sqlSession.selectOne(namespace + ".readByEmailOnId", email);}
}