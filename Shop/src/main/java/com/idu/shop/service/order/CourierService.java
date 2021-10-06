package com.idu.shop.service.order;

import com.idu.shop.domain.order.Courier;
import com.idu.shop.repository.order.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierService {
    CourierRepository repository;
    @Autowired
    public CourierService(CourierRepository repository) {
        this.repository = repository;
    }
    public List<Courier> readList() {return repository.readList();}
    public Courier read(int id) {return repository.read(id);}
    public boolean insert(Courier courier) {return repository.insert(courier);}
}
