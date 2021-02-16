package com.p6e.silkworm;

import com.p6e.silkworm.event.P6eEventPerformer;
import com.p6e.silkworm.event.P6eEventPerformerInterface;
import com.p6e.silkworm.network.P6eNetworkHttpPerformer;
import com.p6e.silkworm.network.P6eNetworkQueue;
import com.p6e.silkworm.mulberry.P6eHttpMulberry;
import com.p6e.silkworm.mulberry.P6eWebSocketMulberry;
import com.p6e.silkworm.network.P6eNetworkWebSocketPerformer;
import com.p6e.silkworm.utils.P6eHttpInterface;
import com.p6e.silkworm.utils.P6eJsonInterface;
import com.p6e.silkworm.utils.P6eUtil;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * P6eSilkwormApplication 入口, 轻松使用！
 *
 * @author lidashuang
 * @version 1.0
 */
public final class P6eSilkwormApplication {

    /**
     * 是否启动
     */
    private static boolean IS_RUN = false;

    /**
     * 是否启动
     * @return 是否通过
     */
    private static boolean isRun() {
        if (!IS_RUN) {
            throw new RuntimeException("Program not started, try again after the program starts.");
        }
        return true;
    }

    /**
     * 是否没有启动
     * @return 是否通过
     */
    private static boolean isNotRun() {
        if (IS_RUN) {
            throw new RuntimeException("Program started, Try again after the program is closed.");
        }
        return true;
    }

    /**
     * 启动程序
     */
    public synchronized static void run() {
        if (IS_RUN) {
            throw new RuntimeException("Please do not repeat the startup procedure !");
        } else {
            P6eNetworkQueue.init();
            P6eEventPerformer.init();
            IS_RUN = true;
        }
    }

    /**
     * 设置/替换 HTTP 对象
     * @param http HTTP 对象
     */
    public static void setHttp(P6eHttpInterface http) {
        if (isNotRun()) {
            P6eUtil.setHttp(http);
        }
    }

    /**
     * 设置/替换 JSON 对象
     * @param json JSON 对象
     */
    public static void setJson(P6eJsonInterface json) {
        if (isNotRun()) {
            P6eUtil.setJson(json);
        }
    }

    /**
     * 获取 HTTP 对象
     * @return HTTP 对象
     */
    public static P6eHttpInterface getHttp() {
        return P6eUtil.http();
    }

    /**
     * 获取 JSON 对象
     * @return JSON 对象
     */
    public static P6eJsonInterface getJson() {
        return P6eUtil.json();
    }

    /**
     * 设置网络的线程池大小
     * @param poolSize 线程池大小
     */
    public static void setNetworkPool(int poolSize) {
        if (isNotRun()) {
            P6eNetworkQueue.setNetworkPool(poolSize);
        }
    }

    /**
     * 设置网络的线程池
     * @param threadPool 线程池对象
     */
    public static void setNetworkPool(ThreadPoolExecutor threadPool) {
        if (isNotRun()) {
            P6eNetworkQueue.setNetworkPool(threadPool);
        }
    }

    /**
     * 设置网络的间隔时间
     * @param interval 间隔的时间
     */
    public static void setNetworkInterval(long interval) {
        if (isNotRun()) {
            P6eNetworkQueue.setInterval(interval);
        }
    }


    /**
     * 设置网络的 HTTP 处理者对象
     * @param performer HTTP 处理者对象
     */
    public static void setNetworkHttpPerformer(P6eNetworkHttpPerformer performer) {
        if (isNotRun()) {
            P6eNetworkQueue.setNetworkHttpPerformer(performer);
        }
    }

    /**
     * 设置网络的 WEB SOCKET 处理者对象
     * @param performer WEB SOCKET 处理者对象
     */
    public static void setNetworkWebsocketPerformer(P6eNetworkWebSocketPerformer performer) {
        if (isNotRun()) {
            P6eNetworkQueue.setNetworkWebSocketPerformer(performer);
        }
    }

    /**
     * 关闭服务
     */
    private synchronized static boolean close() {
        return isRun();
    }

    /**
     * 摧毁服务
     */
    public synchronized static void destroy() {
        if (close()) {
            P6eNetworkQueue.destroy();
            P6eEventPerformer.destroy();
        }
    }

    /**
     * 关闭服务
     */
    public synchronized static void graceClose() {
        if (close()) {
            P6eNetworkQueue.close(P6eEventPerformer::close);
        }
    }

    /**
     * 添加 HTTP
     * @param mulberry 桑叶对象
     */
    public static void addHttpMulberry(P6eHttpMulberry mulberry) {
        if (isRun()) {
            P6eNetworkQueue.put(mulberry);
        }
    }

    /**
     * 添加 WEB SOCKET
     * @param mulberry 桑叶对象
     */
    public static void addWebSocketMulberry(P6eWebSocketMulberry mulberry) {
        if (isRun()) {
            P6eNetworkQueue.put(mulberry);
        }
    }

    /**
     * 添加事件执行者
     * @param performerName 执行者名称
     * @param performerFunction 执行者函数
     */
    public static void addEventPerformer(String performerName, P6eEventPerformerInterface performerFunction) {
        P6eEventPerformer.putPerformer(performerName, performerFunction);
    }

}
