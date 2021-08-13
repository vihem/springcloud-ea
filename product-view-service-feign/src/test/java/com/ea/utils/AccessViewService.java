package com.ea.utils;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpUtil;

/**
 * 一个不停访问服务的类： AccessViewService。 这样可以不断地访问服务，才便于在监控那里观察现象。
 */
public class AccessViewService {
    public static void main(String[] args) {
        while(true) {
            ThreadUtil.sleep(1000);
            try {
                String html= HttpUtil.get("http://127.0.0.1:8012/products");
                System.out.println("html length:" + html.length());
            }
            catch(Exception e) {
                System.err.println(e.getMessage());
            }

        }

    }
}