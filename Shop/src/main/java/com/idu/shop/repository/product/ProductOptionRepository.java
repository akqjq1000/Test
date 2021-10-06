package com.idu.shop.repository.product;

import com.idu.shop.domain.product.Product;
import com.idu.shop.domain.product.ProductOption;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductOptionRepository {
    SqlSession sqlSession;

    @Autowired
    public ProductOptionRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.ProductOptionMapper";
    public List<ProductOption> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public List<ProductOption> readByProductNoList(int productNo) {
        return sqlSession.selectList(namespace + ".readByProductNoList", productNo);
    }
    public ProductOption read(ProductOption productOption) {
        return sqlSession.selectOne(namespace + ".read", productOption);
    }
    public boolean insert(ProductOption productOption) {
        return sqlSession.insert(namespace + ".insert", productOption) == 1;
    }
    public boolean update(ProductOption productOption) {
        return sqlSession.update(namespace + ".update", productOption) == 1;
    }
    public boolean delete(ProductOption productOption) {
        return sqlSession.delete(namespace + ".delete", productOption) == 1;
    }
}