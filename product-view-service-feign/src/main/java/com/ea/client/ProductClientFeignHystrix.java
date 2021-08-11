package com.ea.client;

import com.ea.entity.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加 Hystrix 断路器
 * ProductClientFeignHystrix 实现了 ProductClientFeign 接口，并提供了 listProduct() 方法。
 * 这个方法就会固定返回包含一条信息的集合~
 */
@Component
public class ProductClientFeignHystrix implements ProductClientFeign{
    @Override
    public List<Product> listProduct() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(0,"产品数据微服务不可用",0));
        return products;
    }
}
