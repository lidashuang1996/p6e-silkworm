package com.p6e.silkworm.event;

import com.p6e.silkworm.mulberry.P6eMulberry;
import com.p6e.silkworm.network.P6eNetworkQueue;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eEventPerformerInterface {

    /**
     * 执行方法
     * @param mulberry 桑叶对象
     */
    public void execute(P6eMulberry mulberry);

    /**
     * 创建/添加 HTTP 桑叶对象
     * @param mulberry 桑叶对象
     */
    public default void addHttpMulberry(P6eMulberry mulberry) {
        P6eNetworkQueue.put(mulberry);
    }

    /**
     * 创建/添加 WEB SOCKET 桑叶对象
     * @param mulberry 桑叶对象
     */
    public default void addWebSocketMulberry(P6eMulberry mulberry) {
        P6eNetworkQueue.put(mulberry);
    }
}
