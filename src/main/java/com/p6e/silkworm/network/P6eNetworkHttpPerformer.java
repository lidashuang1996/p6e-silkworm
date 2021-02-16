package com.p6e.silkworm.network;

import com.p6e.silkworm.event.P6eEventQueue;
import com.p6e.silkworm.mulberry.P6eHttpMulberry;
import com.p6e.silkworm.mulberry.P6eMulberry;
import com.p6e.silkworm.utils.P6eHttpInterface;
import com.p6e.silkworm.utils.P6eUtil;
import org.apache.http.HttpEntity;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eNetworkHttpPerformer extends P6eNetworkPerformer {

    /**
     * 注入 HTTP 对象
     */
    private final P6eHttpInterface http = P6eUtil.http();

    /**
     * 执行 HTTP 网络任务
     * @param mulberry 桑叶对象
     */
    public void execute(P6eHttpMulberry mulberry) {
        // HTTP 执行前执行
        this.beforeRun(mulberry);

        // 重试执行
        this.retry(mulberry, () -> request(mulberry));

        // HTTP 执行后执行
        this.afterRun(mulberry);

        // 推送消息
        this.putEventMessage(mulberry);
    }

    /**
     * 推送事件消息
     * @param mulberry 桑叶对象
     */
    public void putEventMessage(P6eHttpMulberry mulberry) {
        mulberry.addLog("[ HTTP NETWORK PUT EVENT MESSAGE ]");
        P6eEventQueue.put(mulberry);
    }

    /**
     * 网络请求的方法
     * @param mulberry 桑叶对象
     */
    public void request(P6eHttpMulberry mulberry) {
        mulberry.addLog("[ HTTP NETWORK REQUEST START ]");
        try {
            final String url = mulberry.getUrl();
            final String method = mulberry.getMethod();

            final String stringBody = mulberry.getStringBody();
            final Map<String, String> mapBody = mulberry.getMapBody();
            final HttpEntity httpEntityBody = mulberry.getHttpEntityBody();

            final Map<String, String> headers = mulberry.getHeaders();

            final String proxyHost = mulberry.getProxyHost();
            final String proxyPort = mulberry.getProxyPort();

            final String httpResult;
            switch (method) {
                case P6eHttpMulberry.PUT_METHDO:
                    if (httpEntityBody != null) {
                        httpResult = http.doPut(url, httpEntityBody, headers, proxyHost, proxyPort);
                    } else if (mapBody != null) {
                        httpResult = http.doPut(url, mapBody, headers, proxyHost, proxyPort);
                    } else {
                        httpResult = http.doPut(url, stringBody, headers, proxyHost, proxyPort);
                    }
                    break;
                case P6eHttpMulberry.POST_METHDO:
                    if (httpEntityBody != null) {
                        httpResult = http.doPost(url, httpEntityBody, headers, proxyHost, proxyPort);
                    } else if (mapBody != null) {
                        httpResult = http.doPost(url, mapBody, headers, proxyHost, proxyPort);
                    } else {
                        httpResult = http.doPost(url, stringBody, headers, proxyHost, proxyPort);
                    }
                    break;
                case P6eHttpMulberry.DELETE_METHDO:
                    httpResult = http.doDelete(url, mapBody, headers, proxyHost, proxyPort);
                    break;
                case P6eHttpMulberry.GET_METHDO:
                default:
                    httpResult = http.doGet(url, mapBody, headers, proxyHost, proxyPort);
                    break;
            }
            // 写入数据
            mulberry.setResultContent(httpResult);
            mulberry.setResultType(P6eMulberry.SUCCESS);
            mulberry.addLog("[ HTTP NETWORK REQUEST END ] ==> SUCCESS");
        } catch (Exception e) {
            mulberry.addLog("[ HTTP NETWORK REQUEST END ] ==> ERROR ( " + e.getMessage() + " )");
            try {
                if (mulberry.errorRun(mulberry)) {
                    retry(mulberry, () -> request(mulberry));
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

}
