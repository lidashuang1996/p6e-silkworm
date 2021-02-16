package com.p6e.silkworm.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eThreadUtil {

    /**
     * 设置线程的名称
     */
    public static class NamedThreadFactory implements ThreadFactory {

        private final String name;

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public NamedThreadFactory(String name) {
            this.name = name;
            final SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            final Thread t = new Thread(group, r, name + "-[" + threadNumber.getAndIncrement() + "]", 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 完全关闭后的回调
     */
    public interface ThreadPoolShutdownCallback {
        /**
         * 回调方法
         */
        public void execute();
    }

    public static void threadPoolShutdown(ThreadPoolExecutor threadPool, ThreadPoolShutdownCallback callback) {
        if (threadPool != null) {
            threadPool.shutdown();
            while (threadPool.getActiveCount() > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (callback != null) {
                callback.execute();
            }
        }
    }
}
