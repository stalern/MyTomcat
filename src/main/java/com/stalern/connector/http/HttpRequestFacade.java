package com.stalern.connector.http;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * request的外观类，防止request的其他重要方法被service调用
 * @author stalern
 * @date 2019/11/05~19:12
 */
public class HttpRequestFacade implements HttpServletRequest{

    private HttpServletRequest request;

    public HttpRequestFacade(HttpRequest request) {
        this.request = request;
    }

    /* implementation of the HttpServletRequest*/
    @Override

    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }

    @Override
    public Enumeration getAttributeNames() {
        return request.getAttributeNames();
    }

    @Override
    public String getAuthType() {
        return request.getAuthType();
    }

    @Override
    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    @Override
    public int getContentLength() {
        return request.getContentLength();
    }

    @Override
    public String getContentType() {
        return request.getContentType();
    }

    @Override
    public String getContextPath() {
        return request.getContextPath();
    }

    @Override
    public Cookie[] getCookies() {
        return request.getCookies();
    }
    @Override
    public long getDateHeader(String name) {
        return request.getDateHeader(name);
    }
    @Override
    public Enumeration getHeaderNames() {
        return request.getHeaderNames();
    }
    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }
    @Override
    public Enumeration getHeaders(String name) {
        return request.getHeaders(name);
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return request.getInputStream();
    }
    @Override
    public int getIntHeader(String name) {
        return request.getIntHeader(name);
    }
    @Override
    public Locale getLocale() {
        return request.getLocale();
    }
    @Override
    public Enumeration getLocales() {
        return request.getLocales();
    }
    @Override
    public String getMethod() {
        return request.getMethod();
    }
    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
    }
    @Override
    public Map getParameterMap() {
        return request.getParameterMap();
    }
    @Override
    public Enumeration getParameterNames() {
        return request.getParameterNames();
    }
    @Override
    public String[] getParameterValues(String name) {
        return request.getParameterValues(name);
    }
    @Override
    public String getPathInfo() {
        return request.getPathInfo();
    }
    @Override
    public String getPathTranslated() {
        return request.getPathTranslated();
    }
    @Override
    public String getProtocol() {
        return request.getProtocol();
    }
    @Override
    public String getQueryString() {
        return request.getQueryString();
    }
    @Override
    public BufferedReader getReader() throws IOException {
        return request.getReader();
    }
    @Override
    public String getRealPath(String path) {
        return request.getRealPath(path);
    }
    @Override
    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }
    @Override
    public String getRemoteHost() {
        return request.getRemoteHost();
    }
    @Override
    public String getRemoteUser() {
        return request.getRemoteUser();
    }
    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return request.getRequestDispatcher(path);
    }
    @Override
    public String getRequestedSessionId() {
        return request.getRequestedSessionId();
    }
    @Override
    public String getRequestURI() {
        return request.getRequestURI();
    }
    @Override
    public StringBuffer getRequestURL() {
        return request.getRequestURL();
    }
    @Override
    public String getScheme() {
        return request.getScheme();
    }
    @Override
    public String getServerName() {
        return request.getServerName();
    }
    @Override
    public int getServerPort() {
        return request.getServerPort();
    }
    @Override
    public HttpSession getSession() {
        return request.getSession();
    }
    @Override
    public HttpSession getSession(boolean create) {
        return request.getSession(create);
    }
    @Override
    public String getServletPath() {
        return request.getServletPath();
    }
    @Override
    public Principal getUserPrincipal() {
        return request.getUserPrincipal();
    }
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return request.isRequestedSessionIdFromCookie();
    }
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return request.isRequestedSessionIdFromURL();
    }
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return request.isRequestedSessionIdFromURL();
    }
    @Override
    public boolean isRequestedSessionIdValid() {
        return request.isRequestedSessionIdValid();
    }
    @Override
    public boolean isSecure() {
        return request.isSecure();
    }
    @Override
    public boolean isUserInRole(String role) {
        return request.isUserInRole(role);
    }
    @Override
    public void removeAttribute(String attribute) {
        request.removeAttribute(attribute);
    }
    @Override
    public void setAttribute(String key, Object value) {
        request.setAttribute(key, value);
    }
    @Override
    public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
        request.setCharacterEncoding(encoding);
    }

}
