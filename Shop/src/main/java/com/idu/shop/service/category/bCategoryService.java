package com.idu.shop.service.category;

import com.idu.shop.domain.category.bCategory;
import com.idu.shop.repository.category.bCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class bCategoryService {

    bCategoryRepository repository;
    @Autowired
    public bCategoryService(bCategoryRepository repository) {
        this.repository = repository;
    }
    public List<bCategory> readList() {return repository.readList();}
    public bCategory read(int cateNo) {return repository.read(cateNo);}
    public boolean insert(bCategory b_category) {return repository.insert(b_category);}
    public boolean update(bCategory b_category) {return repository.update(b_category);}
    public boolean delete(int cateNo) {return repository.delete(cateNo);}

}
