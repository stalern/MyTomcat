package com.stalern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * request的外观类，防止request的其他重要方法被service调用
 * @author stalern
 * @date 2019/10/1--22:42
 */
public class RequestFacade implements ServletRequest {

    private ServletRequest servletRequest = null;

    public RequestFacade(Request request) {
        this.servletRequest = request;
    }

    @Override
    public Object getAttribute(String s) {
        return servletRequest.getAttribute(s);
    }

    @Override
    public Enumeration getAttributeNames() {
        return servletRequest.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return servletRequest.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        servletRequest.setCharacterEncoding(s);
    }

    @Override
    public int getContentLength() {
        return servletRequest.getContentLength();
    }

    @Override
    public String getContentType() {
        return servletRequest.getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return servletRequest.getInputStream();
    }

    @Override
    public String getParameter(String s) {
        return servletRequest.getParameter(s);
    }

    @Override
    public Enumeration getParameterNames() {
        return servletRequest.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String s) {
        return servletRequest.getParameterValues(s);
    }

    @Override
    public Map getParameterMap() {
        return servletRequest.getParameterMap();
    }

    @Override
    public String getProtocol() {
        return servletRequest.getProtocol();
    }

    @Override
    public String getScheme() {
        return servletRequest.getScheme();
    }

    @Override
    public String getServerName() {
        return servletRequest.getServerName();
    }

    @Override
    public int getServerPort() {
        return servletRequest.getServerPort();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return servletRequest.getReader();
    }

    @Override
    public String getRemoteAddr() {
        return servletRequest.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return servletRequest.getRemoteHost();
    }

    @Override
    public void setAttribute(String s, Object o) {
        servletRequest.setAttribute(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        servletRequest.removeAttribute(s);
    }

    @Override
    public Locale getLocale() {
        return servletRequest.getLocale();
    }

    @Override
    public Enumeration getLocales() {
        return servletRequest.getLocales();
    }

    @Override
    public boolean isSecure() {
        return servletRequest.isSecure();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return servletRequest.getRequestDispatcher(s);
    }

    @Override
    public String getRealPath(String s) {
        return servletRequest.getRealPath(s);
    }
}
