package com.idu.shop.service.order;

import com.idu.shop.domain.order.Order;
import com.idu.shop.domain.order.Payment;
import com.idu.shop.repository.order.OrderRepository;
import com.idu.shop.repository.order.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    PaymentRepository repository;
    @Autowired
    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }
    public List<Payment> readList() {return repository.readList();}
    public List<Payment> readByMemberNo(int memberNo) {return repository.readByMemberNo(memberNo);}
    public Payment read(Payment payment) {return repository.read(payment);}
    public Payment readByOrderSequence(String orderSequence) {return repository.readByOrderSequence(orderSequence);}
    public boolean insert(Payment payment) {return repository.insert(payment);}
    public boolean update(Payment payment) {return repository.update(payment);}
    public boolean delete(Payment payment) {return repository.delete(payment);}
}
