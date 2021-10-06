package com.idu.shop.service.product;

import com.idu.shop.domain.product.Product;
import com.idu.shop.domain.product.ProductImage;
import com.idu.shop.repository.product.ProductImageRepository;
import com.idu.shop.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    ProductImageRepository repository;
    @Autowired
    public ProductImageService(ProductImageRepository repository) {
        this.repository = repository;
    }
    public List<ProductImage> readList() {return repository.readList();}
    public List<ProductImage> readByProductNoList(int productNo) {return repository.readByProductNoList(productNo);}
    public ProductImage read(ProductImage productImage) {return repository.read(productImage);}
    public boolean insert(ProductImage productImage) {return repository.insert(productImage);}
    public boolean update(ProductImage productImage) {return repository.update(productImage);}
    public boolean delete(int productImageNo) {return repository.delete(productImageNo);}
}
