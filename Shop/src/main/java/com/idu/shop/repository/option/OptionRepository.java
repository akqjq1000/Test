package com.idu.shop.repository.option;

import com.idu.shop.domain.member.Member;
import com.idu.shop.domain.option.Option;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OptionRepository {
    SqlSession sqlSession;

    @Autowired
    public OptionRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private String namespace = "com.idu.shop.OptionMapper";
    public List<Option> readList() {
        return sqlSession.selectList(namespace + ".readList");
    }
    public Option read(Option option) {
        return sqlSession.selectOne(namespace + ".read", option);
    }
    public Option readByOptionNo(int optionNo) {
        return sqlSession.selectOne(namespace + ".readByOptionNo", optionNo);
    }
    public boolean insert(Option option) {
        return sqlSession.insert(namespace + ".insert", option) == 1;
    }
    public boolean update(Option option) {
        return sqlSession.update(namespace + ".update", option) == 1;
    }
    public boolean delete(Option option) {
        return sqlSession.delete(namespace + ".delete", option) == 1;
    }
}