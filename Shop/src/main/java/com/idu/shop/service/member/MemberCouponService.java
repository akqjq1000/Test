package com.idu.shop.service.member;

import com.idu.shop.domain.member.MemberCoupon;
import com.idu.shop.repository.member.MemberCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberCouponService {
    MemberCouponRepository repository;
    @Autowired
    public MemberCouponService(MemberCouponRepository repository) {
        this.repository = repository;
    }
    public List<MemberCoupon> readList() {return repository.readList();}
    public MemberCoupon read(MemberCoupon memberCoupon) {return repository.read(memberCoupon);}
    public List<MemberCoupon> readByMemberNo(int memberNo) {return repository.readByMemberNo(memberNo);}
    public boolean insert(MemberCoupon memberCoupon) {return repository.insert(memberCoupon);}
    public boolean update(MemberCoupon memberCoupon) {return repository.update(memberCoupon);}
    public boolean delete(MemberCoupon memberCoupon) {return repository.delete(memberCoupon);}
}
