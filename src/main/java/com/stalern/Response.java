package com.stalern;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * 响应类，可以相应静态文件
 * @author stalern
 * @date 2019/9/29--18:08
 */
class Response implements ServletResponse {
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

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        // 把自动刷新设置为true，对println自动刷新到输出流中
        return new PrintWriter(outputStream, true);
    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
