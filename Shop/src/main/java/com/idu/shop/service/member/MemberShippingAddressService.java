package com.idu.shop.service.member;

import com.idu.shop.domain.member.Member;
import com.idu.shop.domain.member.MemberShippingAddress;
import com.idu.shop.repository.member.MemberShippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberShippingAddressService {
    MemberShippingAddressRepository repository;
    @Autowired
    public MemberShippingAddressService(MemberShippingAddressRepository repository) {
        this.repository = repository;
    }
    public List<MemberShippingAddress> readList() {return repository.readList();}
    public List<MemberShippingAddress> readMemberList(int memberNo) {return repository.readMemberList(memberNo);}
    public MemberShippingAddress read(int shippingAddressNo) {return repository.read(shippingAddressNo);}
    public boolean insert(MemberShippingAddress memberShippingAddress) {return repository.insert(memberShippingAddress);}
    public boolean update(MemberShippingAddress memberShippingAddress) {return repository.update(memberShippingAddress);}
    public boolean delete(MemberShippingAddress memberShippingAddress) {return repository.delete(memberShippingAddress);}
}
