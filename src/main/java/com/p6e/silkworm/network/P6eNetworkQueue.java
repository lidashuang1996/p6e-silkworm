package com.p6e.silkworm.network;

import com.p6e.silkworm.mulberry.P6eHttpMulberry;
import com.p6e.silkworm.mulberry.P6eMulberry;
import com.p6e.silkworm.mulberry.P6eWebSocketMulberry;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网络队列
 * @author lidashuang
 * @version 1.0
 */
public final class P6eNetworkQueue {

    /**
     * 默认创建的线程池
     */
    private static ExecutorService THREAD_POOL;

    /**
     * HTTP 执行者
     */
    private static P6eNetworkHttpPerformer NETWORK_HTTP_PERFORMER;

    /**
     * WEB SOCKET 执行者
     */
    private static P6eNetworkWebSocketPerformer NETWORK_WEB_SOCKET_PERFORMER;

    /**
     * 初始化的操作
     */
    public static void init() {
        init(new ThreadPoolExecutor(10, 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new NamedThreadFactory()));
    }

    /**
     * 初始化的操作
     * @param tp 线程池
     */
    public static void init(ExecutorService tp) {
        THREAD_POOL = tp;

        if (NETWORK_HTTP_PERFORMER == null) {
            NETWORK_HTTP_PERFORMER = new P6eNetworkHttpPerformer();
        }

        if (NETWORK_WEB_SOCKET_PERFORMER == null) {
            NETWORK_WEB_SOCKET_PERFORMER = new P6eNetworkWebSocketPerformer();
        }
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

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 设置线程的名称
     */
    private static class NamedThreadFactory implements ThreadFactory{

        private static final String NAME_PREFIX = "P6E-NETWORK-";

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public NamedThreadFactory() {
            final SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            final Thread t = new Thread(group, r, NAME_PREFIX + "[" + threadNumber.getAndIncrement() + "]", 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }
}
