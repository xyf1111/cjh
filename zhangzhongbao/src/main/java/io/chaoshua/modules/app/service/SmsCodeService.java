package io.chaoshua.modules.app.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.AppStatus;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.app.entity.SmsCodeEntity;

import java.util.Map;

/**
 * 短信验证码
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2018-04-25 16:07:10
 */
public interface SmsCodeService extends IService<SmsCodeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据手机号码查找
     *
     * @param mobile 手机号
     * @return
     */
    SmsCodeEntity queryByMobile(String mobile);

    /**
     * 获取短信验证码
     *
     * @param mobile 手机号码
     * @return 验证码
     */
    String get(String mobile);

    /**
     * 校验短信验证码
     *
     * @param mobile 手机号
     * @param code   验证码
     */
    AppStatus valid(String mobile, String code);
}

