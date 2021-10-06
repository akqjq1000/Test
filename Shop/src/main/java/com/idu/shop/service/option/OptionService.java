package com.idu.shop.service.option;

import com.idu.shop.domain.member.Member;
import com.idu.shop.domain.option.Option;
import com.idu.shop.repository.member.MemberRepository;
import com.idu.shop.repository.option.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {
    OptionRepository repository;
    @Autowired
    public OptionService(OptionRepository repository) {
        this.repository = repository;
    }
    public List<Option> readList() {return repository.readList();}
    public Option read(Option option) {return repository.read(option);}
    public Option readByOptionNo(int optionNo) {return repository.readByOptionNo(optionNo);}
    public boolean insert(Option option) {return repository.insert(option);}
    public boolean update(Option option) {return repository.update(option);}
    public boolean delete(Option option) {return repository.delete(option);}
}
