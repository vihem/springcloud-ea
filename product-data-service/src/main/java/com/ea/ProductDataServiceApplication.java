package com.ea;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@EnableEurekaClient
public class ProductDataServiceApplication {
    public static void main( String[] args ) {
        int port;   //最后使用的端口号
        int defaultPort = 8001; //默认端口号
        /*
            异步执行 输入端口号
            Future代表一个异步执行的操作，通过get()方法可以获得操作的结果，如果异步操作还没有完成，则，get()会使当前线程阻塞
            ThreadUtil 是 hutool的工具
            ThreadUtil.execAsync 执行有返回值的异步方法
         */
        Future<Integer> future = ThreadUtil.execAsync(()->{
            int res;
            System.out.println("请于5秒钟内输入端口号, 推荐  8001 、 8002  或者  8003，超过5秒将默认使用 " + defaultPort);
            Scanner sc = new Scanner(System.in);
            while (true){
                String strPort = sc.nextLine();
                if(!NumberUtil.isInteger(strPort)){
                    System.err.println("只能是数字");
//                    continue;
                } else {
                    res = Convert.toInt(strPort);
                    sc.close();
                    break;
                }
            }
            return res;
        });
        try {
            port = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            //如果5秒不输入，那么就默认使用 8001端口
            port = defaultPort;
        }
        if(!NetUtil.isUsableLocalPort(port)){
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(ProductDataServiceApplication.class).properties("server.port=" + port).run(args);
    }
}
