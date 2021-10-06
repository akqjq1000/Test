package com.idu.shop.repository.product;

import com.idu.shop.domain.product.Product;
import com.idu.shop.domain.product.ProductImage;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductImageRepository {
    SqlSession sqlSession;

    @Autowired
    public ProductImageRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.ProductImageMapper";
    public List<ProductImage> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public ProductImage read(ProductImage productImage) {
        return sqlSession.selectOne(namespace + ".read", productImage);
    }
    public List<ProductImage> readByProductNoList(int productNo) {
        return sqlSession.selectList(namespace + ".readByProductNoList", productNo);
    }
    public boolean insert(ProductImage productImage) {
        return sqlSession.insert(namespace + ".insert", productImage) == 1;
    }
    public boolean update(ProductImage productImage) {
        return sqlSession.update(namespace + ".update", productImage) == 1;
    }
    public boolean delete(int productImageNo) {
        return sqlSession.delete(namespace + ".delete", productImageNo) == 1;
    }
}