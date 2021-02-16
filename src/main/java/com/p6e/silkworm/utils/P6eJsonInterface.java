package com.p6e.silkworm.utils;

/**
 * JSON 序列化/反序列化 接口
 * @author lidashuang
 * @version 1.0
 */
public interface P6eJsonInterface {

    /**
     * object -> string  序列化
     * @param o 对象序列化
     * @return 序列化为字符串
     */
    public String toJson(Object o);

    /**
     * string -> object  反序列化
     * @param json JSON 字符串
     * @param tClass 反序列化的 CLASS
     * @param <T> 反序列化 CLASS 类型
     * @return 反序列化后的 CLASS 对象
     */
    public <T> T fromJson(String json, Class<T> tClass);
}
