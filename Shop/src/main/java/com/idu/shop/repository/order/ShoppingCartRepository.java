package com.idu.shop.repository.order;

import com.idu.shop.domain.order.ShoppingCart;
import com.idu.shop.domain.product.ProductImage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShoppingCartRepository {
    SqlSession sqlSession;

    @Autowired
    public ShoppingCartRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.ShoppingCartMapper";
    public List<ShoppingCart> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public List<ShoppingCart> readByMemberNoList(int memberNo) {
        return sqlSession.selectList(namespace + ".readList", memberNo);
    }
    public ShoppingCart read(ShoppingCart shoppingCart) {
        return sqlSession.selectOne(namespace + ".read", shoppingCart);
    }
    public ShoppingCart readRecentlyAddedRow() {
        return sqlSession.selectOne(namespace + ".readRecentlyAddedRow");
    }
    public boolean insert(ShoppingCart shoppingCart) {
        return sqlSession.insert(namespace + ".insert", shoppingCart) == 1;
    }
    public boolean update(ShoppingCart shoppingCart) {
        return sqlSession.update(namespace + ".update", shoppingCart) == 1;
    }
    public boolean delete(int id) {
        return sqlSession.delete(namespace + ".delete", id) == 1;
    }
}