package com.p6e.silkworm;

import com.p6e.silkworm.event.P6eEventPerformer;
import com.p6e.silkworm.event.P6eEventPerformerInterface;
import com.p6e.silkworm.network.P6eNetworkQueue;
import com.p6e.silkworm.mulberry.P6eHttpMulberry;
import com.p6e.silkworm.mulberry.P6eWebSocketMulberry;
import com.p6e.silkworm.utils.P6eHttpInterface;
import com.p6e.silkworm.utils.P6eUtil;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eSilkwormApplication {

    private static boolean IS_RUN = false;

    private static boolean isRun() {
        if (!IS_RUN) {
            throw new RuntimeException("以及开始 无法修改！！！！！");
        }
        return true;
    }

    private static boolean isNotRun() {
        if (IS_RUN) {
            throw new RuntimeException("以及开始 无法修改！！！！！");
        }
        return true;
    }

    public synchronized static void run() {
        if (IS_RUN) {
            throw new RuntimeException("不要重复开始！！！！！");
        } else {
            // 读取 System 的参数执行初始化的操作
            //


            // 启动线程池执行任务
            // ....
            // ....
            P6eNetworkQueue.init();
            P6eEventPerformer.init();
            IS_RUN = true;
        }
    }

    public static void setNetwork() {

    }

    public static void setJson() {

    }

    public static void setHttp(P6eHttpInterface http) {
        if (isNotRun()) {
            P6eUtil.setHttp(http);
        }
    }

    public static P6eHttpInterface getHttp() {
        return P6eUtil.http();
    }

    public static void addHttpMulberry(P6eHttpMulberry message) {
        if (isRun()) {
            P6eNetworkQueue.put(message);
        }
    }

    public static void addWebSocketMulberry(P6eWebSocketMulberry message) {
        if (isRun()) {
            P6eNetworkQueue.put(message);
        }
    }

    public static void addEventPerformer(String performerName, P6eEventPerformerInterface performerFunction) {
        P6eEventPerformer.putPerformer(performerName, performerFunction);
    }

}
