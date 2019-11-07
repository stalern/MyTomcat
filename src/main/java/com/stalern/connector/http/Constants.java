package com.stalern.connector.http;

import java.io.File;

public final class Constants {
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator + "src\\main\\resources";
    public static final String Package = "com.stalern.connector.http";
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final int PROCESSOR_IDLE = 0;
    public static final int PROCESSOR_ACTIVE = 1;
    public static final String WEB_CONTROLLER = "E:\\Codes\\GitHub\\Tomcat\\production\\MyTomcat\\com\\stalern\\controller";
}
