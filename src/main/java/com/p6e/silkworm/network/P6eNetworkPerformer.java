package com.p6e.silkworm.network;

import com.p6e.silkworm.mulberry.P6eMulberry;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eNetworkPerformer {

    /**
     * 重试的回调函数
     */
    public interface RetryCallback {
        /**
         * 重试执行回调
         */
        public void execute();
    }

    /**
     * 在网络之前执行
     * @param mulberry 桑叶对象
     */
    public void beforeRun(P6eMulberry mulberry) {
        if (mulberry != null) {
            try {
                mulberry.addLog("[ HTTP NETWORK BEFORE RUN ]");
                mulberry.beforeRun(mulberry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在网络之后执行
     * @param mulberry 桑叶对象
     */
    public void afterRun(P6eMulberry mulberry) {
        if (mulberry != null) {
            try {
                mulberry.addLog("[ HTTP NETWORK AFTER RUN ]");
                mulberry.beforeRun(mulberry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重试的方法
     * @param mulberry 桑叶对象
     * @param callback 回调函数
     */
    public void retry(P6eMulberry mulberry, RetryCallback callback) {
        if (mulberry != null && callback != null) {
            final int retry = mulberry.getRetry() + 1;
            if (retry <= mulberry.getMaxRetry()) {
                if (retry > 1) {
                    mulberry.addLog("[ HTTP NETWORK RETRY ] ==> [ " + (retry - 1) + " ]");
                }
                mulberry.setRetry(retry);
                callback.execute();
            }
        }
    }

}
