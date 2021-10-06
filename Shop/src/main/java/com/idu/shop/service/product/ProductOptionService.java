package com.idu.shop.service.product;

import com.idu.shop.domain.product.Product;
import com.idu.shop.domain.product.ProductOption;
import com.idu.shop.repository.product.ProductOptionRepository;
import com.idu.shop.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOptionService {
    ProductOptionRepository repository;
    @Autowired
    public ProductOptionService(ProductOptionRepository repository) {
        this.repository = repository;
    }
    public List<ProductOption> readList() {return repository.readList();}
    public List<ProductOption> readByProductNoList(int productNo) {return repository.readByProductNoList(productNo);}
    public ProductOption read(ProductOption productOption) {return repository.read(productOption);}
    public boolean insert(ProductOption productOption) {return repository.insert(productOption);}
    public boolean update(ProductOption productOption) {return repository.update(productOption);}
    public boolean delete(ProductOption productOption) {return repository.delete(productOption);}
}
