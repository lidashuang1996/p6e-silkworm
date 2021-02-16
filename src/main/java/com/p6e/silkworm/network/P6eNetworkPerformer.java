package com.p6e.silkworm.network;

import com.p6e.silkworm.mulberry.P6eMulberry;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eNetworkPerformer {

    public interface RetryCallback {
        /**
         * 重试执行回调
         */
        public void execute();
    }

    /**
     *
     * @param mulberry
     */
    public void beforeRun(P6eMulberry mulberry) {
        if (mulberry != null) {
            try {
                mulberry.beforeRun(mulberry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param mulberry
     */
    public void afterRun(P6eMulberry mulberry) {
        if (mulberry != null) {
            try {
                mulberry.beforeRun(mulberry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void retry(P6eMulberry mulberry, RetryCallback callback) {
        if (mulberry != null && callback != null) {
            final int retry = mulberry.getRetry() + 1;
            if (retry <= mulberry.getMaxRetry()) {
                mulberry.setRetry(retry);
                callback.execute();
            }
        }
    }

}
