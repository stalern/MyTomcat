package com.stalern;

import com.stalern.controller.PrimitiveServlet;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * servlet资源处理
 * 根据前端传入的URI获取路径，进而找到后端的Servlet，通过类加载器获取servlet
 * @author stalern
 * @date 2019/9/30--15:37
 */
class ServletProcessor {

    void process(Request request, Response response) {
        String uri = request.getUri();
        // 截取URI的最后一个路径作为servletName
        String servletName = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println(servletName);
        URLClassLoader urlClassLoader = null;

        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(HttpServer.WEB_CONTROLLER);

            // 拿到servlet的路径
            String repository = String.valueOf(new URL("file", null, classPath.getCanonicalPath() + File.separator));
            System.out.println(repository);
            urls[0] = new URL(null, repository, streamHandler);
            // 通过文件路径去加载urls，进而加载servlet类
            urlClassLoader = new URLClassLoader(urls);
        } catch (IOException e){
            e.printStackTrace();
        }

        Class clazz = null;
        try {
            // 获取包名
            String packName = PrimitiveServlet.class.getPackage().toString();
            System.out.println(packName);
            // 此处加载的是.class字节码文件，不是Java文件，同时，不仅仅是文件名，还有 它的包名
            clazz = urlClassLoader != null ? urlClassLoader.loadClass(packName.substring(packName.indexOf(" ") + 1) + "." + servletName) : null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Servlet servlet;

        try {
            if (clazz != null) {
                servlet = (Servlet) clazz.newInstance();
                // 防止servlet调用request和response的其他方法，故产生了外观类
                ResponseFacade responseFacade = new ResponseFacade(response);
                RequestFacade requestFacade = new RequestFacade(request);
                servlet.service(requestFacade, responseFacade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            System.out.println(e.toString());
        }
    }
}
