package com.p6e.silkworm.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON 序列化/反序列化 实现
 * @author lidashuang
 * @version 1.0
 */
public final class P6eJsonUtil implements P6eJsonInterface {

    /**
     * 创建 G S O N 对象
     */
    private static final Gson GSON = (new GsonBuilder()).disableHtmlEscaping().create();

    @Override
    public String toJson(Object o) {
        return o == null ? null : GSON.toJson(o);
    }

    @Override
    public <T> T fromJson(String json, Class<T> tClass) {
        return GSON.fromJson(json, tClass);
    }

}