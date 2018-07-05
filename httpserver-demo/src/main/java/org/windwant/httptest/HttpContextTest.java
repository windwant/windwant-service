package org.windwant.httptest;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * test post context
 * 同一个会话的多个请求应该保持同一个httpcontext
 * Created by Administrator on 18-7-5.
 */
public class HttpContextTest {

    public static HttpClientContext context = HttpClientContext.create();
    public static void testHttpContext() {
        // 模拟表单
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("username", "test_context"));
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost post = new HttpPost("http://localhost:8888/index.html");
        post.setEntity(entity);
        try (CloseableHttpResponse response = HttpUtils.getClient().execute(post, context)){
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(post.getURI(), target, redirectLocations);
            System.out.println("Final HTTP location: " + location.toASCIIString());
            // Expected to be an absolute URI
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testHttpContext();
    }
}
