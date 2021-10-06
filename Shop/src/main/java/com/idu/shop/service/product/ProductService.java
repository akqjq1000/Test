package com.idu.shop.service.product;

import com.idu.shop.domain.member.Member;
import com.idu.shop.domain.product.Product;
import com.idu.shop.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    ProductRepository repository;
    @Autowired
    public ProductService(ProductRepository repository) {this.repository = repository;}
    public List<Product> readList() {return repository.readList();}
    public List<Product> readListByBCateNo(int bCateNo) {return repository.readListByBCateNo(bCateNo);}
    public List<Product> readListByMCateNo(int mCateNo) {return repository.readListByMCateNo(mCateNo);}
    public List<Product> searchByProductName(String productName) {return repository.searchByProductName(productName);}
    public List<Product> readListByContentsImage(String contentsImage) {return repository.readListByContentsImage(contentsImage);}
    public Product read(Product product) {return repository.read(product);}
    public Product readById(int productNo) {return repository.readById(productNo);}
    public boolean insert(Product product) {return repository.insert(product);}
    public boolean update(Product product) {return repository.update(product);}
    public boolean increaseSoldCount(Product product) {return repository.increaseSoldCount(product);}
    public boolean updateToRatio(Product product) {return repository.update(product);}
    public boolean delete(Product product) {return repository.delete(product);}
}
