package com.p6e.silkworm.event;

import com.p6e.silkworm.mulberry.P6eMulberry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eEventPerformerJsoup implements P6eEventPerformerInterface {

    /**
     * 类型
     */
    public static final String TYPE = "HTML_TYPE";

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eEventPerformerJsoup.class);

    @Override
    public void execute(P6eMulberry mulberry) {
        if (mulberry != null && P6eMulberry.SUCCESS.equals(mulberry.getResultType())) {
            final String result = mulberry.getResultContent();
            mulberry.addLog("[ EVENT RUN PERFORMER JSOUP START ]");
            this.dispose(Jsoup.parse(result), mulberry);
            mulberry.addLog("[ EVENT RUN PERFORMER JSOUP END ]");
        }
    }

    public void dispose(Document document, P6eMulberry message) {
        LOGGER.info(document.toString());
    }
}
