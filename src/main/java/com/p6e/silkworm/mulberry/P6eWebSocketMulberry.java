package com.p6e.silkworm.mulberry;

import java.io.Serializable;

/**
 * HTTP 网络队列消息对象
 * @author lidashuang
 * @version 1.0
 */
public class P6eWebSocketMulberry extends P6eMulberry implements Serializable {

    /**
     * 类型名称
     */
    public static final String TYPE = "WEB_SOCKET_TYPE";

    public P6eWebSocketMulberry(String sourceType, String performerType) {
        super(sourceType, performerType);
    }
}
