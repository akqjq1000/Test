package com.idu.shop.repository.order;

import com.idu.shop.domain.order.Courier;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourierRepository {
    SqlSession sqlSession;
    @Autowired
    public CourierRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    String namespace = "com.idu.shop.CourierMapper";
    public List<Courier> readList() {return sqlSession.selectList(namespace + ".readList");}
    public Courier read(int id) {return sqlSession.selectOne(namespace + ".read", id);}
    public boolean insert(Courier courier) {return sqlSession.insert(namespace + ".insert", courier) == 1;}
}
