package com.stalern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 请求类，做了一件事，就是解析了HTTP请求的URI
 * 同时，继承了ServletRequest
 * @author stalern
 * @date 2019/9/29--13:53
 */
class Request implements ServletRequest {
    private InputStream inputStream;
    private String uri;

    Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 解析HTTP请求中的原始数据
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

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public Enumeration getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }
}
