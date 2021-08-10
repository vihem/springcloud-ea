# spring cloud
本项目使用

spring-boot     2.0.3.RELEASE版本

spring-cloud    Finchley.RELEASE

### 一 eureka-server
eureka-server 是 服务注册中心。

配置文件application.properties，提供 eureka 的相关信息。

1. hostname=localhost 表示主机名称。
2. register-with-eureka=false. 表示是否注册到服务器。 
    --- 因为它本身就是服务器，所以就无需把自己注册到服务器了。
3. fetch-registry=false. 表示是否获取服务器的注册信息，和上面同理，这里也设置为 false。
4. service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/ 
   自己作为服务器，公布出来的地址。 
   比如后续某个微服务要把自己注册到 eureka server, 那么就要使用这个地址： http://localhost:8761/eureka/
5. name=eureka-server 表示这个微服务本身的名称是 eureka-server

### 二 product-data-service
product-data-service 是 一个微服务。

1. 启动多次该服务，分配不一样的端口号8001，8002...
2. 在服务层service中加入了 一个port变量，可以显现出用了哪个端口，从不同的微服务得到的数据。
3. 配置文件中需要 配置：
    1. 注册中心的地址 http://localhost:8761/eureka/
    2. spring.application.name=product-data-service 表示 该数据服务在 eureka 注册中心的名称。
   

### 三 product-view-service-ribbon
访问已经注册好的数据微服务。 spring-cloud 提供了两种方式，一种是 Ribbon，一种是 Feign。
#### 理论
1. Ribbon 是使用 RestTemplate 进行调用，并进行客户端负载均衡。
2. 客户端负载均衡：在前面 注册数据微服务 里，注册了8001和8002两个微服务， Ribbon 会从注册中心获知这个信息，
   然后由 Ribbon 这个客户端自己决定是调用哪个，这个就叫做客户端负载均衡。
3. Feign 是对 Ribbon的封装。使用注解的方式，调用起来更简单，也是主流的方式。
4. 为什么不用前后端分离呢？ 干嘛要用 thymeleaf 做服务端渲染呢？
   
    原因如下：
    1. 使用前后端分离，站长多半会用 vue.js + axios.js来做，就像 springboot 天猫教程那样。 如果学习者没有这个基础，就会加重学习的负担。
    2. 使用前后端分离，是走的 http 协议， 那么就无法演示重要的 微服务端调用了，所以站长这里特意没有用前后端分离，以便于大家观察和掌握微服务的彼此调用

### 三 product-view-service-feign
Feign 是对 Ribbon 的封装，使用注解的方式，调用起来更简单，也是主流的方式。

pom需要添加 spring-cloud-starter-openfeign 依赖，用来支持 Feign 方式的

client客户端，Ribbon和Feign的不同之处：

Ribbon：
```java
@Component public class ProductClientRibbon {
   @Autowired
   private RestTemplate restTemplate;
   public List<Product> listProduct(){
      return restTemplate.getForObject("http://PRODUCT-DATA-SERVICE/products",List.class);
   }

}
```
Feign：

```java
@FeignClient(value = "PRODUCT-DATA-SERVICE")
public interface ProductClientFeign {
    @GetMapping("/products")
    List<Product> listProduct();
}
```

#### 实操
1. ribbon 需要 配置一个类 ProductClientRibbon：

   Ribbon 客户端， 通过 RestTemplate 访问（getForObject/postForObject） http://PRODUCT-DATA-SERVICE/products，
   而 product-data-service 既不是域名也不是ip地址，而是 数据服务在 eureka 注册中心的名称。
   注意看，这里只是指定了要访问的 微服务名称，但是并没有指定端口号到底是 8001, 还是 8002

2. ribbon 和 service 有相同实体类、service层，略微不同的controller，但是ribbon有web层（试图）
3. 启动类 配置： 
    
    @EnableDiscoveryClient， 表示用于发现eureka 注册中心的微服务；
    @Bean @LoadBalanced RestTemplate 表示用 restTemplate 这个工具来做负载均衡。
4. 配置文件

product-view-service-ribbon：该数据服务在 eureka 注册中心的名称。
### 启动：
1. 先启动注册中心 EurekaServerApplication
2. 然后启动两次服务 ProductDataServiceApplication， 分别输入 8001和8002.
3. 然后运行Ribbon ProductViewServiceRibbonApplication 以启动 微服务，然后访问地址：
   http://127.0.0.1:8010/products
   或者运行Feign  ProductViewServiceFeignApplication 已启动 微服务，访问
   http://127.0.0.1:8012/products
---

