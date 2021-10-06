package com.idu.shop.service.order;

import com.idu.shop.domain.order.ShoppingCart;
import com.idu.shop.repository.order.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    ShoppingCartRepository repository;
    @Autowired
    public ShoppingCartService(ShoppingCartRepository repository) {
        this.repository = repository;
    }
    public List<ShoppingCart> readList() {return repository.readList();}
    public List<ShoppingCart> readByMemberNoList(int memberNo) {return repository.readByMemberNoList(memberNo);}
    public ShoppingCart read(ShoppingCart shoppingCart) {return repository.read(shoppingCart);}
    public ShoppingCart readRecentlyAddedRow() {return repository.readRecentlyAddedRow();}
    public boolean insert(ShoppingCart shoppingCart) {return repository.insert(shoppingCart);}
    public boolean update(ShoppingCart shoppingCart) {return repository.update(shoppingCart);}
    public boolean delete(int id) {return repository.delete(id);}
}
