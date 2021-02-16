package com.p6e.silkworm.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 该类为 HTTP 请求的工具类
 * @author lidashuang
 * @version 1.0
 */
public final class P6eHttpUtil implements P6eHttpInterface {

    /**
     * HTTPS
     */
    private static final String HTTPS = "https";

    /**
     * HTTP 客户端
     */
    private static HttpClient HTTP_CLIENT = null;

    /**
     * HTTPS 客户度
     */
    private static HttpClient HTTPS_CLIENT = null;

    /**
     * CONTENT_TYPE
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * APPLICATION_JSON
     */
    public static final String APPLICATION_JSON = "application/json";

    /**
     * APPLICATION_X_WWW_FORM_URLENCODED
     */
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private String globalProxyHost;
    private String globalProxyPort;
    private Map<String, String> globalHeaders;

    /**
     * URL 参数格式化
     * @param url url 参数
     * @param params 请求参数
     * @return 格式化后的结果
     * @throws IOException 格式化中产生的 IO 异常
     */
    public static String urlParamFormat(String url, Map<String, String> params) throws IOException {
        return urlParamFormat(url, params, true);
    }

    /**
     * URL 参数格式化
     * @param url url 参数
     * @param params 请求参数
     * @param isAddUrl 是否添加 url
     * @return 格式化后的结果
     * @throws IOException 格式化中产生的 IO 异常
     */
    public static String urlParamFormat(String url, Map<String, String> params, boolean isAddUrl) throws IOException {
        if (params != null) {
            StringBuilder pContent = new StringBuilder();
            for (String name : params.keySet()) {
                pContent.append("&")
                        .append(URLEncoder.encode(name, "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(params.get(name), "UTF-8"));
            }
            if (params.size() > 0) {
                if (url.contains("?")) {
                    return isAddUrl ? (url + pContent) : pContent.substring(1);
                } else {
                    return (isAddUrl ? (url + "?") : "") + pContent.substring(1);
                }
            }
        }
        return isAddUrl ? url : "";
    }

    /**
     * APPLICATION_JSON 参数格式化
     * @param params 请求参数
     * @param headers 头部参数
     * @return 格式化后的结果
     * @throws IOException 格式化中产生的 IO 异常
     */
    public static StringEntity applicationJsonParamFormat(String params, Map<String, String> headers) throws IOException {
        if (headers == null) {
            headers = new HashMap<>(1);
            headers.put(CONTENT_TYPE, APPLICATION_JSON);
        }
        if (APPLICATION_JSON.equals(headers.get(CONTENT_TYPE))) {
            if  (params != null) {
                return new StringEntity(params);
            }
        }
        return null;
    }

    /**
     * APPLICATION_X_WWW_FORM_URLENCODED 参数格式化
     * @param params 请求参数
     * @param headers 头部参数
     * @return 格式化后的结果
     * @throws IOException 格式化中产生的 IO 异常
     */
    public static StringEntity applicationXWwwFormUrlencodedParamFormat(Map<String, String> params, Map<String, String> headers) throws IOException {
        if (headers == null) {
            headers = new HashMap<>(1);
            headers.put(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED);
        }
        if (APPLICATION_X_WWW_FORM_URLENCODED.equals(headers.get(CONTENT_TYPE))) {
            final String content = urlParamFormat("", params, false);
            if (content != null && !content.isEmpty()) {
                return new StringEntity(content);
            }
        }
        return null;
    }

    @Override
    public void setGlobalProxy(String proxyHost, String proxyPort) {
        this.globalProxyHost = proxyHost;
        this.globalProxyPort = proxyPort;
    }

    @Override
    public void setGlobalHeaders(Map<String, String> headers) {
        this.globalHeaders = headers;
    }

    @Override
    public String doGet(String url, Map<String, String> params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        return httpString(new HttpGet(urlParamFormat(url, params)), headers, proxyHost, proxyPort);
    }

    @Override
    public String doDelete(String url, Map<String, String> params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        return httpString(new HttpDelete(urlParamFormat(url, params)), headers, proxyHost, proxyPort);
    }

    @Override
    public String doPut(String url, String params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        final HttpPut httpPut = new HttpPut(url);
        final StringEntity stringEntity = applicationJsonParamFormat(params, headers);
        if (stringEntity != null) {
            httpPut.setEntity(stringEntity);
        }
        return httpString(httpPut, headers, proxyHost, proxyPort);
    }

    @Override
    public String doPost(String url, String params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        final HttpPost httpPost = new HttpPost(url);
        final StringEntity stringEntity = applicationJsonParamFormat(params, headers);
        if (stringEntity != null) {
            httpPost.setEntity(stringEntity);
        }
        return httpString(httpPost, headers, proxyHost, proxyPort);
    }

    @Override
    public String doPut(String url, Map<String, String> params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        final HttpPut httpPut = new HttpPut(url);
        final StringEntity stringEntity = applicationXWwwFormUrlencodedParamFormat(params, headers);
        if (stringEntity != null) {
            httpPut.setEntity(stringEntity);
        }
        return httpString(httpPut, headers, proxyHost, proxyPort);
    }

    @Override
    public String doPost(String url, Map<String, String> params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        final HttpPost httpPost = new HttpPost(url);
        final StringEntity stringEntity = applicationXWwwFormUrlencodedParamFormat(params, headers);
        if (stringEntity != null) {
            httpPost.setEntity(stringEntity);
        }
        return httpString(httpPost, headers, proxyHost, proxyPort);
    }

    @Override
    public String doPut(String url, HttpEntity params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        final HttpPut httpPut = new HttpPut(url);
        if (params != null) {
            httpPut.setEntity(params);
        }
        return httpString(httpPut, headers, proxyHost, proxyPort);
    }

    /**
     *
     * final FileBody fileBody = new FileBody(new File(""));
     * final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
     * builder.addPart("files", fileBody);
     * builder.addPart("filesFileName", new StringBody("", ContentType.create("text/plain", Consts.UTF_8)));
     * builder.build();
     *
     */
    @Override
    public String doPost(String url, HttpEntity params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        final HttpPost httpPost = new HttpPost(url);
        if (params != null) {
            httpPost.setEntity(params);
        }
        return httpString(httpPost, headers, proxyHost, proxyPort);
    }


    public HttpResponse http(HttpUriRequest httpUriRequest, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        final String scheme = httpUriRequest.getURI().getScheme();
        HttpClient client;
        // 判断是否存在 HTTP 客户端对象
        if (HTTP_CLIENT == null) {
            HTTP_CLIENT = HttpClients.createDefault();
        }
        // 初始化为 HTTP 客户端对象
        client = HTTP_CLIENT;
        // 判断是否为 HTTPS 对象
        if (HTTPS.equals(scheme)) {
            if (HTTPS_CLIENT == null) {
                HTTPS_CLIENT = new MyHttpsClient();
            }
            // 替换为 HTTPS 请求对象
            client = HTTPS_CLIENT;
        }

        if (globalProxyHost != null && globalProxyPort != null) {
            if (proxyHost != null && proxyPort != null) {
                if ("".equals(proxyHost) && "".equals(proxyPort)) {

                } else {
                    final HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort), scheme);
                    client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
                }
            } else {
                final HttpHost proxy = new HttpHost(globalProxyHost, Integer.parseInt(globalProxyPort), scheme);
                client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            }

        }

        if (globalHeaders != null) {
            for (String name : globalHeaders.keySet()) {
                httpUriRequest.setHeader(name, globalHeaders.get(name));
            }
        }

        if (headers != null) {
            for (String name : headers.keySet()) {
                httpUriRequest.setHeader(name, headers.get(name));
            }
        }
        return client.execute(httpUriRequest);
    }


