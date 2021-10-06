package com.idu.shop.repository.order;

import com.idu.shop.domain.order.CancelOrder;
import com.idu.shop.domain.product.ProductImage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CancelOrderRepository {
    SqlSession sqlSession;

    @Autowired
    public CancelOrderRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.CancelOrderMapper";
    public List<CancelOrder> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public CancelOrder read(CancelOrder cancelOrder) {
        return sqlSession.selectOne(namespace + ".read", cancelOrder);
    }
    public boolean insert(CancelOrder cancelOrder) {
        return sqlSession.insert(namespace + ".insert", cancelOrder) == 1;
    }
    public boolean update(CancelOrder cancelOrder) {
        return sqlSession.update(namespace + ".update", cancelOrder) == 1;
    }
    public boolean delete(CancelOrder cancelOrder) {
        return sqlSession.delete(namespace + ".delete", cancelOrder) == 1;
    }
}