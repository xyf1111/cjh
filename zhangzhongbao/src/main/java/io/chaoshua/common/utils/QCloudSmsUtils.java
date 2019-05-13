package io.chaoshua.common.utils;


import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author dws
 * @date 2018-04-25 16:47
 */
public class QCloudSmsUtils {
    private static final Integer SMS_APP_ID = 1400046354;
    private static final String SMS_APP_KEY = "e80b231f27a57dadd16690eec8e07f82";
    public static final Integer SMS_TEMPLATE_ID = 50989;

    /**
     * 发送单条短信
     *
     * @param phoneNumber 手机号码
     * @param msg         短信消息，utf8 编码，需要匹配审核通过的模板内容
     * @return 短信发送结果
     */
    public static SmsSingleSenderResult sendSmsSingle(String phoneNumber, String msg) {
        return sendSmsSingle(0, "86", phoneNumber, msg, "", "");
    }

    /**
     * 发送单条短信
     *
     * @param type        短信类型，Enum{0: 普通短信, 1: 营销短信}
     * @param nationCode  国家码,86
     * @param phoneNumber 手机号码
     * @param msg         短信消息，utf8 编码，需要匹配审核通过的模板内容
     * @param extend      短信码号扩展号，格式为纯数字串，其他格式无效。默认没有开通
     * @param ext         用户的 session 内容，腾讯 server 回包中会原样返回，可选字段，不需要就填空
     * @return 短信发送结果
     */
    public static SmsSingleSenderResult sendSmsSingle(int type, String nationCode, String phoneNumber,
                                                      String msg, String extend, String ext) {
        SmsSingleSenderResult result = null;
        try {
            SmsSingleSender singleSender = new SmsSingleSender(SMS_APP_ID, SMS_APP_KEY);
            result = singleSender.send(type, nationCode, phoneNumber, msg, extend, ext);
        } catch (HTTPException | JSONException | IOException e) {
            // HTTP响应码错误/json解析错误/网络IO错误
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 默认模板发送单条短信
     *
     * @param phoneNumber 手机号码
     * @param params      模板参数，若模板没有参数，请提供为空列表
     * @return
     */
    public static SmsSingleSenderResult sendSmsSingleByTemplate(String phoneNumber, ArrayList<String> params) {
        return sendSmsSingleByTemplate("86",phoneNumber,SMS_TEMPLATE_ID,params,"","","");
    }

    /**
     * 根据模板发送单条短信
     *
     * @param phoneNumber 手机号码
     * @param templateId  模板ID
     * @param params      模板参数，若模板没有参数，请提供为空列表
     * @return
     */
    public static SmsSingleSenderResult sendSmsSingleByTemplate(String phoneNumber, int templateId, ArrayList<String> params) {
        return sendSmsSingleByTemplate("86",phoneNumber,templateId,params,"","","");
    }

    /**
     * 根据模板发送单条短信
     *
     * @param nationCode  国家码,86
     * @param phoneNumber 手机号码
     * @param templateId  模板ID
     * @param params      模板参数，若模板没有参数，请提供为空列表
     * @param sign        短信签名，如果使用默认签名，该字段可缺省
     * @param extend      短信码号扩展号，格式为纯数字串，其他格式无效。默认没有开通
     * @param ext         用户的 session 内容，腾讯 server 回包中会原样返回，可选字段，不需要就填空
     * @return
     */
    public static SmsSingleSenderResult sendSmsSingleByTemplate(String nationCode, String phoneNumber, int templateId,
                                                                ArrayList<String> params, String sign, String extend, String ext) {
        SmsSingleSenderResult result = null;
        try {
            SmsSingleSender singleSender = new SmsSingleSender(SMS_APP_ID, SMS_APP_KEY);
            result = singleSender.sendWithParam(nationCode, phoneNumber, templateId, params, sign, extend, ext);
        } catch (HTTPException | JSONException | IOException e) {
            // HTTP响应码错误/json解析错误/网络IO错误
            e.printStackTrace();
        }
        return result;
    }
}
