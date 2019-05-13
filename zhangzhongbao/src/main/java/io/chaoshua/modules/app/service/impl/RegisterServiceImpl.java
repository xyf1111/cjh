package io.chaoshua.modules.app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.common.validator.ValidatorUtils;
import io.chaoshua.modules.app.dao.AppUserDao;
import io.chaoshua.modules.app.entity.AppUserEntity;
import io.chaoshua.modules.app.form.RegisterForm;
import io.chaoshua.modules.app.form.UserImgVo;
import io.chaoshua.modules.app.service.AppUserService;
import io.chaoshua.modules.app.service.RegisterService;
import io.chaoshua.modules.app.service.SmsCodeService;
import io.chaoshua.modules.background.entity.InvitationEntity;
import io.chaoshua.modules.background.entity.InvitationStepEntity;
import io.chaoshua.modules.background.entity.UserAddressEntity;
import io.chaoshua.modules.background.entity.UserImgEntity;
import io.chaoshua.modules.background.service.InvitationService;
import io.chaoshua.modules.background.service.InvitationStepService;
import io.chaoshua.modules.background.service.UserAddressService;
import io.chaoshua.modules.background.service.UserImgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dws on 2018/10/23 0023.
 */
@Service("registerService")
public class RegisterServiceImpl extends ServiceImpl<AppUserDao, AppUserEntity> implements RegisterService {

    @Autowired
    private AppUserService userService;
    @Autowired
    InvitationService invitationService;
    @Autowired
    InvitationStepService invitationStepService;
    @Autowired
    SmsCodeService smsCodeService;
    @Autowired
    private UserImgService userImgService;
    @Autowired
    private UserAddressService userAddressService;


    @Override
    @Transactional
    public AppResult register(RegisterForm form) {
        AppResult appResult = null;
        //表单校验
        ValidatorUtils.validateEntity(form);
        InvitationEntity invitationEntity = invitationService.selectOne(new EntityWrapper<InvitationEntity>().eq("code", form.getParentInvitation()));
        InvitationStepEntity invitationStepEntity = invitationStepService.selectById(1);
        appResult = check(form, invitationEntity, invitationStepEntity.getOpen());
        if (appResult.getCode() != 0) {
            return appResult;
        }
        AppUserEntity userEntity = AppUserEntity.toEntity(form);
        if (invitationStepEntity.getOpen().equals(1)) {
            userEntity.setHighUserId(invitationEntity.getUserId());
            //修改邀请码状态
            invitationEntity.setUpdateTime(new Date());
            invitationEntity.setIsUse(2);
            invitationEntity.setUseMan(userEntity.getName());
            invitationEntity.setUseTime(new Date());
            invitationService.updateById(invitationEntity);
        } else {//默认是客服账号
            userEntity.setHighUserId(0L);
        }
        userEntity.setDayLimit(invitationStepEntity.getDayLimit());
        userEntity.setMonthLimit(invitationStepEntity.getMonthLimit());
        userEntity.setLimit(invitationStepEntity.getLimit());
        userEntity.setWithLimit(invitationStepEntity.getWithLimit());
        userEntity.setCreateTime(new Date());
        if (userService.insert(userEntity)) {
            List<UserImgVo> list = form.getImgs();
            List<UserImgEntity> list1 = new ArrayList<>();
            for (UserImgVo userImgVo : list) {//存入注册图片
                UserImgEntity userImgEntity = new UserImgEntity();
                userImgEntity.setImg(userImgVo.getImg());
                userImgEntity.setName(userImgVo.getName());
                userImgEntity.setUserId(userEntity.getId());
                list1.add(userImgEntity);
            }
            userImgService.insertBatch(list1);
            // 存入用户地址
            UserAddressEntity userAddress = new UserAddressEntity();
            userAddress.setUserId(userEntity.getId());
            userAddress.setReceiveName(form.getReceiveName());
            userAddress.setReceiveMobile(form.getReceiveMobile());
            String[] areas = StringUtils.split(form.getArea()," ");
            if (areas != null && areas.length > 2) {
                userAddress.setProvince(areas[0]);
                userAddress.setCity(areas[1]);
                userAddress.setDistrict(areas[2]);
            }
            userAddress.setAddress(form.getAddress());
            userAddress.setCreateTime(new Date());
            userAddress.setUpdateTime(new Date());
            userAddressService.insert(userAddress);
            appResult = AppResult.success();
        } else {
            appResult = AppResult.error(500, "注册错误!");
        }
        return appResult;
    }

    /**
     * 验证合法性
     *
     * @param form
     */
    public AppResult check(RegisterForm form, InvitationEntity invitationEntity, Integer open) {
        if (!form.getPassword().equals(form.getPasswordAgain())) {
            return AppResult.error(1001, "前后密码不一致");
        }
        AppUserEntity userEntity = userService.selectOne(new EntityWrapper<AppUserEntity>().eq("mobile", form.getMobile()));
        if (userEntity != null) {
            return AppResult.error(1002, "此手机号已被注册");
        }
        AppUserEntity appUserEntity = userService.selectOne(new EntityWrapper<AppUserEntity>().eq("taobao", form.getTaobao()));
        if (appUserEntity != null) {
            return AppResult.error(1003, "此淘宝号已被注册");
        }
        if (open.equals(1)) {//是否需要邀请码，1：是，2否
            if (invitationEntity != null) {
                if (invitationEntity.getStatus() == 2) {
                    return AppResult.error(1005, "该邀请码已被禁用");
                }
                if (invitationEntity.getIsUse() == 2) {
                    return AppResult.error(1006, "该邀请码已被使用");
                }
            } else {
                return AppResult.error(1007, "该邀请码不存在");
            }
        }
        return AppResult.success();
    }

    /**
     * 获取邀请码信息
     *
     * @param str
     * @return
     */
    public InvitationEntity getInvitationEntity(String str) {
        InvitationEntity invitationEntity = invitationService.selectOne(new EntityWrapper<InvitationEntity>().eq("code", str));
        return invitationEntity;
    }
}
