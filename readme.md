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
1. Feign �Ƕ� Ribbon �ķ�װ��ʹ��ע��ķ�ʽ�������������򵥣�Ҳ�������ķ�ʽ��
2. pom��Ҫ��� spring-cloud-starter-openfeign ����������֧�� Feign ��ʽ��
3. client�ͻ��ˣ�Ribbon��Feign�Ĳ�֮ͬ����
   
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
   4. ������·׷�ٷ����� [http://localhost:9411/zipkin/dependency/][] �Ϳ��Կ��� ��ͼ΢�����������΢���� ��ͼ��
   
3. ���죺product-data-service��product-view-service-xxx
   
   1. ������pom���� spring-cloud-starter-zipkin
   2. �����඼���� Sampler �������ԣ� ALWAYS_SAMPLE ��ʾ��������
      ```
      @Bean
      public Sampler defaultSampler(){ return Sampler.ALWAYS_SAMPLE;}
      ```
   3. �����ļ������ӣ� spring.zipkin.base-url: http://localhost:9411

### �� config-server ���÷���

��ʱ��΢����Ҫ����Ⱥ�������ζ�ţ����ж��΢����ʵ����
��ҵ������ʱ����Ҫ�޸�һЩ������Ϣ������˵ �汾��Ϣ��~ 
����û�����÷�����ô����Ҫ�����޸�΢���񣬰������²���΢���������ͱȽ��鷳��
Ϊ��͵������Щ������Ϣ�ͻ����һ�������ĵط�������git, Ȼ��ͨ�����÷�����������ȡ������Ȼ��΢�����ٴ����÷�������ȡ������
����ֻҪ�޸�git�ϵ���Ϣ����ôͬһ����Ⱥ�������΢����������ȡ��Ӧ��Ϣ�ˣ������ʹ���Լ�˿��������ߺ����²����ʱ���ˡ�

��ͼ��repos/ConfigServer.png��
�������� git �ﱣ�� version ��Ϣ��Ȼ��ͨ�� ConfigServer ȥ��ȡ version ��Ϣ�����Ų�ͬ����ͼ΢����ʵ����ȥ ConfigServer ���ȡ version.

https://github.com/vihem/springcloud-ea/blob/master/respo/product-view-service-feign-dev.properties \
https://gitee.com/vihem/springcloud-ea/blob/master/respo/product-view-service-feign-dev.properties

������� @EnableConfigServer ���ע���ʾ��springboot �Ǹ����÷�������

### �� Ϊ feign �������÷���ˣ�������ʾ config-server ���õ�git��Ϣ

1. pom��spring-cloud-starter-config ���ڷ������÷�������
2. ����һ�������ļ� bootstrap.yml \
   ��Ϊ���ÿͻ��ˣ��Ƚ��ر�~ ����Ҫ�� bootstrap.yml ������ config-server ����Ϣ������������ǰ������ application.yml ��������á�\
   bootstrap.yml �� application.yml ������:
   ��˵����ǰ��������������һЩϵͳ�����������Ҫ�� bootstrap.yml ��������á�\
   �� bootstrap.yml �������ṩ�� serviceId: config-server, ��������÷������� eureka server ��ķ������ƣ������Ϳ��Զ�λ config-server�ˡ�\
   application.yml �� eureka ��ַ��Ϣ�ƶ����� bootstrap.yml ���Ҳ���Բ��ƣ�

3. ���⣺
�����޸� �汾��Ϣ��
https://gitee.com/vihem/springcloud-ea/blob/master/respo/product-view-service-feign-dev.properties
�ĳ� version = vihem springcloud version 1.1,

Ȼ��ˢ�� http://localhost:8012/products �ᷢ�֡������������� 1.0.������ 
��ôҪ�����Ч�أ� �ͱ������� ConfigServerApplication �� ProductViewServiceFeignApplication ���С�
��ν���أ������õ���Ϣ���ߣ�Ҳ������Ϣ���� RabbitMQ��

### �� ��Ϣ���� RabbitMQ

RabbitMQ ��һ����Ϣ���У���Ҫ������ʵ��Ӧ�ó�����첽�ͽ��ͬʱҲ������Ϣ���壬��Ϣ�ַ������á�

�汾�ţ� RabbitMQ Server 3.6.5 ��������ӣ� http://127.0.0.1:15672/#/
1. �ͻ��� feign �������
   1. ����spring-boot-starter-actuator ���ڷ���·����/actuator/bus-refresh
   2. ����spring-cloud-starter-bus-amqp ����֧�� rabbitmq
2. bootstrap.yml
   
```yaml
spring:
  cloud:
    bus:
      enabled: true
      trace:
        enabled: true
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```
3. application.yml

```yaml
# ����·�������������ܷ��ʣ� /actuator/bus-refresh
management:
  endpoints:
    web:
      exposure:
        include: "*"
      cors:
        allowed-origins: "*"
        allowed-methods: "*"
```
4. ���������� RabbitMQ �˿ڼ��
5. ����һ���� utils/FreshConfigUtil.java

   ʹ�� post �ķ�ʽ���� http://localhost:8012/actuator/bus-refresh ��ַ��
   ֮����Ҫר����һ�� FreshConfigUtil �࣬����Ϊ�˿���ʹ�� post ���ʣ���Ϊ����֧�� get ��ʽ���ʣ�ֱ�Ӱ������ַ�����������ǻ��׳� 405����ġ�\
   �����ַ�����þ����� config-server ȥ git ��ȡ���µ�������Ϣ�����Ѵ���Ϣ�㲥����Ⱥ������� ��ͼ΢����

6. ��ͼ��������˸��죬֧���� rabbitMQ, ��ô��Ĭ������£�������Ϣ�Ͳ������ Zipkin�ˡ� ��Zipkin �￴������ͼ����������ˡ�

   ����zipkin��Ϊ: java -jar zipkin-server-2.10.1-exec.jar --zipkin.collector.rabbitmq.addresses=localhost

### �� Hystrix ��·��
��ͼ������ ������ ���ݷ��������� product-data-service ���ˣ����� http://127.0.0.1:8012/products �ͻᱨ��500��Ϊ���������⣬����ͼ��������Ӷ�·����

1. pom �� spring-cloud-starter-netflix-hystrix
2. ���� ProductClientFeignHystrix implements ProductClientFeign
3. �޸� ProductClientFeign ��ע�� @FeignClient(value = "PRODUCT-DATA-SERVICE", fallback = ProductClientFeignHystrix.class)
4. �����ļ�application.yml �� feign.hystrix.enabled: true
##### ��������
1. ���Ȱ������� EurekaServerApplication, ConfigServerApplication, ProductDataServiceApplication
2. Ȼ������������ͼ΢���� ProductViewServiceFeignApplication���˿ںŷֱ��� 8012, 8013.
3. ��ʱ����\
   http://127.0.0.1:8012/products \
   http://127.0.0.1:8013/products \
   ���Կ���git��ԭ�汾��
4. �޸� git ��İ汾��Ϊ�µ���ֵ
5. Ȼ������ FreshConfigUtil
6. �ٴη���\
   http://127.0.0.1:8012/products \
   http://127.0.0.1:8013/products \
   ���Կ��� �汾�����޸�֮���ֵ��

### �� hystrix-dashboard  Hystrix ��·�� ���
�����ݷ��񲻿��õ�ʱ�򣬶�·���ͻᷢ�����ã�
���ݷ���ʲôʱ����ã�ʲôʱ�򲻿��ã���μ����������أ����Ǿ�Ҫ�õ� ��·����� �����ӻ��ƿ���������

�ص㣺
1. �� ��ͼ������ ProductViewServiceFeignApplication �������
   @EnableCircuitBreaker   //ʹ�������԰���Ϣ�����������ģ����� ��·����أ�
2. �������һ�� AccessViewService

������
1. �������� EurekaServerApplication, ConfigServerApplication, ProductDataServiceApplication�� ProductViewServiceFeignApplication��HystrixDashboardApplication
2. ������ͼ΢����test��� AccessViewService �������Եط��� http://127.0.0.1:8012/products�� ��Ϊֻ�з����ˣ��������ܿ������ݡ�
3. �򿪼�ص�ַ http://localhost:8020/hystrix
4. ������������ http://localhost:8012/actuator/hystrix.stream �����ַ������ͼ΢����Ķ�·��Ϣ��
5. Ȼ���� Monitor Stream �Ϳ��Կ��������Ϣ�ˡ�

### ʮ zuul ����
��������������΢���񣬷ֱ�������΢�������ͼ΢����
�����п��ܷ��ڲ�ͬ�� ip ��ַ�ϣ��п����ǲ�ͬ�Ķ˿ڡ�
Ϊ�˷������ǣ�����Ҫ��¼��Щ��ַ�Ͷ˿ڡ� ����ַ�Ͷ˿ڶ����ܻ�仯����������˷����ߵĸ�����
�������ʱ�����ǾͿ��������������������⡣
��ͼ��ʾ������ֻ��Ҫ��ס���صĵ�ַ�Ͷ˿ںž����ˡ�\
���Ҫ�������ݷ��񣬷��ʵ�ַ http://ip:port/api-data/products ���ɡ�\
���Ҫ������ͼ���񣬷��ʵ�ַ http://ip:port/api-view/products ���ɡ�\

1. ���Ȱ������� EurekaServerApplication, ConfigServerApplication, ProductDataServiceApplication�� ProductViewServiceFeignApplication��
2. Ȼ������ ProductServiceZuulApplication
3. ���ŷ��ʵ�ַ��
http://localhost:8040/api-data/products \
http://localhost:8040/api-view/products \
�����Ϳ��Է�������΢�������΢����Ⱥ�ˣ���������ȥ��ס��ô��ip��ַ�Ͷ˿ں��ˡ�

---

### ������
1. EurekaServerApplication
2. ConfigServerApplication������ http://localhost:8030/version/dev
   ```
   {"name":"version","profiles":["dev"],"label":null,"version":"5046c9520e739312615997563357740456cc5756","state":null,"propertySources":[]}
   ```
3. �����������ݷ��� ProductDataServiceApplication�� �ֱ����� 8001��8002.
4. ��������feign  ProductViewServiceFeignApplication ������ ΢���񣬷���
   http://127.0.0.1:8012/products \
   ����������Ribbon ProductViewServiceRibbonApplication ������ ΢����Ȼ����ʵ�ַ��
   http://127.0.0.1:8010/products \
   ����Feign����ʹ��Ribbon��
5. ����zipkin��Ϊ: java -jar zipkin-server-2.10.1-exec.jar --zipkin.collector.rabbitmq.addresses=localhost \
   ִ��һ�� http://127.0.0.1:8012/products \
   ������·׷�ٷ����� http://localhost:9411/zipkin/dependency/ �Ϳ��Կ��� ��ͼ΢�����������΢���� ��ͼ�� 
6. ���� HystrixDashboardApplication
7. ���� ZuulApplication
---
΢����

eureka-server: 8761
product-data-service: 8001,8002,8003
product-view-service-ribbon: 8010
product-view-service-feign: 8012, 8013, 8014
hystrix-dashboard: 8020
turbine: 8021
config-server: 8030
zuul: 8040

��������

zipkin:9411
rabbitMQ: 5672
---