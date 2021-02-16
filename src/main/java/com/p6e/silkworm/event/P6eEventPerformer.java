package com.p6e.silkworm.event;

import com.p6e.silkworm.mulberry.P6eMulberry;
import com.p6e.silkworm.utils.P6eThreadUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eEventPerformer {

    /**
     * 线程池任务的 ID
     */
    private static volatile String TASK_ID;

    /**
     * 定时任务线程池对象
     */
    private static ExecutorService EXECUTOR_SERVICE;


    /**
     * 事件处理者的缓存
     */
    private static final Map<String, P6eEventPerformerInterface> PERFORMER_CACHE = new HashMap<>();

    /**
     * 添加一个执行者
     */
    public static void putPerformer(String performerType, P6eEventPerformerInterface performer) {
        PERFORMER_CACHE.put(performerType, performer);
    }

    /**
     * 初始化
     */
    public static void init() {
        PERFORMER_CACHE.clear();
        putPerformer(P6eEventPerformerText.TYPE, new P6eEventPerformerText());
        putPerformer(P6eEventPerformerJson.TYPE, new P6eEventPerformerJson());
        putPerformer(P6eEventPerformerJsoup.TYPE, new P6eEventPerformerJsoup());

        EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new P6eThreadUtil.NamedThreadFactory("P6E-EVENT"));

        TASK_ID = UUID.randomUUID().toString().replaceAll("-", "");
        EXECUTOR_SERVICE.execute(new Task(TASK_ID));
    }

    /**
     * 执行事件的分发和处理
     * @param mulberry 桑叶对象
     */
    public static void execute(P6eMulberry mulberry) {
        if (mulberry != null) {
            final String performerType = mulberry.getPerformerType();
            if (performerType != null && PERFORMER_CACHE.get(performerType) != null) {
                mulberry.addLog("[ EVENT RUN PERFORMER ( " + performerType + " ) ]");
                PERFORMER_CACHE.get(performerType).execute(mulberry);
            } else {
                mulberry.addLog("[ EVENT RUN PERFORMER DEFAULT TEXT ]");
                PERFORMER_CACHE.get(P6eEventPerformerText.TYPE).execute(mulberry);
            }
            // 清空数据
            // ...
        }
    }

    /**
     * 关闭
     */
    public static void close() {
        TASK_ID = "";
        if (EXECUTOR_SERVICE != null) {
            EXECUTOR_SERVICE.shutdown();
        }
    }

    /**
     * 摧毁
     */
    public static void destroy() {
        TASK_ID = "";
        if (EXECUTOR_SERVICE != null) {
            EXECUTOR_SERVICE.shutdownNow();
        }
    }

    /**
     * 任务线程
     */
    private static class Task implements Runnable {

        /**
         * 任务的 ID
         */
        private final String id;

        public Task(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 如果 ID 和目标的 ID 不一样，就退出执行任务
                    if (!id.equals(TASK_ID)) {
                        break;
                    } else {
                        // 获取事件消息
                        P6eMulberry mulberry = P6eEventQueue.poll();
                        while (mulberry != null) {
                            try {
                                mulberry.addLog("[ EVENT RUN ]");
                                // 执行事件任务
                                execute(mulberry);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // 再次获取事件消息
                            mulberry = P6eEventQueue.poll();
                        }
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

