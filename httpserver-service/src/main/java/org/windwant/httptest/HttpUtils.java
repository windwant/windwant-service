package org.windwant.httptest;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by windwant on 2016/6/5.
 */
public class HttpUtils {
    //应用结束时需要shutdown getTotalStats：leased当前使用的线程数，pending等待数，available可用数 max最大数
    private static PoolingHttpClientConnectionManager mgr;
    //CloseableHttpClient是线程安全的，建议相同的这个类的实例被重用于多个请求执行
    private static CloseableHttpClient client;
    private static HttpContext hc;

    public static PoolingHttpClientConnectionManager getMgr() {
        return mgr;
    }

    public static void setMgr(PoolingHttpClientConnectionManager mgr) {
        HttpUtils.mgr = mgr;
    }

    public static CloseableHttpClient getClient() {
        return client;
    }

    public static void setClient(CloseableHttpClient client) {
        HttpUtils.client = client;
    }

    public static HttpContext getHc() {
        return hc;
    }

    public static void setHc(HttpContext hc) {
        HttpUtils.hc = hc;
    }

    static {
        //安全套接字工厂 or SSLEngines 工厂类
        SSLContext sslContext = null;
        //支持的scheme
        Registry<ConnectionSocketFactory> registry = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            //server's X.509 certificate 主机验证
            HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
            //安全链接套接字工厂
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();

            registry = registryBuilder
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }


        mgr = registry == null?new PoolingHttpClientConnectionManager():new PoolingHttpClientConnectionManager(registry);
        ConnectionConfig config = ConnectionConfig.custom().setBufferSize(4 * 1024).setCharset(Charset.defaultCharset()).build();
        mgr.setDefaultConnectionConfig(config);
        SocketConfig sconfig = SocketConfig.custom()
                .setBacklogSize(65535) //backlog的定义是已连接但未进行accept处理的SOCKET队列大小
                .setRcvBufSize(4 * 1024)
                .setSndBufSize(4 * 1024)
                .setSoKeepAlive(false)
                .setSoTimeout(30000)
                .setTcpNoDelay(true)
                .build();
        mgr.setDefaultSocketConfig(sconfig);
        mgr.setDefaultMaxPerRoute(100);//每个路由最大连接数
        mgr.setMaxTotal(100); //设置整个连接池最大连接数
        mgr.setValidateAfterInactivity(2000);

        client = HttpClientBuilder.create()
                .setConnectionManager(mgr)
                .setConnectionReuseStrategy(new DefaultClientConnectionReuseStrategy())
                .setConnectionManagerShared(true)
                .setDefaultCredentialsProvider(new SystemDefaultCredentialsProvider())
                .addInterceptorFirst((HttpRequest request, HttpContext context) -> {//拦截器
                    request.addHeader("intercepter_var", "intercepter_var_" + ThreadLocalRandom.current().nextInt(10));
                })
                .setRetryHandler((exception, executionCount, context) -> {//重试
                    System.out.println("retry execute count: " + executionCount);
                    if(executionCount > 2){
                        return false;
                    }
                    if(exception instanceof NoHttpResponseException){
                        return true;
                    }
                    if(exception instanceof SSLHandshakeException){
                        return false;
                    }

                    HttpRequest request = (HttpRequest) context.getAttribute(HttpCoreContext.HTTP_REQUEST);
                    if(!(request instanceof HttpEntityEnclosingRequest)){
                        return true;
                    }
                    return false;
                })
                .build();
        hc = new BasicHttpContext();
        hc.setAttribute("count", 0);
    }

    public static CloseableHttpResponse httpGet(String url) throws IOException {
        System.out.println("conn mgr stats: " + mgr.getTotalStats());
        return client.execute(new HttpGet(url), hc);
    }

    public static CloseableHttpClient getInstance(){
        return HttpClients.createDefault();
    }

    public static HttpGet getHttpGet(String url){
        return new HttpGet(url);
    }
    public static HttpPost getHttpPost(String url){
        return new HttpPost(url);
    }

    /**
     * get request form
     * @param host
     * @param port
     * @param path
     * @param scheme
     * @param params
     * @return
     */
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

    public static void unmap(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }

            mappedByteBuffer.force();
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @SuppressWarnings("restriction")
                public Object run() {
                    try {
                        Method getCleanerMethod = mappedByteBuffer.getClass()
                                .getMethod("cleaner", new Class[0]);
                        getCleanerMethod.setAccessible(true);
                        sun.misc.Cleaner cleaner =
                                (sun.misc.Cleaner) getCleanerMethod
                                        .invoke(mappedByteBuffer, new Object[0]);
                        cleaner.clean();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("clean MappedByteBuffer completed");
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String formPost(String url, Map<String, String> paramPairs) {
        // 模拟表单
        List<NameValuePair> params = new ArrayList();
        paramPairs.entrySet().forEach(item -> params.add(new BasicNameValuePair(item.getKey(), item.getValue())));
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        String result = "";
        try (CloseableHttpResponse response = HttpUtils.getClient().execute(post)){
            System.out.println(response.getStatusLine());
            HttpEntity e = response.getEntity();
            result = EntityUtils.toString(e);
            EntityUtils.consume(e);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeHttp(null, post, null, null);
        }
        return result;
    }

    private static void closeHttp(CloseableHttpClient client, HttpPost httpPost, HttpGet httpGet, CloseableHttpResponse response){
        try {
            if(null != client) {
                client.close();
            }
            if(null != httpPost) {
                httpPost.releaseConnection();
            }
            if(null != httpGet) {
                httpGet.releaseConnection();
            }
            if(null != response) {
                response.close();
            }
        } catch (IOException e) {
            System.out.println("post error: " + e);
        }
    }

    public static void main(String[] args) {
        System.out.println(formPost("http://yongchetest.aachuxing.com//v3/tripshare/show",
                new HashMap() {{
                    put("order_id", "1811270650122");
                    put("user_id", "771252");
                }}));
    }
}
