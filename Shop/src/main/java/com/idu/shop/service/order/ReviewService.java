package com.idu.shop.service.order;

import com.idu.shop.domain.order.Payment;
import com.idu.shop.domain.order.Review;
import com.idu.shop.repository.order.PaymentRepository;
import com.idu.shop.repository.order.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    ReviewRepository repository;
    @Autowired
    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }
    public List<Review> readList() {return repository.readList();}
    public List<Review> readProductNoList(int productNo) {return repository.readProductNoList(productNo);}
    public Review read(Review review) {return repository.read(review);}
    public boolean insert(Review review) {return repository.insert(review);}
    public boolean update(Review review) {return repository.update(review);}
    public boolean delete(Review review) {return repository.delete(review);}
}
