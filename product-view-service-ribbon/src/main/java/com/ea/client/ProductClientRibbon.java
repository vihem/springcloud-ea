package com.ea.client;

import com.ea.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Ribbon 客户端， 通过 restTemplate 访问 http://PRODUCT-DATA-SERVICE/products ，
 * 而 product-data-service 既不是域名也不是ip地址，而是 数据服务在 eureka 注册中心的名称。
 *
 * 注意看，这里只是指定了要访问的 微服务名称，但是并没有指定端口号到底是 8001, 还是 8002
 */
@Component
public class ProductClientRibbon {
    @Autowired
    private RestTemplate restTemplate;

    public List<Product> listProduct(){
        return restTemplate.getForObject("http://PRODUCT-DATA-SERVICE/products",List.class);
    }

}
