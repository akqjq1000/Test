package com.idu.shop.service.order;

import com.idu.shop.domain.order.ShoppingCartOption;
import com.idu.shop.repository.order.ShoppingCartOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartOptionService {
    ShoppingCartOptionRepository repository;
    @Autowired
    public ShoppingCartOptionService(ShoppingCartOptionRepository repository) {
        this.repository = repository;
    }

    public int insert(ShoppingCartOption sco) {return repository.insert(sco);}
    public List<ShoppingCartOption> read(int shoppingCartNo) {return repository.read(shoppingCartNo);}
    public boolean delete(int id) {return repository.delete(id);}
}
