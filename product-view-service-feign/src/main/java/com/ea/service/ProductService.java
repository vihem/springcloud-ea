package com.ea.service;

import com.ea.client.ProductClientFeign;
import com.ea.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductClientFeign productClientFeign;
    public List<Product> listProduct(){
        return productClientFeign.listProduct();
    }
}
