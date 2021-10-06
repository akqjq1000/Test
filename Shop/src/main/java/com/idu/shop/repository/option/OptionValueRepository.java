package com.idu.shop.repository.option;

import com.idu.shop.domain.option.Option;
import com.idu.shop.domain.option.OptionValue;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OptionValueRepository {
    SqlSession sqlSession;

    @Autowired
    public OptionValueRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.OptionValueMapper";
    public List<OptionValue> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public OptionValue read(OptionValue optionValue) {
        return sqlSession.selectOne(namespace + ".read", optionValue);
    }
    public List<OptionValue> readByOptionNo(int optionNo) {
        return sqlSession.selectList(namespace + ".readByOptionNo", optionNo);
    }
    public boolean insert(OptionValue optionValue) {
        return sqlSession.insert(namespace + ".insert", optionValue) == 1;
    }
    public boolean update(OptionValue optionValue) {
        return sqlSession.update(namespace + ".update", optionValue) == 1;
    }
    public boolean delete(OptionValue optionValue) {
        return sqlSession.delete(namespace + ".delete", optionValue) == 1;
    }
    public boolean deleteByOptionNo(int optionNo) {
        return sqlSession.delete(namespace + ".deleteByOptionNo", optionNo) == 1;
    }
}