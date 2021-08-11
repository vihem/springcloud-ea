package com.ea.web;

import com.ea.entity.Product;
import com.ea.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Value("${version}")
    private String version;

    @RequestMapping("/products")
    public Object products(Model m) {
        List<Product> ps = productService.listProduct();
        m.addAttribute("ps", ps);
        m.addAttribute("version", version);
        return "products";
    }
}
