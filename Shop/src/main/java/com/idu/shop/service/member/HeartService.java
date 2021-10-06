package com.idu.shop.service.member;

import com.idu.shop.domain.member.Heart;
import com.idu.shop.repository.member.HeartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeartService {
    HeartRepository repository;
    @Autowired
    public HeartService(HeartRepository repository) {
        this.repository = repository;
    }
    public List<Heart> readList() {return repository.readList();}
    public List<Heart> readByMemberNo(int memberNo) {return repository.readByMemberNo(memberNo);}
    public boolean insert(Heart heart) {return repository.insert(heart);}
    public boolean delete(int productNo) {return repository.delete(productNo);}
}
