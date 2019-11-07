package com.stalern.connector.http;

import com.stalern.ServletProcessor;
import com.stalern.StaticResourceProcessor;
import com.stalern.util.RequestUtil;
import org.apache.tomcat.util.res.StringManager;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


/**
 * 创建request和response对象，同时被HttpConnector调用
 * @author stalern
 * @date 2019/10/6--22:04
 */
class HttpProcessor {

    private HttpConnector connector = null;
    private HttpRequest request;
    private HttpResponse response;
    private HttpRequestLine requestLine = new HttpRequestLine();
    /**
     * 该包的字符串管理类
     */
    private StringManager sm = StringManager.getManager(Constants.Package);

    HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    void process(Socket socket) {
        // 拿到客户端传给服务端的输入流, 并构建输出流
        SocketInputStream inputStream;
        OutputStream outputStream;
        try {

            inputStream = new SocketInputStream(socket.getInputStream(), 2048);
            outputStream = socket.getOutputStream();

            // 把客户端的输入流解析为request
            request = new HttpRequest(inputStream);

            // 服务端作出响应并把输出流response到客户端
            response = new HttpResponse(outputStream);
            response.setRequest(request);
            response.setHeader("Server", "JerryRat Servlet Container");

            // 解析请求行和请求头，只解析请求中的必须部分，其他的如请求体和请求参数（因为这些不必须）需要HttpRequest自己解决
            parseRequest(inputStream, outputStream);
            parseHeaders(inputStream);

            String uri = request.getRequestURI();
            String servlet = "/servlet";
            if (uri != null) {
                if (uri.startsWith(servlet)) {
                    ServletProcessor servletProcessor = new ServletProcessor();
                    servletProcessor.process(request, response);
                } else {
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.process(request, response);
                }

                // close the socket
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是org.apache.catalina.connector.http.HttpProcessor的简化版
     * 但是这个方法只能处理类似于cookie, content-length, content-type // 待议
     * @param input SocketInputStream
     * @throws ServletException 解析错误
     * @throws IOException 输入输出错误
     */
    private void parseHeaders(SocketInputStream input) throws ServletException, IOException {
        while (true) {
            HttpHeader header = new HttpHeader();

            // 读出下一个请求头，readHeader会把input的值填充给header对象
            input.readHeader(header);
            if (header.nameEnd == 0) {
                if (header.valueEnd == 0) {
                    return;
                } else {
                    throw new ServletException(sm.getString("httpProcessor.parseHeaders.colon"));
                }
            }

            String name = new String(header.name, 0, header.nameEnd);
            String value = new String(header.value, 0, header.valueEnd);
            request.addHeader(name, value);

            if ("cookie".equals(name)) {
                Cookie[] cookies = RequestUtil.parseCookieHeader(value);
            }
        }
    }

    /**
     * 解析URI
     * @param inputStream socketInputStream
     * @param outputStream 输出流
     * @throws ServletException 异常
     */
    private void parseRequest(SocketInputStream inputStream, OutputStream outputStream) throws ServletException, IOException {
        // 解析请求行
        inputStream.readRequestLine(requestLine);
        String method = new String(requestLine.method, 0, requestLine.methodEnd);
        String uri = null;
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

        // 验证请求
        if (method.length() < 1){
            throw new ServletException("Missing Http Request Method");
        } else if (requestLine.uriEnd < 1){
            throw new ServletException("Missing Http Request URI");
        }

        // 从URI中解析参数
        int question = requestLine.indexOf("?");
        if (question >= 0) {
            request.setQueryString(new String(requestLine.uri, question + 1, requestLine.uriEnd - question- 1));
        } else {
            request.setQueryString(null);
        }
        // 拿到相对路径的URI
        uri = new String(requestLine.uri, 0, requestLine.uriEnd);

        // 检查URI是否是绝对路径，如果是，转为相对路径
        // http://www.stonee.club/index.html?name=me
        if (!uri.startsWith("/")) {
            int pos = uri.indexOf("://");
            // 解析协议和主机名
            if (pos != -1) {
                pos = uri.indexOf('/', pos + 3);
                if (pos == -1){
                    uri = "";
                } else {
                    uri = uri.substring(pos);
                }
            }
        }

        // 解析在URI中的jsessionid，为了防止浏览器禁用cookie，
        // 例子?username=1;jsessionid=9;uid=0  / ?username=1;jsessionid=9
        String match = ";jsessionid=";
        int semicolon = uri.indexOf(match);
        if (semicolon >= 0) {
            // 解析出jsessionid的值(以及其后面的东西)
            String rest = uri.substring(semicolon + match.length());
            int semicolon2 = rest.indexOf(";");
            // 如果不是最后一个，即有";"
            if (semicolon2 >= 0) {
                request.setRequestedSessionId(rest.substring(0, semicolon2));
                // 拿到除了jsessionid值之外的值
                rest = rest.substring(semicolon2);
            } else {
                // 否则是最后一个
                request.setRequestedSessionId(rest);
                rest = "";
            }
            // 把requestSessionURL标记为true
            request.setRequestedSessionURL(true);
            // 此时的URI不包含jsessionid和它的值
            uri = uri.substring(0, semicolon) + rest;
        } else {
            request.setRequestedSessionId(null);
            request.setRequestedSessionURL(false);
        }

        // 格式化URI，如把"\"变为"/"
        String normalizedUri = normalize(uri);
        request.setMethod(method);
        request.setProtocol(protocol);
        if (normalizedUri != null) {
            request.setRequestURI(normalizedUri);
        } else {
            request.setRequestURI(uri);
        }
        if (normalizedUri == null){
            throw new ServletException("Invalid URI: " + uri + "'");
        }

    }

    private String normalize(String uri){
        if (uri == null) {
            return null;
        }
        String normalized = uri;
        // Normalize "/%7E" and "/%7e" at the beginning to "/~"
        if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e")) {
            normalized = "/~" + normalized.substring(4);
        }

        // Prevent encoding '%', '/', '.' and '\', which are special reserved
        // characters
        if ((normalized.contains("%25"))
                || (normalized.contains("%2F"))
                || (normalized.contains("%2E"))
                || (normalized.contains("%5C"))
                || (normalized.contains("%2f"))
                || (normalized.contains("%2e"))
                || (normalized.contains("%5c"))) {
            return null;
        }

        if (normalized.equals("/.")) {
            return "/";
        }

        // Normalize the slashes and add leading slash if necessary
        if (normalized.indexOf('\\') >= 0) {
            normalized = normalized.replace('\\', '/');
        }
        if (!normalized.startsWith("/")) {
            normalized = "/" + normalized;
        }

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0) {
                break;
            }
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0) {
                break;
            }
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0) {
                break;
            }
            if (index == 0) {
                return (null);  // Trying to go outside our context
            }
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // Declare occurrences of "/..." (three or more dots) to be invalid
        // (on some Windows platforms this walks the directory tree!!!)
        if (normalized.contains("/...")) {
            return (null);
        }

        // Return the normalized path that we have completed
        return (normalized);
    }
}
