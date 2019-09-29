package com.stalern;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.server.ExportException;

/**
 * @author stalern
 * @date 2019/9/29--18:08
 */
class Response {
    private static final int BUFFER_SIZE = 1024;
    private Request request;
    private OutputStream outputStream;

    Response(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    void setRequest(Request request) {
        this.request = request;
    }

    /**
     * 给客户端返回静态页面
     * @throws IOException 读取文件异常
     */
    void sendStaticResource() throws IOException {
        byte[] bytes = new byte[2048];
        FileInputStream fileInputStream = null;
        try {
            // 通过父路径(自己设置的) + 子路径(URI)拿到文件
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
            if (file.exists()) {
                // 注：原书中没有successMessage这一项，但是我按书上来时客户端不能解析这个响应，所以我就加了一个相应头
                String successMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n";
                outputStream.write(successMessage.getBytes());
                // 把文件作为输入流
                fileInputStream = new FileInputStream(file);
                // 把从文件中读入的字节放到bytes中，缓冲区为BUFFER_SIZE，字节个数为ch
                int ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    // 把bytes中读到的字节写入输出流中
                    outputStream.write(bytes, 0, ch);
                    ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);
                }
            } else {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                outputStream.write(errorMessage.getBytes());
            }
        } catch (Exception e){
            System.out.println(e.toString());
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
}
