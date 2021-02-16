package com.p6e.silkworm.event;

import com.p6e.silkworm.mulberry.P6eMulberry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eEventPerformerText implements P6eEventPerformerInterface {

    public static final String TYPE = "TEXT_TYPE";
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eEventPerformerText.class);

    @Override
    public void execute(P6eMulberry mulberry) {
        if (mulberry != null && P6eMulberry.SUCCESS.equals(mulberry.getResultType())) {
            final String result = mulberry.getResultContent();
            this.dispose(result, mulberry);
        }
    }

    public void dispose(String text, P6eMulberry message) {
        LOGGER.info(text);
    }
}
