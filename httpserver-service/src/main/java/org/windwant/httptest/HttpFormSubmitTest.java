package org.windwant.httptest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * test post context
 * 同一个会话的多个请求应该保持同一个httpcontext
 * Created by Administrator on 18-7-5.
 */
public class HttpFormSubmitTest {

    public static HttpClientContext context = HttpClientContext.create();
    public static void testFormPostSubmit() {
        // 模拟表单
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("username", "test_context"));
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost("http://localhost:8888/index.html");
        post.setEntity(entity);
        try (CloseableHttpResponse response = HttpUtils.getClient().execute(post, context)){
            System.out.println(response.getStatusLine());
            HttpEntity e = response.getEntity();
            System.out.println(EntityUtils.toString(e));
            EntityUtils.consume(e);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testFormGetSubmit() {
        CloseableHttpClient chc = HttpClients.createDefault();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "test"));
        CloseableHttpResponse chr = null;
        try {
            HttpGet get = HttpUtils.getHttpGet("localhost", 8888, "/index.html", null, params);
            chr = chc.execute(get, context);
            System.out.println(chr.getStatusLine());
            HttpEntity e = chr.getEntity();
            System.out.println(EntityUtils.toString(e));
            EntityUtils.consume(e);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                chr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        testFormPostSubmit();
        testFormGetSubmit();
    }
}
