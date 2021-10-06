package com.idu.shop.repository.order;

import com.idu.shop.domain.order.Order;
import com.idu.shop.domain.product.ProductImage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {
    SqlSession sqlSession;

    @Autowired
    public OrderRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.OrderMapper";
    public List<Order> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public List<Order> readBySerialNumber(String serialNumber) { return sqlSession.selectList(namespace + ".readBySerialNumber", serialNumber);}
    public Order read(Order order) {
        return sqlSession.selectOne(namespace + ".read", order);
    }
    public boolean insert(Order order) {
        return sqlSession.insert(namespace + ".insert", order) == 1;
    }
    public boolean update(Order order) {
        return sqlSession.update(namespace + ".update", order) == 1;
    }
    public boolean delete(Order order) {
        return sqlSession.delete(namespace + ".delete", order) == 1;
    }
}