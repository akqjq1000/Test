package com.idu.shop.repository.order;

import com.idu.shop.domain.order.Review;
import com.idu.shop.domain.product.ProductImage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepository {
    SqlSession sqlSession;

    @Autowired
    public ReviewRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.ReviewMapper";
    public List<Review> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public List<Review> readProductNoList(int productNo) {
        return sqlSession.selectList(namespace + ".readProductNoList", productNo);
    }
    public Review read(Review review) {
        return sqlSession.selectOne(namespace + ".read", review);
    }
    public boolean insert(Review review) {
        return sqlSession.insert(namespace + ".insert", review) == 1;
    }
    public boolean update(Review review) {
        return sqlSession.update(namespace + ".update", review) == 1;
    }
    public boolean delete(Review review) {
        return sqlSession.delete(namespace + ".delete", review) == 1;
    }
}