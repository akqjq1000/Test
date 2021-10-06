package com.idu.shop.service.order;

import com.idu.shop.domain.order.Delivery;
import com.idu.shop.domain.order.Order;
import com.idu.shop.repository.order.DeliveryRepository;
import com.idu.shop.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    OrderRepository repository;
    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }
    public List<Order> readList() {return repository.readList();}
    public List<Order> readBySerialNumber(String serialNumber) {return repository.readBySerialNumber(serialNumber);}
    public Order read(Order order) {return repository.read(order);}
    public boolean insert(Order order) {return repository.insert(order);}
    public boolean update(Order order) {return repository.update(order);}
    public boolean delete(Order order) {return repository.delete(order);}
}
