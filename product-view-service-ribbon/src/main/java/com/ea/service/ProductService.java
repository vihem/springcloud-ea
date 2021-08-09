package com.ea.service;

import com.ea.client.ProductClientRibbon;
import com.ea.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务类，数据从 ProductClientRibbon 中获取
 */
@Service
public class ProductService {
    @Autowired
    ProductClientRibbon clientRibbon;
    public List<Product> listProduct(){
        return clientRibbon.listProduct();
    }
}
