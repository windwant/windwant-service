package org.windwant.httptest;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by windwant on 2016/6/5.
 */
public class MyHttpTest {
    public static final String URL_PNG = "http://pic1.win4000.com/wallpaper/b/594dfcbbf2b24.jpg";
    public static void main(String[] args) {
        testSSL_TSL();
    }

    public static void testMgr(){
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();

            Registry<ConnectionSocketFactory> registry = registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();
            PoolingHttpClientConnectionManager pconn = new PoolingHttpClientConnectionManager(registry);
            pconn.setMaxTotal(100);
            pconn.setDefaultMaxPerRoute(10);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(10000).build();
            pconn.setDefaultSocketConfig(socketConfig);

            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(pconn).build();
            HttpGet hp = new HttpGet("http://www.baidu.com");
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(hp);
            System.out.println(EntityUtils.toString(closeableHttpResponse.getEntity()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpClientConnectionManager hccm = new BasicHttpClientConnectionManager();

    }

    public static void testProxy(){
        HttpHost hp = new HttpHost("http://www.baidu.com");
        CloseableHttpClient chc = HttpClients.custom().setProxy(hp).build();
    }

    public static void testSSL_TSL(){
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager trustManager = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            System.out.println(sslContext.getProtocol());

            SSLSocketFactory sslSocketFactory = SSLContext.getDefault().getSocketFactory();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket();
            sslSocket.setEnabledCipherSuites(new String[]{"SSL_RSA_WITH_RC4_128_MD5"});
            sslSocket.connect(new InetSocketAddress("localhost", 8888));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testFormSubmit() {
        CloseableHttpClient chc = HttpClients.createDefault();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("VendorBusinessType", "2"));
        CloseableHttpResponse chr = null;
        try {
            HttpGet get = HttpUtils.getHttpGet("localhost", 8006, "/test/postfile", null, params);
            chr = chc.execute(get);
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
}
