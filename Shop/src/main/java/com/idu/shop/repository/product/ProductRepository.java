package com.idu.shop.repository.product;

import com.idu.shop.domain.member.Member;
import com.idu.shop.domain.product.Product;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    SqlSession sqlSession;

    @Autowired
    public ProductRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.ProductMapper";
    public List<Product> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public List<Product> searchByProductName(String productName) {return sqlSession.selectList(namespace + ".searchByProductName", productName);}
    public List<Product> readListByBCateNo(int bCateNo) { return sqlSession.selectList(namespace + ".readListByBCateNo", bCateNo); }
    public List<Product> readListByMCateNo(int mCateNo) {return sqlSession.selectList(namespace + ".readListByMCateNo", mCateNo);}
    public List<Product> readListByContentsImage(String contentsImage) {return sqlSession.selectList(namespace + ".readListByContentsImage", contentsImage);}
    public Product read(Product product) {
        return sqlSession.selectOne(namespace + ".read", product);
    }
    public Product readById(int productNo) {
        return sqlSession.selectOne(namespace + ".readById", productNo);
    }
    public boolean insert(Product product) {
        return sqlSession.insert(namespace + ".insert", product) == 1;
    }
    public boolean update(Product product) {
        return sqlSession.update(namespace + ".update", product) == 1;
    }
    public boolean increaseSoldCount(Product product) {
        return sqlSession.update(namespace + ".increaseSoldCount", product) == 1;
    }
    public boolean updateToRatio(Product product) {
        return sqlSession.update(namespace + ".updateToRatio", product) == 1;
    }
    public boolean delete(Product product) {
        return sqlSession.delete(namespace + ".delete", product) == 1;
    }
}