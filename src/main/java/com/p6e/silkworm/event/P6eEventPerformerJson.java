package com.p6e.silkworm.event;

import com.p6e.silkworm.mulberry.P6eMulberry;
import com.p6e.silkworm.utils.P6eJsonUtil;
import com.p6e.silkworm.utils.P6eUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eEventPerformerJson implements P6eEventPerformerInterface {

    public static final String TYPE = "JSON_TYPE";
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eEventPerformerJson.class);

    private final P6eJsonUtil json = P6eUtil.json();

    @Override
    public void execute(P6eMulberry message) {
        dispose(json.toJson(message), message);
    }

    public void dispose(String json, P6eMulberry message) {
        LOGGER.info(json);
    }

}
