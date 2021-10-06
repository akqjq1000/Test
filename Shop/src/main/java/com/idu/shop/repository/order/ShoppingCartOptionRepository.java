package com.idu.shop.repository.order;

import com.idu.shop.domain.order.ShoppingCartOption;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShoppingCartOptionRepository {
    SqlSession sqlSession;
    @Autowired
    public ShoppingCartOptionRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    String namespace = "com.idu.shop.ShoppingCartOptionMapper";

    public int insert(ShoppingCartOption sco) {
        return sqlSession.insert(namespace + ".insert", sco);
    }
    public List<ShoppingCartOption> read(int shoppingCartNo) {
        return sqlSession.selectList(namespace + ".read", shoppingCartNo);
    }
    public boolean delete(int id) {return sqlSession.delete(namespace + ".delete", id) == 1;}
}
