package com.idu.shop.service.order;

import com.idu.shop.domain.order.Delivery;
import com.idu.shop.domain.product.Product;
import com.idu.shop.repository.order.DeliveryRepository;
import com.idu.shop.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    DeliveryRepository repository;
    @Autowired
    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }
    public List<Delivery> readList() {return repository.readList();}
    public Delivery readById(int deliveryNo) {return repository.readById(deliveryNo);}
    public Delivery read(String orderSequence) {return repository.read(orderSequence);}
    public boolean insert(Delivery delivery) {return repository.insert(delivery);}
    public boolean update(Delivery delivery) {return repository.update(delivery);}
    public boolean delete(Delivery delivery) {return repository.delete(delivery);}
}
