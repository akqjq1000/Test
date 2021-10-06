package com.idu.shop.repository.category;

import com.idu.shop.domain.category.bCategory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class bCategoryRepository {
    SqlSession sqlSession;

    @Autowired
    public bCategoryRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.bCategoryMapper";
    public List<bCategory> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public bCategory read(int cateNo) {
        return sqlSession.selectOne(namespace + ".read", cateNo);
    }
    public boolean insert(bCategory bCate) {
        return sqlSession.insert(namespace + ".insert", bCate) == 1;
    }
    public boolean update(bCategory bCate) {
        return sqlSession.update(namespace + ".update", bCate) == 1;
    }
    public boolean delete(int cateNo) {
        return sqlSession.delete(namespace + ".delete", cateNo) == 1;
    }
}