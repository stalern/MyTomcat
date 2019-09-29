package com.stalern;

import java.io.File;

/**
 * @author stalern
 * @date 2019/9/29--13:02
 */
public class HttpServer {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();

        server.await();
    }

    public void await(){

    }
}
