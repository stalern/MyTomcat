package com.stalern.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 连接器，用来等待并获取Http请求
 * 多线程
 * @author stalern
 * @date 2019/10/6--21:51
 */
public class HttpConnector implements Runnable {

    private boolean stopped = false;
    private String scheme = "http";

    public String getScheme() {
        return scheme;
    }

    @Override
    public void run() {
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
        while (!stopped){
            Socket socket;
            // 拿到服务端接收的socket
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                continue;
            }

            // 把socket交给processor
            HttpProcessor processor = new HttpProcessor(this);
            processor.process(socket);
        }
    }

    public void start(){
        Thread thread = new Thread(this);
        thread.start();
    }
}
