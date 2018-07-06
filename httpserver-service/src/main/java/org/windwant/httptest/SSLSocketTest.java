package org.windwant.httptest;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by windwant on 2016/6/5.
 */
public class SSLSocketTest {
    public static void main(String[] args) {
        testSSL_TSL();
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
}
