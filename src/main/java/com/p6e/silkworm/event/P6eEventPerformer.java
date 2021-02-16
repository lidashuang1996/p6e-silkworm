package com.p6e.silkworm.event;

import com.p6e.silkworm.mulberry.P6eMulberry;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eEventPerformer {

    /**
     * 事件处理者的缓存
     */
    private static final Map<String, P6eEventPerformerInterface> PERFORMER_CACHE = new HashMap<>();

    static {
        // 启动定时任务
        new Timer().schedule(new Task(), 0, 1000);
    }

    /**
     * 添加一个执行者
     */
    public static void putPerformer(String performerType, P6eEventPerformerInterface performer) {
        PERFORMER_CACHE.put(performerType, performer);
    }

    public static void init() {
        putPerformer(P6eEventPerformerText.TYPE, new P6eEventPerformerText());
        putPerformer(P6eEventPerformerJson.TYPE, new P6eEventPerformerJson());
        putPerformer(P6eEventPerformerJsoup.TYPE, new P6eEventPerformerJsoup());
    }

    public static void execute(P6eMulberry mulberry) {
        if (mulberry != null) {
            final String performerType = mulberry.getPerformerType();
            if (performerType != null && PERFORMER_CACHE.get(performerType) != null) {
                PERFORMER_CACHE.get(performerType).execute(mulberry);
            } else {
                PERFORMER_CACHE.get(P6eEventPerformerText.TYPE).execute(mulberry);
            }
            // 清空数据
            // ...
        }
    }

    /**
     * 事件任务
     */
    private static class Task extends TimerTask {
        @Override
        public void run() {
            try {
                // 获取事件消息
                P6eMulberry mulberry = P6eEventQueue.poll();
                do {
                    try {
                        // 执行事件任务
                        execute(mulberry);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 再次获取事件消息
                    mulberry = P6eEventQueue.poll();
                } while (mulberry != null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

