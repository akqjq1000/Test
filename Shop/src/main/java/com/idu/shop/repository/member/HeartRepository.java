package com.idu.shop.repository.member;

import com.idu.shop.domain.member.Heart;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HeartRepository {
    SqlSession sqlSession;
    @Autowired
    public HeartRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    String namespace = "com.idu.shop.HeartMapper";

    public List<Heart> readList() {return sqlSession.selectList(namespace + ".readList");}
    public List<Heart> readByMemberNo(int memberNo) {return sqlSession.selectList(namespace + ".readByMemberNo", memberNo);}
    public boolean insert(Heart heart) {return sqlSession.insert(namespace + ".insert", heart) == 1;}
    public boolean delete(int productNo) {return sqlSession.delete(namespace + ".delete", productNo) == 1;}
}
