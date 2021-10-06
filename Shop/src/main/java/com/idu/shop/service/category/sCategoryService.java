package com.idu.shop.service.category;

import com.idu.shop.domain.category.sCategory;
import com.idu.shop.repository.category.sCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class sCategoryService {

    sCategoryRepository repository;
    @Autowired
    public sCategoryService(sCategoryRepository repository) {
        this.repository = repository;
    }
    public List<sCategory> readList() {return repository.readList();}
    public sCategory read(int cateNo) {return repository.read(cateNo);}//완료!! 무슨 오류 나는거였어? sCategoryRepository로 ㄱㄱ
    public boolean insert(sCategory s_category) {return repository.insert(s_category);}
    public boolean update(sCategory s_category) {return repository.update(s_category);}
    public boolean deleteBymCateNo(int mCateNo) {return repository.deleteBymCateNo(mCateNo);}
    public boolean delete(int cateNo) {return repository.delete(cateNo);}

}
