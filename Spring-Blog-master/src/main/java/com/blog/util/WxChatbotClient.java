package com.blog.util;

import com.blog.pojo.WebhookMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author shoji
 */
public class WxChatbotClient {

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 600000;
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", createSSLConnSocketFactory())
                .build();
        // コネクションプールの設定
        connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // コネクションプールの大きさの設定
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // タイムアウト
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // ソケットタイムアウト
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // コネクションインスタンスを取得するときのタイムアウト
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // リクエストする前にコネクションが使えるかをテスト
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    public static boolean send(String webhook, WebhookMessage message) throws IOException{
    	if(StringUtils.isBlank(webhook)){
    		return false;
    	}
        boolean flag = false;
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        try{
            HttpPost httppost = new HttpPost(webhook);
            httppost.addHeader("Content-Type", "application/json;charset=utf-8");
            StringEntity se = new StringEntity(objectMapper.writeValueAsString(message.toJsonString()), "utf-8");
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            System.out.println(response);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                flag = true;
            }
        } finally {
            return flag;
        }
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            sslsf = new SSLConnectionSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }
}


