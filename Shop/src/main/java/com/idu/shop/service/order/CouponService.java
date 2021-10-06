package com.idu.shop.service.order;

import com.idu.shop.domain.order.Coupon;
import com.idu.shop.repository.order.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {
    CouponRepository couponRepository;
    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> readList() {return couponRepository.readList();}
    public Coupon readById(int id) {return couponRepository.readById(id);}
    public boolean insert(Coupon coupon) {return couponRepository.insert(coupon);}
    public boolean delete(int id) {return couponRepository.delete(id);}
}
