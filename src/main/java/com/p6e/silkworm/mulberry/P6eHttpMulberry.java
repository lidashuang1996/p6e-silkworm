package com.p6e.silkworm.mulberry;

import org.apache.http.HttpEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 网络队列消息对象
 * @author lidashuang
 * @version 1.0
 */
public class P6eHttpMulberry extends P6eMulberry implements Serializable {

    /**
     * 类型名称
     */
    public static final String TYPE = "HTTP_TYPE";
    public static final String GET_METHDO = "get";
    public static final String POST_METHDO = "post";
    public static final String PUT_METHDO = "put";
    public static final String DELETE_METHDO = "delete";

    /**
     * URL
     */
    private String url;

    /**
     * 请求 body
     */
    private String stringBody;
    private HttpEntity httpEntityBody;
    private Map<String, String> mapBody;

    /**
     * 请求的方法
     */
    private String method;

    /**
     * 代理
     */
    private String proxyHost;
    private String proxyPort;

    /**
     * 头部数据
     */
    private final Map<String, String> headers = new HashMap<>();


    public P6eHttpMulberry(String url, String performerType) {
        super(TYPE, performerType);
        this.url = url;
        this.method = GET_METHDO;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStringBody() {
        return stringBody;
    }

    public void setStringBody(String stringBody) {
        this.stringBody = stringBody;
    }

    public HttpEntity getHttpEntityBody() {
        return httpEntityBody;
    }

    public void setHttpEntityBody(HttpEntity httpEntityBody) {
        this.httpEntityBody = httpEntityBody;
    }

    public Map<String, String> getMapBody() {
        return mapBody;
    }

    public void setMapBody(Map<String, String> mapBody) {
        this.mapBody = mapBody;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void clearHeaders() {
        headers.clear();
    }

    public static class Builder {

        private final P6eHttpMulberry mulberry;

        public static Builder create(String url, String performerType) {
            return new Builder(url, performerType);
        }

        private Builder(String url, String performerType) {
            mulberry = new P6eHttpMulberry(url, performerType);
        }

        public Builder setStringBody(String body) {
            mulberry.setStringBody(body);
            return this;
        }

        public Builder setMapBody(Map<String, String> mapBody) {
            mulberry.setMapBody(mapBody);
            return this;
        }

        public Builder setHttpEntityBody(HttpEntity httpEntityBody) {
            mulberry.setHttpEntityBody(httpEntityBody);
            return this;
        }

        public Builder setGetMethod() {
            mulberry.setMethod(GET_METHDO);
            return this;
        }

        public Builder setPutMethod() {
            mulberry.setMethod(PUT_METHDO);
            return this;
        }

        public Builder setPostMethod() {
            mulberry.setMethod(POST_METHDO);
            return this;
        }

        public Builder setDeleteMethod() {
            mulberry.setMethod(DELETE_METHDO);
            return this;
        }

        public Builder setProxyHost() {
            mulberry.setProxyHost(GET_METHDO);
            return this;
        }

        public Builder setProxyPort() {
            mulberry.setProxyPort(GET_METHDO);
            return this;
        }

        public Builder setMaxRetry(int maxRetry) {
            mulberry.setMaxRetry(maxRetry);
            return this;
        }

        public Builder addHeader(String key, String value) {
            mulberry.addHeader(key, value);
            return this;
        }

        public Builder setAttribute(String key, Object value) {
            mulberry.setAttribute(key, value);
            return this;
        }

        public Builder setAttribute(Map<String, Object> attribute) {
            if (attribute != null) {
                for (String key : attribute.keySet()) {
                    mulberry.setAttribute(key, attribute.get(key));
                }
            }
            return this;
        }

        public P6eHttpMulberry build() {
            return mulberry;
        }

    }

}
