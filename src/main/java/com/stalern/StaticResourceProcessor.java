package com.stalern;

import java.io.IOException;

/**
 * 静态资源处理器
 * @author stalern
 * @date 2019/9/30--15:33
 */
class StaticResourceProcessor {
    void process(Request request, Response response){
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