    public String httpString(HttpUriRequest httpUriRequest, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException {
        HttpEntity httpEntity = http(httpUriRequest, headers, proxyHost, proxyPort).getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    public static void doGetImg(File file, String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        if (params != null) {
            StringBuilder pContent = new StringBuilder();
            for (String name : params.keySet()) {
                pContent.append("&")
                        .append(URLEncoder.encode(name, "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(params.get(name), "UTF-8"));
            }
            if (params.size() > 0) {
                url += "?" + pContent.substring(1);
            }
        }

        HttpUriRequest httpUriRequest = new HttpGet(url);

        final String scheme = httpUriRequest.getURI().getScheme();
        HttpClient client;
        // 判断是否存在 HTTP 客户端对象
        if (HTTP_CLIENT == null) {
            HTTP_CLIENT = HttpClients.createDefault();
        }
        // 初始化为 HTTP 客户端对象
        client = HTTP_CLIENT;
        // 判断是否为 HTTPS 对象
        if (HTTPS.equals(scheme)) {
            if (HTTPS_CLIENT == null) {
                HTTPS_CLIENT = new MyHttpsClient();
            }
            // 替换为 HTTPS 请求对象
            client = HTTPS_CLIENT;
        }

        // 设置代理IP、端口、协议（请分别替换）
        final HttpHost proxy = new HttpHost("127.0.0.1", 10887, "http");

        // 设置代理
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

        if (headers != null) {
            for (String name : headers.keySet()) {
                httpUriRequest.setHeader(name, headers.get(name));
            }
        }
        HttpResponse httpResponse = client.execute(httpUriRequest);
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            FileOutputStream fos = new FileOutputStream(file);
            InputStream stream = httpEntity.getContent();
            int i;
            byte[] buffer = new byte[1024];
            while ((i = stream.read(buffer)) != -1) {
                fos.write(buffer, 0, i);
            }
            stream.close();
            fos.flush();
            fos.close();
        }
        if (httpEntity != null){
            httpEntity.consumeContent();
        }
    }

    /**
     * 创建 HTTPS 客户端，且绕过证书校验
     */
    public static class MyHttpsClient extends DefaultHttpClient {
        public MyHttpsClient() throws IOException {
            super();
            try {
                // 绕过 HTTPS/SSL 证书验证
//                final SSLContext ctx = SSLContext.getInstance("TLS");
//                final X509TrustManager tm = new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                    }
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
//                    }
//                };
//                ctx.init(null, new TrustManager[]{ tm }, null);
                SSLContext ctx = SSLContexts.custom()
                        .loadTrustMaterial(new File("/Users/admin/Documents/my.keystore"), "123456".toCharArray(),
                                new TrustSelfSignedStrategy())
                        .build();
                final SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                final ClientConnectionManager ccm = this.getConnectionManager();
                SchemeRegistry sr = ccm.getSchemeRegistry();
                sr.register(new Scheme("https", 443, ssf));
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        }
    }
}
