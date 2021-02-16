package com.p6e.silkworm.event;

import com.p6e.silkworm.mulberry.P6eMulberry;
import com.p6e.silkworm.utils.P6eJsonInterface;
import com.p6e.silkworm.utils.P6eUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eEventPerformerJson implements P6eEventPerformerInterface {

    /**
     * 类型
     */
    public static final String TYPE = "JSON_TYPE";

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eEventPerformerJson.class);

    /**
     * JSON 对象
     */
    private final P6eJsonInterface json = P6eUtil.json();

    @Override
    public void execute(P6eMulberry mulberry) {
        mulberry.addLog("[ EVENT RUN PERFORMER JSON START ]");
        dispose(json.toJson(mulberry), mulberry);
        mulberry.addLog("[ EVENT RUN PERFORMER JSON END ]");
    }

    public void dispose(String json, P6eMulberry mulberry) {
        LOGGER.info(json);
    }

}
