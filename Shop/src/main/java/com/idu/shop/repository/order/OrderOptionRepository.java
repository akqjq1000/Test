package com.idu.shop.repository.order;

import com.idu.shop.domain.order.OrderOption;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderOptionRepository {
    SqlSession sqlSession;
    @Autowired
    public OrderOptionRepository (SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    private String namespace = "com.idu.shop.OrderOptionMapper";
    public List<OrderOption> readList() {return sqlSession.selectList(namespace + ".readList");}
    public List<OrderOption> readByOrderNo(int orderNo) {return sqlSession.selectList(namespace + ".read", orderNo);}
    public int insert(OrderOption orderOption) {return sqlSession.insert(namespace + ".insert", orderOption);}
}
