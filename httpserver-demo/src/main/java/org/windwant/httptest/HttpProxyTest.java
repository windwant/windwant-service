package org.windwant.httptest;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Administrator on 18-7-6.
 */
public class HttpProxyTest {
    /**
     * 测试代理服务器 CCProxy8.0 设置本机为代理服务器
     */
    public static HttpClientContext context = HttpClientContext.create();
    public static void testProxy(){
        //代理主机
        HttpHost hp = new HttpHost("192.168.191.1", 808);
        CloseableHttpClient chc = HttpClients.custom().setProxy(hp).build();
        try(CloseableHttpResponse response = chc.execute(new HttpGet("http://localhost:8888/index.html"), context)) {
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testProxy();
    }
}
