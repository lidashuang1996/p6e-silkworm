package com.p6e.silkworm.network;

import com.p6e.silkworm.mulberry.P6eHttpMulberry;
import com.p6e.silkworm.mulberry.P6eMulberry;
import com.p6e.silkworm.mulberry.P6eWebSocketMulberry;
import com.p6e.silkworm.utils.P6eThreadUtil;

import java.util.concurrent.*;

/**
 * 网络队列
 * @author lidashuang
 * @version 1.0
 */
public final class P6eNetworkQueue {

    /**
     * 默认创建的线程池
     */
    private static ThreadPoolExecutor THREAD_POOL;

    /**
     * HTTP 执行者
     */
    private static P6eNetworkHttpPerformer NETWORK_HTTP_PERFORMER;

    /**
     * WEB SOCKET 执行者
     */
    private static P6eNetworkWebSocketPerformer NETWORK_WEB_SOCKET_PERFORMER;

    /**
     * 任务之间的间隔
     */
    private static long INTERVAL = 1000;

    /**
     * 初始化的操作
     */
    public static void init() {
        init(new ThreadPoolExecutor(10, 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new P6eThreadUtil.NamedThreadFactory("P6E-NETWORK")));
    }

    /**
     * 初始化的操作
     * @param executor 线程池
     */
    public static void init(ThreadPoolExecutor executor) {
        if (THREAD_POOL == null) {
            THREAD_POOL = executor;
        } else {
            executor.shutdown();
        }
        if (NETWORK_HTTP_PERFORMER == null) {
            NETWORK_HTTP_PERFORMER = new P6eNetworkHttpPerformer();
        }

        if (NETWORK_WEB_SOCKET_PERFORMER == null) {
            NETWORK_WEB_SOCKET_PERFORMER = new P6eNetworkWebSocketPerformer();
        }
    }

    /**
     * 设置网路线程池的大小
     * @param poolSize 线程池的大小
     */
    public static void setNetworkPool(int poolSize) {
        if (THREAD_POOL != null) {
            THREAD_POOL.shutdown();
        }
        THREAD_POOL = new ThreadPoolExecutor(poolSize, poolSize, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new P6eThreadUtil.NamedThreadFactory("P6E-NETWORK"));
    }

    /**
     * 设置网路线程池
     * @param executorService 线程池
     */
    public static void setNetworkPool(ThreadPoolExecutor executorService) {
        if (THREAD_POOL != null) {
            THREAD_POOL.shutdown();
        }
        THREAD_POOL = executorService;
    }

    public static void setInterval(long interval) {
        INTERVAL = interval;
    }

    /**
     * HTTP 执行者替换的方法
     * @param networkHttpPerformer HTTP 执行者
     */
    public static void setNetworkHttpPerformer(P6eNetworkHttpPerformer networkHttpPerformer) {
        NETWORK_HTTP_PERFORMER = networkHttpPerformer;
    }

    /**
     * WEBSOCKET 执行者替换的方法
     * @param networkWebSocketPerformer WEB SOCKET 执行者
     */
    public static void setNetworkWebSocketPerformer(P6eNetworkWebSocketPerformer networkWebSocketPerformer) {
        NETWORK_WEB_SOCKET_PERFORMER = networkWebSocketPerformer;
    }

    /**
     * 添加一片桑叶
     * @param mulberry 桑叶对象
     */
    public static void put(P6eMulberry mulberry) {
        if (THREAD_POOL != null) {
            THREAD_POOL.execute(() -> {
                try {
                    if (mulberry != null && mulberry.getSourceType() != null) {

                        // HTTP
                        if (mulberry instanceof P6eHttpMulberry
                                && P6eHttpMulberry.TYPE.equals(mulberry.getSourceType())) {
                            if (NETWORK_HTTP_PERFORMER != null) {
                                mulberry.addLog("[ DISTRIBUTE HTTP NETWORK ]");
                                NETWORK_HTTP_PERFORMER.execute((P6eHttpMulberry) mulberry);
                            }
                        }

                        // WEB SOCKET
                        else if (mulberry instanceof P6eWebSocketMulberry
                                && P6eWebSocketMulberry.TYPE.equals(mulberry.getSourceType())) {
                            if (NETWORK_WEB_SOCKET_PERFORMER != null) {
                                NETWORK_WEB_SOCKET_PERFORMER.execute((P6eWebSocketMulberry) mulberry);
                            }
                        }

                        // 间隔的时间
                        Thread.sleep(INTERVAL);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static ThreadPoolExecutor getThreadPool() {
        return THREAD_POOL;
    }

    /**
     * 摧毁
     */
    public static void destroy() {
        THREAD_POOL.shutdownNow();
    }

    /**
     * 关闭
     * @param callback 关闭之后的回调函数
     */
    public static void close(P6eThreadUtil.ThreadPoolShutdownCallback callback) {
        P6eThreadUtil.threadPoolShutdown(THREAD_POOL, callback);
    }
}
