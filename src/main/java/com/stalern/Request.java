package com.stalern;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author stalern
 * @date 2019/9/29--13:53
 */
class Request {
    private InputStream inputStream;
    private String uri;

    Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 解析HTTP请求中的原始数据（
     * 把HTTP请求的输入流转化为字符串
     */
    void parse() {
        // 读出socket中的字符
        StringBuilder request = new StringBuilder(2048);
        // read的次数
        int i;
        // 每次read的缓冲区大小
        byte[] buffer = new byte[2048];

        try {
            i = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            // i = -1 说明没有读入
            i = -1;
        }

        // 把缓冲buffer中的输入流读入request
        for (int j = 0; j < i; j++) {
            // 此处必须要转换为char，不然是一堆输入流
            request.append((char)buffer[j]);
        }
        System.out.println(request.toString());
        uri = parseUri(request.toString());
    }

    /**
     * 解析HTTP请求中的URI
     * @param requestString 请求
     * @return URI
     */
    private String parseUri(String requestString) {
        // POST /uri HTTP/1.1
        int index1, index2;
        index1 = requestString.indexOf(' ');
        // 如果出现该字符串
        if (index1 != -1){
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index1 < index2){
                return requestString.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    String getUri() {
        return uri;
    }
}
