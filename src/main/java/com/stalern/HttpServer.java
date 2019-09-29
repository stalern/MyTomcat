package com.stalern;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * v 0.1 通过解析客户端的URI，然后比较自身的路径，拿到路径资源，返回给客户端
 * @author stalern
 * @date 2019/9/29--13:02
 */
public class HttpServer {

    /**
     * resources 是我们存放静态资源的目录
     */
    static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "src\\main\\resources";

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    /**
     * 服务器等待连接并返回response
     */
    private void await(){

        // 获得服务端的socket
        ServerSocket serverSocket = null;
        String host = "127.0.0.1";
        int port = 8080;

        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName(host));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // 等待响应
        while (!shutdown){
            String uri = null;
            // 拿到服务端接收的socket
            try(Socket socket = serverSocket.accept()) {

                // 拿到客户端传给服务端的输入流，并构建输出流
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                // 把客户端的输入流解析为request
                Request request = new Request(inputStream);
                request.parse();

                // 服务端作出响应并把输出流response到客户端
                Response response = new Response(outputStream);
                response.setRequest(request);
                response.sendStaticResource();

                uri = request.getUri();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 接收URI，观察是否关闭服务器
            // 有时候不知道为什么会request会变成空指针
            shutdown = uri != null && uri.equals(SHUTDOWN_COMMAND);
        }
    }
}
