package com.p6e.silkworm.utils;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eUtil {

    /**
     * 默认 HTTP 实现
     */
    private static P6eHttpInterface HTTP = new P6eHttpUtil();

    /**
     * 默认 JSON 实现
     */
    private static P6eJsonInterface JSON = new P6eJsonUtil();

    /**
     * 返回 HTTP 实现
     * @return HTTP 对象
     */
    public static P6eHttpInterface http() {
        return HTTP;
    }

    /**
     * 返回 JSON 实现
     * @return JSON 对象
     */
    public static P6eJsonInterface json() {
        return JSON;
    }

    /**
     * 设置 HTTP 对象
     * @param http HTTP 对象
     */
    public static void setHttp(P6eHttpInterface http) {
        P6eUtil.HTTP = http;
    }

    /**
     * 设置 JSON 对象
     * @param json JSON 对象
     */
    public static void setJson(P6eJsonInterface json) {
        P6eUtil.JSON = json;
    }
}
