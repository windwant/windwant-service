package org.windwant.httptest;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by windwant on 2016/6/5.
 */
public class HttpUtils {
    public static CloseableHttpClient getInstance(){
        return HttpClients.createDefault();
    }

    public static HttpGet getHttpGet(String url){
        return new HttpGet(url);
    }
    public static HttpPost getHttpPost(String url){
        return new HttpPost(url);
    }

    public static HttpGet getHttpGet(String host, int port, String path, String scheme, List<NameValuePair> params){
        URIBuilder ub = new URIBuilder();
        ub.setScheme(null != scheme?scheme:"http");
        ub.setHost(host);
        ub.setPort(port);
        ub.setPath(path);
        if(null != params) {
            ub.setParameters(params);
        }
        try {
            return new HttpGet(ub.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
