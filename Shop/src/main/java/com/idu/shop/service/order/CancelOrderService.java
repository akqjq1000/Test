package com.idu.shop.service.order;

import com.idu.shop.domain.order.CancelOrder;
import com.idu.shop.domain.product.Product;
import com.idu.shop.repository.order.CancelOrderRepository;
import com.idu.shop.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelOrderService {
    CancelOrderRepository repository;
    @Autowired
    public CancelOrderService(CancelOrderRepository repository) {
        this.repository = repository;
    }
    public List<CancelOrder> readList() {return repository.readList();}
    public CancelOrder read(CancelOrder cancelOrder) {return repository.read(cancelOrder);}
    public boolean insert(CancelOrder cancelOrder) {return repository.insert(cancelOrder);}
    public boolean update(CancelOrder cancelOrder) {return repository.update(cancelOrder);}
    public boolean delete(CancelOrder cancelOrder) {return repository.delete(cancelOrder);}
}
