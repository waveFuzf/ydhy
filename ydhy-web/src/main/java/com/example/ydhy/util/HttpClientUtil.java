package com.example.ydhy.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

@Component
public class HttpClientUtil {

    private static Logger LOG = Logger.getLogger(String.valueOf(HttpClientUtil.class));
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;

    static {
        connMgr = new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }


    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGetAndDelete(url, new HashMap<String, Object>(),HttpMethod.GET);
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param apiUrl
     * @param params
     * @return
     */
    public static String doGetAndDelete(String apiUrl, Map<String, Object> params, HttpMethod method) {
        if(MapUtils.isNotEmpty(params)){
            StringBuffer param = new StringBuffer();
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(params.get(key));
                i++;
            }
            apiUrl += param;
        }
        LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT请求参数:" + apiUrl);
        String result = null;
        HttpClientBuilder httpBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpBuilder.build();
        try {
            HttpRequestBase http = null;
            if(method.equals(HttpMethod.GET)){
                http = new HttpGet(apiUrl);
            } else if (method.equals(HttpMethod.DELETE)){
                http = new HttpDelete(apiUrl);
            }
            http.setHeader("Accept", "application/json, text/javascript, */*");
            HttpResponse response = closeableHttpClient.execute(http);
            int statusCode = response.getStatusLine().getStatusCode();

            LOG.info("执行状态码 : " + statusCode);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                result = IOUtils.toString(inputStream, "UTF-8");
            }
            LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT返回数据:" + result);
        } catch (IOException e) {
            LOG.info("HttpClientUtil Exception"+ e);
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) {
        return doPostWithMap(apiUrl, new HashMap<String, Object>());
    }

    public static String doPostWithMap(String apiUrl, Map<String, Object> params) {
        return doPostWithMap(apiUrl, params, new HashMap<String, String>());
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPostWithMap(String apiUrl, Map<String, Object> params, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        for (String key : headers.keySet()) {
            httpPost.addHeader(key, headers.get(key));
        }
        CloseableHttpResponse response = null;
        try {
            LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT请求参数:" + params.toString());
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            if(response != null
                    && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                LOG.info(response.toString());
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "UTF-8");
                LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT返回数据:" + httpStr);
            }

        } catch (IOException e) {
            LOG.info("HttpClientUtil Exception"+ e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.info("HttpClientUtil Exception");
                }
            }
        }
        return httpStr;
    }

    /**
     * 这种方式可以请求Restful的API
     * model + string类型的接口
     * 发送 POST 请求（HTTP）
     * 可以是String
     * 也可以是JSON形式
     *
     * @param apiUrl
     * @param json   json对象
     * @return
     */
    public static String doPostOrPutWihtString(String apiUrl, Object json,HttpMethod method)
            throws MethodNotSupportedException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpEntityEnclosingRequestBase http = null;
        if(method.equals(HttpMethod.POST)){
            http = new HttpPost(apiUrl);
        } else if (method.equals(HttpMethod.PUT)){
            http = new HttpPut(apiUrl);
        } else {
            LOG.info("doPostOrPutWihtString 方法不支持method :" + method);
            throw new MethodNotSupportedException("doPostOrPutWihtString 方法不支持method :" + method);
        }
        CloseableHttpResponse response = null;

        try {
            LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT请求参数:" + json.toString());
            http.setConfig(requestConfig);
            http.setHeader("Accept", "application/json, text/javascript, */*");
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            http.setEntity(stringEntity);
            response = httpClient.execute(http);
            HttpEntity entity = response.getEntity();
            LOG.info(String.valueOf(response.getStatusLine().getStatusCode()));
            httpStr = EntityUtils.toString(entity, "UTF-8");
            LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT返回数据:" + httpStr);
        } catch (IOException e) {
            LOG.info("HttpClientUtil Exception"+ e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.info("HttpClientUtil Exception");
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT请求参数:" + params.toString());
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
            LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT返回数据:" + httpStr);
        } catch (Exception e) {
            LOG.info("HttpClientUtil Exception"+ e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.info("HttpClientUtil Exception");
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     *
     * @param apiUrl API接口URL
     * @param json   JSON对象
     * @return
     */
    public static String doPostSSL(String apiUrl, Object json) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT请求参数:" + json.toString());
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
            LOG.info(">>>>>>>>>>>>>>>> HTTP CLIENT返回数据:" + httpStr);
        } catch (Exception e) {
            LOG.info("HttpClientUtil Exception"+ e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.info("HttpClientUtil Exception");
                }
            }
        }
        return httpStr;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static LayeredConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            LOG.info("HttpClientUtil Exception"+ e);
        }
        return sslsf;
    }
}
