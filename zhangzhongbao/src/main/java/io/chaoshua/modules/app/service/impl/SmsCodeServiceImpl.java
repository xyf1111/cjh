package io.chaoshua.modules.app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.qcloudsms.SmsSingleSenderResult;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.utils.*;
import io.chaoshua.modules.app.dao.SmsCodeDao;
import io.chaoshua.modules.app.entity.SmsCodeEntity;
import io.chaoshua.modules.app.service.SmsCodeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;


@Service("smsCodeService")
public class SmsCodeServiceImpl extends ServiceImpl<SmsCodeDao, SmsCodeEntity> implements SmsCodeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SmsCodeEntity> page = this.selectPage(
                new Query<SmsCodeEntity>(params).getPage(),
                new EntityWrapper<SmsCodeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public SmsCodeEntity queryByMobile(String mobile) {
        SmsCodeEntity smsCodeEntity = new SmsCodeEntity();
        smsCodeEntity.setMobile(mobile);
        return baseMapper.selectOne(smsCodeEntity);
    }

    @Override
    public String get(String mobile) {
        //验证手机号码
        boolean isMatch = Pattern.matches(Constant.REGEX_MOBILE_EXACT, mobile);
        if (!isMatch) {
            throw new RRException("手机号码格式不正确");
        }
        String code = GenerateCodeUtils.randomNumber(6);
        ///发送验证码
        ArrayList<String> params = new ArrayList<>();
        params.add(code);
        SmsSingleSenderResult result = QCloudSmsUtils.sendSmsSingleByTemplate(mobile, params);
        ///{"result":0,"errmsg":"OK","ext":"","sid":"8:s2X8ZWhZABqA5ejuy9R20180426","fee":1}
        if (result.result != 0) {
            System.out.println("获取验证码：" + result);
            throw new RRException(result.errMsg, result.result);
        }

        //保存验证码数据
        SmsCodeEntity smsCode = queryByMobile(mobile);
        if (smsCode == null) {
            smsCode = new SmsCodeEntity();
            smsCode.setMobile(mobile);
            smsCode.setCode(code);
            //设置默认发送模版类型
            smsCode.setType(QCloudSmsUtils.SMS_TEMPLATE_ID);
            smsCode.setCreateTime(new Date());
            smsCode.setUpdateTime(new Date());
            baseMapper.insert(smsCode);
        } else {
            smsCode.setCode(code);
            smsCode.setUpdateTime(new Date());
            baseMapper.updateById(smsCode);
        }
        return code;
    }

    @Override
    public AppStatus valid(String mobile, String code) {
        //根据手机号码查找
        AppStatus appStatus  = AppStatus.success();
        SmsCodeEntity smsCode = queryByMobile(mobile);
        if (smsCode == null || !smsCode.getCode().equals(code)) {
            return AppStatus.error(500,"验证码错误");
        }
        //过期时间
        Date expireTime = DateUtils.addDateMinutes(smsCode.getUpdateTime(), Constant.SMS_CODE_EXPIRE);
        if (Constant.SMS_CODE_ISEXPIRE && new Date().after(expireTime)) {
            return AppStatus.error(500,"验证码过期，请重新发送");
        }
        return appStatus;
    }
}
