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

### �� zipkin ������·

1. ʲô�Ƿ�����·

   ����������΢���񣬷ֱ������ݷ������ͼ��������ҵ������ӣ��ͻ���Խ��Խ���΢������ڣ�����֮��Ҳ���и��Ӹ��ӵĵ��ù�ϵ��
   ������ù�ϵ������ͨ���۲���룬��Խ��Խ����ʶ�����Ծ���Ҫͨ�� zipkin ������·׷�ٷ����� �����������ͼƬ����ʶ���ˡ�
   
2. ����ʹ�� zipkin-server-2.10.1-exec.jar��zipkinĿ¼�£�

   1. �������� java -jar zipkin-server-2.10.1-exec.jar(��ַ���Ϳ�����)/��ideaֱ���Ҽ�run
   2. ����������Ҫ�ķ���ϵͳ
   3. ִ��һ�� http://127.0.0.1:8012/products
   4. ������·׷�ٷ����� http://localhost:9411/zipkin/dependency/ �Ϳ��Կ��� ��ͼ΢�����������΢���� ��ͼ��
   
3. ���죺product-data-service��product-view-service-xxx
   
   1. ������pom���� spring-cloud-starter-zipkin
   2. �����඼���� Sampler �������ԣ� ALWAYS_SAMPLE ��ʾ��������
      ```
      @Bean
      public Sampler defaultSampler(){ return Sampler.ALWAYS_SAMPLE;}
      ```
   3. �����ļ������ӣ� spring.zipkin.base-url: http://localhost:9411

### ���÷���

��ʱ��΢����Ҫ����Ⱥ�������ζ�ţ����ж��΢����ʵ����
��ҵ������ʱ����Ҫ�޸�һЩ������Ϣ������˵ �汾��Ϣ��~ ����û�����÷��� ��ô����Ҫ�����޸�΢���񣬰������²���΢���������ͱȽ��鷳��
Ϊ��͵���� ��Щ������Ϣ�ͻ����һ�������ĵط�������git, Ȼ��ͨ�����÷�����������ȡ������Ȼ��΢�����ٴ����÷�������ȡ������
����ֻҪ�޸�git�ϵ���Ϣ����ôͬһ����Ⱥ�������΢����������ȡ��Ӧ��Ϣ�ˣ������ʹ���Լ�˿��������ߺ����²����ʱ���ˡ�

��ͼ��repos/ConfigServer.png��
�������� git �ﱣ�� version ��Ϣ�� Ȼ��ͨ�� ConfigServer ȥ��ȡ version ��Ϣ�� ���Ų�ͬ����ͼ΢����ʵ����ȥ ConfigServer ���ȡ version.

### ������
1. ������ע������ EurekaServerApplication
2. ����ConfigServerApplication������ http://localhost:8030/version/dev
   ```
   {"name":"version","profiles":["dev"],"label":null,"version":"5046c9520e739312615997563357740456cc5756","state":null,"propertySources":[]}
   ```
3. Ȼ���������η��� ProductDataServiceApplication�� �ֱ����� 8001��8002.
4. Ȼ������Ribbon ProductViewServiceRibbonApplication ������ ΢����Ȼ����ʵ�ַ��
   http://127.0.0.1:8010/products

   ��������Feign  ProductViewServiceFeignApplication ������ ΢���񣬷���
   http://127.0.0.1:8012/products
5. ִ��һ�� http://127.0.0.1:8012/products������Feign����ʹ��Ribbon��
6. ������·׷�ٷ����� http://localhost:9411/zipkin/dependency/ �Ϳ��Կ��� ��ͼ΢�����������΢���� ��ͼ��
---