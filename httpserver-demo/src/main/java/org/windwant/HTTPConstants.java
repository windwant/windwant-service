package org.windwant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 18-7-4.
 */
public interface HTTPConstants {
    String WEB_ROOT = System.getProperty("user.dir") + "\\httpserver-demo\\src\\test\\resources\\webroot";

    Map<String, String> CONTENT_TYPE = new HashMap(){{
        put("html", "text/html");
        put("htm", "text/html");
        put("png", "image/png");
        put("jpg", "image/jpeg");
        put("js", "application/x-javascript");
    }};

    List<String> RESOURCES = new ArrayList(){{
       add("index.html");
       add("index1.html");
       add("index2.html");
       add("index3.html");
       add("index4.html");
       add("index5.html");
       add("head.png");
    }};
}
