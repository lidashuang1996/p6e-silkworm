package com.p6e.silkworm.event;

import com.p6e.silkworm.mulberry.P6eMulberry;
import com.p6e.silkworm.network.P6eNetworkQueue;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eEventPerformerInterface {

    /**
     * 执行
     * @param message
     */
    public void execute(P6eMulberry message);

    public default void addHttpMulberry(P6eMulberry mulberry) {
        P6eNetworkQueue.put(mulberry);
    }

    public default void addWebSocketMulberry(P6eMulberry mulberry) {
        P6eNetworkQueue.put(mulberry);
    }
}
