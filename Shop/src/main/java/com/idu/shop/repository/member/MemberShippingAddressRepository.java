package com.idu.shop.repository.member;

import com.idu.shop.domain.member.MemberShippingAddress;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberShippingAddressRepository {
    SqlSession sqlSession;

    @Autowired
    public MemberShippingAddressRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.MemberShippingAddressMapper";
    public List<MemberShippingAddress> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public List<MemberShippingAddress> readMemberList(int memberNo) {
        return sqlSession.selectList(namespace + ".readMemberList", memberNo);
    }
    public MemberShippingAddress read(int shippingAddressNo) {
        return sqlSession.selectOne(namespace + ".read", shippingAddressNo);
    }
    public boolean insert(MemberShippingAddress memberShippingAddress) {
        return sqlSession.insert(namespace + ".insert", memberShippingAddress) == 1;
    }
    public boolean update(MemberShippingAddress memberShippingAddress) {
        return sqlSession.update(namespace + ".update", memberShippingAddress) == 1;
    }
    public boolean delete(MemberShippingAddress memberShippingAddress) {
        return sqlSession.delete(namespace + ".delete", memberShippingAddress) == 1;
    }
}