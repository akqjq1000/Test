package com.idu.shop.repository.order;

import com.idu.shop.domain.order.Payment;
import com.idu.shop.domain.product.ProductImage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentRepository {
    SqlSession sqlSession;

    @Autowired
    public PaymentRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.PaymentMapper";
    public List<Payment> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public List<Payment> readByMemberNo(int memberNo) { return sqlSession.selectList(namespace + ".readByMemberNo", memberNo);}
    public Payment read(Payment payment) {
        return sqlSession.selectOne(namespace + ".read", payment);
    }
    public Payment readByOrderSequence(String orderSequence) {
        return sqlSession.selectOne(namespace + ".readByOrderSequence", orderSequence);
    }
    public boolean insert(Payment payment) {
        return sqlSession.insert(namespace + ".insert", payment) == 1;
    }
    public boolean update(Payment payment) {
        return sqlSession.update(namespace + ".update", payment) == 1;
    }
    public boolean delete(Payment payment) {
        return sqlSession.delete(namespace + ".delete", payment) == 1;
    }
}