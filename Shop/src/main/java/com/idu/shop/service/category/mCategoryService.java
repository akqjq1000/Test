package com.idu.shop.service.category;

import com.idu.shop.domain.category.mCategory;
import com.idu.shop.repository.category.mCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class mCategoryService {

    mCategoryRepository repository;
    @Autowired
    public mCategoryService(mCategoryRepository repository) {
        this.repository = repository;
    }
    public List<mCategory> readList() {return repository.readList();}
    public List<mCategory> readListByBCateNo(int bCateNo) {return repository.readListByBCateNo(bCateNo);}
    public mCategory read(int cateNo) {return repository.read(cateNo);}
    public boolean insert(mCategory m_category) {return repository.insert(m_category);}
    public boolean update(mCategory m_category) {return repository.update(m_category);}
    public boolean deleteBybCateNo(int bCateNo) {return repository.deleteBybCateNo(bCateNo);}
    public boolean delete(int cateNo) {return repository.delete(cateNo);}

}
