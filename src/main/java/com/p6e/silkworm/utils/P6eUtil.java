package com.p6e.silkworm.utils;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eUtil {
    private static P6eHttpInterface HTTP = new P6eHttpUtil();

    public static P6eHttpInterface http() {
        return HTTP;
    }

    public static void setHttp(P6eHttpInterface HTTP) {
        P6eUtil.HTTP = HTTP;
    }

    public static void setJson(P6eJsonUtil JSON) {
        P6eUtil.JSON = JSON;
    }

    private static P6eJsonUtil JSON = new P6eJsonUtil();

    public static P6eJsonUtil json() {
        return JSON;
    }
}
