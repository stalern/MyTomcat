package com.stalern;

import com.stalern.connector.http.HttpRequest;
import com.stalern.connector.http.HttpResponse;

import java.io.IOException;

/**
 * 静态资源处理器
 * @author stalern
 * @date 2019/9/30--15:33
 */
public class StaticResourceProcessor {
    public void process(HttpRequest request, HttpResponse response){
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
