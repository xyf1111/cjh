package io.chaoshua.common.yto.req;

import io.chaoshua.common.utils.DateUtils;
import io.chaoshua.common.yto.YtoConfig;
import org.apache.commons.collections.map.HashedMap;

import java.util.Date;
import java.util.Map;

/**
 * 公共请求参数
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-11 10:34
 */
public class CommonRequest {

    public CommonRequest(String appKey, String timestamp, String format) {
        this.appKey = appKey;
        this.timestamp = timestamp;
        this.format = format;
    }

    public CommonRequest() {
        this.appKey = YtoConfig.APP_KEY;
        this.timestamp = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
        this.format = "json";
    }

    /**
     * 分配给应用的appKey
     */
    private String appKey;
    /**
     * 时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，
     * 例如：2016-01-01 12:00:00
     * API服务端允许客户端请求最大时间误差为5分钟。
     */
    private String timestamp;
    /**
     * 响应格式。默认为json格式，非必传
     * 可选值：json。
     */
    private String format;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public static Map<String, String> toMap(CommonRequest request) {
        Map<String, String> map = new HashedMap();
        map.put("appKey", request.getAppKey());
        map.put("timestamp", request.getTimestamp());
        map.put("format", request.getFormat());

        return map;
    }
}
