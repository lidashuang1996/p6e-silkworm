package com.p6e.silkworm.event;

import com.p6e.silkworm.mulberry.P6eMulberry;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 事件队列
 * @author lidashuang
 * @version 1.0
 */
public final class P6eEventQueue {

    /**
     * 最大的队列长度
     */
    private static int MAX_QUEUE_SIZE = 2048;

    /**
     * 网络队列消息
     */
    private static LinkedBlockingQueue<P6eMulberry> QUEUE_MESSAGES = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);

    /**
     * 设置最大的队列长度
     * @param size 队列的长度
     */
    public synchronized static void setMaxQueueSize(int size) {
        MAX_QUEUE_SIZE = size;
        QUEUE_MESSAGES = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);
    }

    /**
     * 获取最大的队列长度
     * @return 队列长度
     */
    public static int getMaxQueueSize() {
        return MAX_QUEUE_SIZE;
    }

    /**
     * 获取当前队列长度
     * @return 长度
     */
    public static int size() {
        return QUEUE_MESSAGES.size();
    }

    /**
     * 从开始位置获取一条消息
     * @return 消息对象
     */
    public static P6eMulberry poll() {
        return QUEUE_MESSAGES.poll();
    }

    /**
     * 添加一条消息
     * @param message 消息对象
     */
    public static void put(P6eMulberry message) {
        try {
            QUEUE_MESSAGES.put(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
