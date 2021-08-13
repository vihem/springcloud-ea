package com.ea;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient //让 Eureka 注册中心找到
@EnableDiscoveryClient  //让 Eureka等 注册中心找到
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String[] args) {
        int port = 8040;
        if (!NetUtil.isUsableLocalPort(port)){
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(ZuulApplication.class).properties("server.port="+port).run(args);
    }
}
