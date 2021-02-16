package com.p6e.silkworm.utils;

import org.apache.http.HttpEntity;
import java.io.IOException;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eHttpInterface {

    /**
     * 设置全局的头部参数
     * @param headers 头部数据
     */
    public void setGlobalHeaders(Map<String, String> headers);

    /**
     * 设置全局的代理
     * @param proxyHost 代理的 HOST
     * @param proxyPort 代理的 PORT
     */
    public void setGlobalProxy(String proxyHost, String proxyPort);


    /**
     * GET 请求
     * @param url 请求的地址
     * @param params 请求的参数
     * @param headers 请求的头部数据
     * @param proxyHost 代理的 HOST
     * @param proxyPort 代理的 PORT
     * @return 请求的结果
     * @throws IOException 请求过程中的 IO 异常
     */
    public String doGet(String url, Map<String, String> params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException;

    /**
     * DELETE 请求
     * @param url 请求的地址
     * @param params 请求的参数
     * @param headers 请求的头部数据
     * @param proxyHost 代理的 HOST
     * @param proxyPort 代理的 PORT
     * @return 请求的结果
     * @throws IOException 请求过程中的 IO 异常
     */
    public String doDelete(String url, Map<String, String> params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException;

    /**
     * PUT 请求
     * @param url 请求的地址
     * @param params 请求的参数
     * @param headers 请求的头部数据
     * @param proxyHost 代理的 HOST
     * @param proxyPort 代理的 PORT
     * @return 请求的结果
     * @throws IOException 请求过程中的 IO 异常
     */
    public String doPut(String url, String params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException;

    /**
     * POST 请求
     * @param url 请求的地址
     * @param params 请求的参数
     * @param headers 请求的头部数据
     * @param proxyHost 代理的 HOST
     * @param proxyPort 代理的 PORT
     * @return 请求的结果
     * @throws IOException 请求过程中的 IO 异常
     */
    public String doPost(String url, String params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException;

    /**
     * PUT 请求
     * @param url 请求的地址
     * @param params 请求的参数
     * @param headers 请求的头部数据
     * @param proxyHost 代理的 HOST
     * @param proxyPort 代理的 PORT
     * @return 请求的结果
     * @throws IOException 请求过程中的 IO 异常
     */
    public String doPut(String url, Map<String, String> params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException;

    /**
     * POST 请求
     * @param url 请求的地址
     * @param params 请求的参数
     * @param headers 请求的头部数据
     * @param proxyHost 代理的 HOST
     * @param proxyPort 代理的 PORT
     * @return 请求的结果
     * @throws IOException 请求过程中的 IO 异常
     */
    public String doPost(String url, Map<String, String> params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException;

    /**
     * PUT 请求
     * @param url 请求的地址
     * @param params 请求的参数
     * @param headers 请求的头部数据
     * @param proxyHost 代理的 HOST
     * @param proxyPort 代理的 PORT
     * @return 请求的结果
     * @throws IOException 请求过程中的 IO 异常
     */
    public String doPut(String url, HttpEntity params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException;

    /**
     * POST 请求
     * @param url 请求的地址
     * @param params 请求的参数
     * @param headers 请求的头部数据
     * @param proxyHost 代理的 HOST
     * @param proxyPort 代理的 PORT
     * @return 请求的结果
     * @throws IOException 请求过程中的 IO 异常
     */
    public String doPost(String url, HttpEntity params, Map<String, String> headers, String proxyHost, String proxyPort) throws IOException;
}
