package com.stalern.startup;

import com.stalern.connector.http.HttpConnector;

/**
 * 启动类
 * @author stalern
 * @date 2019/10/6--22:09
 */
public class Bootstrap {
    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        // 此处后期可增加线程池
        httpConnector.start();
    }
}
