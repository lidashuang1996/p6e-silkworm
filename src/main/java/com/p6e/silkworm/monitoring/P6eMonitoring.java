package com.p6e.silkworm.monitoring;

import com.p6e.silkworm.event.P6eEventQueue;
import com.p6e.silkworm.network.P6eNetworkQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eMonitoring {

    public int getNetworkThreadActiveCount() {
        final ThreadPoolExecutor threadPool = P6eNetworkQueue.getThreadPool();
        if (threadPool != null) {
            return threadPool.getActiveCount();
        } else {
            return 0;
        }
    }

    public long getNetworkThreadTaskCount() {
        final ThreadPoolExecutor threadPool = P6eNetworkQueue.getThreadPool();
        if (threadPool != null) {
            return threadPool.getTaskCount();
        } else {
            return 0;
        }
    }

    public long getNetworkThreadCorePoolSize() {
        final ThreadPoolExecutor threadPool = P6eNetworkQueue.getThreadPool();
        if (threadPool != null) {
            return threadPool.getCorePoolSize();
        } else {
            return 0;
        }
    }


    public long getNetworkThreadPoolSize() {
        final ThreadPoolExecutor threadPool = P6eNetworkQueue.getThreadPool();
        if (threadPool != null) {
            return threadPool.getPoolSize();
        } else {
            return 0;
        }
    }

    public int getEventQueueSize() {
        return P6eEventQueue.size();
    }
}
