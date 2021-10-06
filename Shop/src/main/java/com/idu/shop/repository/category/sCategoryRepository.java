package com.idu.shop.repository.category;

import com.idu.shop.domain.category.bCategory;
import com.idu.shop.domain.category.mCategory;
import com.idu.shop.domain.category.sCategory;
import com.idu.shop.domain.member.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class sCategoryRepository {
    SqlSession sqlSession;

    @Autowired
    public sCategoryRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.sCategoryMapper";
    public List<sCategory> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public sCategory read(int cateNo) {
        return sqlSession.selectOne(namespace + ".read", cateNo);
    }
    public boolean insert(sCategory sCate) {
        return sqlSession.insert(namespace + ".insert", sCate) == 1;
    }
    public boolean update(sCategory sCate) {
        return sqlSession.update(namespace + ".update", sCate) == 1;
    }
    public boolean deleteBymCateNo(int mCateNo) { return sqlSession.delete(namespace + ".deleteBymCateNo", mCateNo) == 1;}
    public boolean delete(int cateNo) {
        return sqlSession.delete(namespace + ".delete", cateNo) == 1;
    }
}