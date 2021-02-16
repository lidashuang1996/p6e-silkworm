package com.p6e.silkworm.utils;

import org.apache.http.HttpEntity;

import java.io.IOException;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eHttpInterface {

    public String doGet(String url, Map<String, String> params, Map<String, String> headers) throws IOException;
    public String doDelete(String url, Map<String, String> params, Map<String, String> headers) throws IOException;

    public String doPut(String url, String params, Map<String, String> headers) throws IOException;
    public String doPost(String url, String params, Map<String, String> headers) throws IOException;

    public String doPut(String url, Map<String, String> params, Map<String, String> headers) throws IOException;
    public String doPost(String url, Map<String, String> params, Map<String, String> headers) throws IOException;

    public String doPut(String url, HttpEntity params, Map<String, String> headers) throws IOException;
    public String doPost(String url, HttpEntity params, Map<String, String> headers) throws IOException;

}
