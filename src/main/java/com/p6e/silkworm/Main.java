package com.p6e.silkworm;

import com.p6e.silkworm.event.*;
import com.p6e.silkworm.mulberry.P6eHttpMulberry;
import com.p6e.silkworm.mulberry.P6eMulberry;
import org.jsoup.nodes.Document;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        // 全局配置
        // ...
        // ...

        // 启动程序
        P6eSilkwormApplication.run();

        // 添加事件处理器
        P6eSilkwormApplication.addEventPerformer("1", new P6eEventPerformerJsoup() {
            @Override
            public void dispose(Document document, P6eMulberry message) {
                System.out.println(document);
            }
        });

        // 添加事件处理器
        P6eSilkwormApplication.addEventPerformer("2", new P6eEventPerformerJson() {
            @Override
            public void dispose(String json, P6eMulberry message) {
                System.out.println();
            }
        });

        // 添加事件处理器
        P6eSilkwormApplication.addEventPerformer("3", new P6eEventPerformerText() {
            @Override
            public void dispose(String text, P6eMulberry message) {
                System.out.println(text);
                // addHttpMulberry(P6eHttpMulberry.Builder.create("http://www.baidu.com", "1").setGetMethod().build());
            }
        });

        // 添加事件处理器
        P6eSilkwormApplication.addEventPerformer("4", new P6eEventPerformerInterface() {
            @Override
            public void execute(P6eMulberry message) {
                //
            }
        });

        // 添加爬虫任务
        P6eHttpMulberry a = P6eHttpMulberry.Builder.create("http://www.baidu.com", "3").setGetMethod().build();
        P6eSilkwormApplication.addHttpMulberry(a);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (String log : a.getLogs()) {
            System.out.println(log);
        }

        P6eSilkwormApplication.graceClose();
    }

}
