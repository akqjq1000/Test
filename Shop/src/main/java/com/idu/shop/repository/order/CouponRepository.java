package com.idu.shop.repository.order;

import com.idu.shop.domain.order.Coupon;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouponRepository {
    SqlSession sqlSession;
    @Autowired
    public CouponRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    String namespace = "com.idu.shop.CouponMapper";

    public List<Coupon> readList() {return sqlSession.selectList(namespace + ".readList");}
    public Coupon readById(int id) {return sqlSession.selectOne(namespace + ".readById", id);}
    public boolean insert(Coupon coupon) {return sqlSession.insert(namespace + ".insert", coupon) == 1;}
    public boolean delete(int id) {return sqlSession.delete(namespace + ".delete", id) == 1;}
}
