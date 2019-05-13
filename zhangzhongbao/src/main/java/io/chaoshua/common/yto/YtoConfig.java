package io.chaoshua.common.yto;

/**
 * 运单号实时获取 配置参数
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-08 17:11
 */
public class YtoConfig {
    /**
     * api 域名
     * 测试：test.api.yto18.com
     * 正式：api.yto18.com
     * 注：测试环境生成的单号，不会出走件信息，不能用来发货
     */
    public static final String DOMAIN_URL = "api.yto18.com";

    /**
     * api 请求地址
     */
    public static final String API_URL = "http://" + DOMAIN_URL + "/Api/V1";

    /**
     * appKey
     * 测试：test_1000012
     * 正式：1017157
     */
    public static final String APP_KEY = "1017157";

    /**
     * appSecret
     * 测试：faa1s20ae123fsadf2adf87a91c47
     * 正式：a149702a135942628304bf387c0aff19
     */
    public static final String APP_SECRET = "a149702a135942628304bf387c0aff19";




}
