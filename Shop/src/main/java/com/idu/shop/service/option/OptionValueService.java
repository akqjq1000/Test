package com.idu.shop.service.option;

import com.idu.shop.domain.option.Option;
import com.idu.shop.domain.option.OptionValue;
import com.idu.shop.repository.option.OptionRepository;
import com.idu.shop.repository.option.OptionValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionValueService {
    OptionValueRepository repository;
    @Autowired
    public OptionValueService(OptionValueRepository repository) {
        this.repository = repository;
    }
    public List<OptionValue> readList() {return repository.readList();}
    public OptionValue read(OptionValue optionValue) {return repository.read(optionValue);}
    public List<OptionValue> readByOptionNo(int optionNo) {return repository.readByOptionNo(optionNo);}
    public boolean insert(OptionValue optionValue) {return repository.insert(optionValue);}
    public boolean update(OptionValue optionValue) {return repository.update(optionValue);}
    public boolean delete(OptionValue optionValue) {return repository.delete(optionValue);}
    public boolean deleteByOptionNo(int optionNo) {return repository.deleteByOptionNo(optionNo);}
}
