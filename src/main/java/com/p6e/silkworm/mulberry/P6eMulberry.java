package com.p6e.silkworm.mulberry;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 网络队列消息对象
 * @author lidashuang
 * @version 1.0
 */
public class P6eMulberry implements Serializable {

    public final static String ERROR = "ERROR";
    public final static String SUCCESS = "SUCCESS";
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 生命周期的回调函数
     */
    public interface Callback {

        /**
         * 生命周期的执行
         * @param mulberry 参数对象
         * @throws Exception 执行中可能出现的异常
         */
        public void execute(P6eMulberry mulberry) throws Exception;
    }

    /**
     * 生命周期的回调函数
     */
    public interface ErrorCallback {

        /**
         * 生命周期的执行
         * @param mulberry 参数对象
         * @return 是否需要重新尝试
         * @throws Exception 执行中可能出现的异常
         */
        public boolean execute(P6eMulberry mulberry) throws Exception;
    }

    /**
     * ID
     */
    private final String id;

    /**
     * 消息来源类型
     */
    private final String sourceType;

    /**
     * 事件处理的类型
     */
    private final String performerType;

    /**
     * 重试次数
     */
    private int retry = 0;
    private int maxRetry = 3;

    /**
     * 结果类型
     */
    private String resultType;
    private String resultContent;

    /**
     * 日志
     */
    private final List<String> logs = new ArrayList<>();

    /**
     * 属性
     */
    private final Map<String, Object> attribute = new HashMap<>();

    /**
     * 生命周期的参数
     */
    private Callback beforeFunction;
    private Callback afterFunction;
    private ErrorCallback errorFunction;

    public void beforeRun(P6eMulberry mulberry) throws Exception {
        if (beforeFunction != null) {
            beforeFunction.execute(mulberry);
        }
    }

    public void afterRun(P6eMulberry mulberry) throws Exception {
        if (afterFunction != null) {
            afterFunction.execute(mulberry);
        }
    }

    public boolean errorRun(P6eMulberry mulberry) throws Exception {
        if (afterFunction != null) {
            return errorFunction.execute(mulberry);
        }
        return true;
    }


    public P6eMulberry(String sourceType, String performerType) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.sourceType = sourceType;
        this.performerType = performerType;
        this.addLog("[ CREATE ] ==> SOURCE TYPE: " + sourceType + ", PERFORMER TYPE: " + performerType);
    }

    public String getId() {
        return id;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getPerformerType() {
        return performerType;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }

    public void setBeforeFunction(Callback beforeFunction) {
        this.beforeFunction = beforeFunction;
    }

    public void setAfterFunction(Callback afterFunction) {
        this.afterFunction = afterFunction;
    }

    public void setErrorFunction(ErrorCallback errorFunction) {
        this.errorFunction = errorFunction;
    }

    public List<String> getLogs() {
        return logs;
    }

    public Map<String, Object> getAttribute() {
        return attribute;
    }

    public void addLog(String log) {
        final String tName = Thread.currentThread().getName();
        final String dateTime = DATE_TIME_FORMATTER.format(LocalDateTime.now());
        logs.add(id + "  " + tName + "/" + dateTime + "  " + log);
    }

    public void setAttribute(String key, Object value) {
        attribute.put(key, value);
    }

}
