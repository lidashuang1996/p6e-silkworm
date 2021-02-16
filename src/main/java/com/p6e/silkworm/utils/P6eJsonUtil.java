package com.p6e.silkworm.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eJsonUtil {
    private static final Gson GSON = (new GsonBuilder()).disableHtmlEscaping().create();

    public P6eJsonUtil() {
    }

    public String toJson(Object o) {
        return o == null ? null : GSON.toJson(o);
    }

    public <T> T fromJson(String json, Class<T> tClass) {
        return GSON.fromJson(json, tClass);
    }

    public <T> T fromJson(String json, Type typeOfT) {
        return GSON.fromJson(json, typeOfT);
    }

    public <T, W> Map<T, W> fromJsonToMap(String json, Class<T> keyClass, Class<W> valueClass) {
        return (Map)GSON.fromJson(json, (new TypeToken<Map<T, W>>() {
        }).getType());
    }
}