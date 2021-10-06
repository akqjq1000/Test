package com.idu.shop.repository.order;

import com.idu.shop.domain.order.Delivery;
import com.idu.shop.domain.product.ProductImage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeliveryRepository {
    SqlSession sqlSession;

    @Autowired
    public DeliveryRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.DeliveryMapper";
    public List<Delivery> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public Delivery read(String orderSequence) {
        return sqlSession.selectOne(namespace + ".read", orderSequence);
    }
    public Delivery readById(int deliveryNo) {return sqlSession.selectOne(namespace + ".readById", deliveryNo);}
    public boolean insert(Delivery delivery) {
        return sqlSession.insert(namespace + ".insert", delivery) == 1;
    }
    public boolean update(Delivery delivery) {
        return sqlSession.update(namespace + ".update", delivery) == 1;
    }
    public boolean delete(Delivery delivery) {
        return sqlSession.delete(namespace + ".delete", delivery) == 1;
    }
}