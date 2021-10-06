package com.idu.shop.repository.category;

import com.idu.shop.domain.category.bCategory;
import com.idu.shop.domain.category.mCategory;
import com.idu.shop.domain.member.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class mCategoryRepository {
    SqlSession sqlSession;

    @Autowired
    public mCategoryRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.mCategoryMapper";
    public List<mCategory> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public List<mCategory> readListByBCateNo(int bCateNo) {return sqlSession.selectList(namespace + ".readListByBCateNo", bCateNo);}
    public mCategory read(int cateNo) {
        return sqlSession.selectOne(namespace + ".read", cateNo);
    }
    public boolean insert(mCategory mCate) {
        return sqlSession.insert(namespace + ".insert", mCate) == 1;
    }
    public boolean update(mCategory mCate) {
        return sqlSession.update(namespace + ".update", mCate) == 1;
    }
    public boolean deleteBybCateNo(int bCateNo) { return sqlSession.delete(namespace + ".deleteBybCateNo", bCateNo) == 1;}
    public boolean delete(int cateNo) {
        return sqlSession.delete(namespace + ".delete", cateNo) == 1;
    }
}