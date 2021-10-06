package com.idu.shop.repository.member;

import com.idu.shop.domain.member.MemberCoupon;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberCouponRepository {
    SqlSession sqlSession;

    @Autowired
    public MemberCouponRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.MemberCouponMapper";
    public List<MemberCoupon> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public MemberCoupon read(MemberCoupon memberCoupon) {
        return sqlSession.selectOne(namespace + ".read", memberCoupon);
    }
    public List<MemberCoupon> readByMemberNo(int memberNo) {
        return sqlSession.selectList(namespace + ".readByMemberNo", memberNo);
    }
    public boolean insert(MemberCoupon memberCoupon) {
        return sqlSession.insert(namespace + ".insert", memberCoupon) == 1;
    }
    public boolean update(MemberCoupon memberCoupon) {
        return sqlSession.update(namespace + ".update", memberCoupon) == 1;
    }
    public boolean delete(MemberCoupon memberCoupon) {
        return sqlSession.delete(namespace + ".delete", memberCoupon) == 1;
    }
}
