# spring cloud
����Ŀʹ��

spring-boot     2.0.3.RELEASE�汾

spring-cloud    Finchley.RELEASE

### һ eureka-server
eureka-server �� ����ע�����ġ�

�����ļ�application.properties���ṩ eureka �������Ϣ��

1. hostname=localhost ��ʾ�������ơ�
2. register-with-eureka=false. ��ʾ�Ƿ�ע�ᵽ�������� 
    --- ��Ϊ��������Ƿ����������Ծ�������Լ�ע�ᵽ�������ˡ�
3. fetch-registry=false. ��ʾ�Ƿ��ȡ��������ע����Ϣ��������ͬ������Ҳ����Ϊ false��
4. service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/ 
   �Լ���Ϊ�����������������ĵ�ַ�� 
   �������ĳ��΢����Ҫ���Լ�ע�ᵽ eureka server, ��ô��Ҫʹ�������ַ�� http://localhost:8761/eureka/
5. name=eureka-server ��ʾ���΢������������� eureka-server

### �� product-data-service
product-data-service �� һ��΢����

1. ������θ÷��񣬷��䲻һ���Ķ˿ں�8001��8002...
2. �ڷ����service�м����� һ��port�������������ֳ������ĸ��˿ڣ��Ӳ�ͬ��΢����õ������ݡ�
3. �����ļ�����Ҫ ���ã�
    1. ע�����ĵĵ�ַ http://localhost:8761/eureka/
    2. spring.application.name=product-data-service ��ʾ �����ݷ����� eureka ע�����ĵ����ơ�
   

### �� product-view-service-ribbon
�����Ѿ�ע��õ�����΢���� spring-cloud �ṩ�����ַ�ʽ��һ���� Ribbon��һ���� Feign��
#### ����
1. Ribbon ��ʹ�� RestTemplate ���е��ã������пͻ��˸��ؾ��⡣
2. �ͻ��˸��ؾ��⣺��ǰ�� ע������΢���� �ע����8001��8002����΢���� Ribbon ���ע�����Ļ�֪�����Ϣ��
   Ȼ���� Ribbon ����ͻ����Լ������ǵ����ĸ�������ͽ����ͻ��˸��ؾ��⡣
3. Feign �Ƕ� Ribbon�ķ�װ��ʹ��ע��ķ�ʽ�������������򵥣�Ҳ�������ķ�ʽ��
4. Ϊʲô����ǰ��˷����أ� ����Ҫ�� thymeleaf ���������Ⱦ�أ�
   
    ԭ�����£�
    1. ʹ��ǰ��˷��룬վ�������� vue.js + axios.js���������� springboot ��è�̳������� ���ѧϰ��û������������ͻ����ѧϰ�ĸ�����
    2. ʹ��ǰ��˷��룬���ߵ� http Э�飬 ��ô���޷���ʾ��Ҫ�� ΢����˵����ˣ�����վ����������û����ǰ��˷��룬�Ա��ڴ�ҹ۲������΢����ı˴˵���

### �� product-view-service-feign
Feign �Ƕ� Ribbon �ķ�װ��ʹ��ע��ķ�ʽ�������������򵥣�Ҳ�������ķ�ʽ��

pom��Ҫ��� spring-cloud-starter-openfeign ����������֧�� Feign ��ʽ��

client�ͻ��ˣ�Ribbon��Feign�Ĳ�֮ͬ����

Ribbon��
```java
@Component public class ProductClientRibbon {
   @Autowired
   private RestTemplate restTemplate;
   public List<Product> listProduct(){
      return restTemplate.getForObject("http://PRODUCT-DATA-SERVICE/products",List.class);
   }

}
```
Feign��

```java
@FeignClient(value = "PRODUCT-DATA-SERVICE")
public interface ProductClientFeign {
    @GetMapping("/products")
    List<Product> listProduct();
}
```

#### ʵ��
1. ribbon ��Ҫ ����һ���� ProductClientRibbon��

   Ribbon �ͻ��ˣ� ͨ�� RestTemplate ���ʣ�getForObject/postForObject�� http://PRODUCT-DATA-SERVICE/products��
   �� product-data-service �Ȳ�������Ҳ����ip��ַ������ ���ݷ����� eureka ע�����ĵ����ơ�
   ע�⿴������ֻ��ָ����Ҫ���ʵ� ΢�������ƣ����ǲ�û��ָ���˿ںŵ����� 8001, ���� 8002

2. ribbon �� service ����ͬʵ���ࡢservice�㣬��΢��ͬ��controller������ribbon��web�㣨��ͼ��
3. ������ ���ã� 
    
    @EnableDiscoveryClient�� ��ʾ���ڷ���eureka ע�����ĵ�΢����
    @Bean @LoadBalanced RestTemplate ��ʾ�� restTemplate ��������������ؾ��⡣
4. �����ļ�

product-view-service-ribbon�������ݷ����� eureka ע�����ĵ����ơ�
### ������
1. ������ע������ EurekaServerApplication
2. Ȼ���������η��� ProductDataServiceApplication�� �ֱ����� 8001��8002.
3. Ȼ������Ribbon ProductViewServiceRibbonApplication ������ ΢����Ȼ����ʵ�ַ��
   http://127.0.0.1:8010/products
   ��������Feign  ProductViewServiceFeignApplication ������ ΢���񣬷���
   http://127.0.0.1:8012/products
---

