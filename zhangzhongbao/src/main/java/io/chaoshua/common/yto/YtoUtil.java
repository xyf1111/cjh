package io.chaoshua.common.yto;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.yto.req.CommonRequest;
import io.chaoshua.common.yto.req.OrderRequest;
import io.chaoshua.common.yto.req.OrderSubmitRequest;
import io.chaoshua.common.yto.resp.BalanceGetResponse;
import io.chaoshua.common.yto.resp.OrderSubmitResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 运单号实时获取接口
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-08 17:01
 */
public class YtoUtil {

    private static Logger logger = LoggerFactory.getLogger(YtoUtil.class);

    /**
     * 查询余额接口
     *
     * @return
     */
    public static BalanceGetResponse balanceGet() {
        BalanceGetResponse response;
        try {
            //获取参数
            CommonRequest request = new CommonRequest();
            Map<String, String> params = CommonRequest.toMap(request);
            String result = sendPost(YtoConfig.API_URL + "/BalanceGet", params);
            response = JSON.parseObject(result, BalanceGetResponse.class);
        } catch (Exception e) {
            throw new RRException("调用查询余额接口报错");
        }
        return response;
    }

    /**
     * 拉取电子面单单号
     * 相同订单号多次拉取不会重复扣费。
     *
     * @param request 请求参数
     * @return 快递单号信息
     */
    public static OrderSubmitResponse orderSubmit(OrderSubmitRequest request) {
        OrderSubmitResponse response = null;
        try {
            Map<String, String> params = OrderSubmitRequest.toMap(request);
            String result = sendPost(YtoConfig.API_URL + "/OrderSubmit", params);
            logger.warn("params=" + params + ",result=" + result);
            response = JSON.parseObject(result, OrderSubmitResponse.class);
        } catch (Exception e) {
            throw new RRException("调用拉取电子面单单号接口报错");
        }
        return response;
    }

    /**
     * 发送请求加签 （post）
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     * @throws Exception
     */
    private static String sendPost(String url, Map<String, String> params) throws Exception {
        // 加签
        String sign = sign(params, YtoConfig.APP_SECRET);
        return sendPost(url, joinParams(params, sign));
    }

    /**
     * 发送请求 （post）
     *
     * @param url  请求地址
     * @param body 请求参数
     * @return
     * @throws Exception
     */
    private static String sendPost(String url, String body) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        int statusCode = 200;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(body);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            statusCode = ((HttpURLConnection) conn).getResponseCode();
            if (statusCode != 200) {
                in = new BufferedReader(new InputStreamReader(((HttpURLConnection) conn).getErrorStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (statusCode != 200) {
            throw new IOException("\nHttp StatusCode: " + statusCode + "\nErrorMessage: " + result);
        }
        return result;
    }

    /**
     * 请求签名
     *
     * @param params 所有请求参数
     * @param secret 签名密钥
     * @return
     */
    private static String sign(Map<String, String> params, String secret) throws Exception {
        // 第一步：根据字母顺序排序，不区分大小写
        params = MapUtil.sort(params, Comparator.comparing(String::toUpperCase));

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder(secret);
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                query.append(key).append(value);
            }
        }
        query.append(secret);

        // 第三步：使用MD5加密 把二进制转化为大写的十六进制
        return SecureUtil.md5(query.toString());
    }

    /**
     * 请求拼接参数
     *
     * @param params 参数
     * @param sign   签名
     * @return
     */
    private static String joinParams(Map<String, String> params, String sign) {
        StringBuilder body = new StringBuilder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                body.append(key).append("=").append(value).append("&");
            }
        }
        body.append("sign=").append(sign);
        return body.toString();
    }

    public static void main(String[] args) {
        BalanceGetResponse response1 = YtoUtil.balanceGet();

        OrderSubmitRequest request = new OrderSubmitRequest();
        List<OrderRequest> orders = new ArrayList<>();
        orders.add(new OrderRequest());
        request.setLogiType(4);
        request.setOrders(orders);

        OrderSubmitResponse response2 = YtoUtil.orderSubmit(request);

        YtoUtil.balanceGet();
    }
}
