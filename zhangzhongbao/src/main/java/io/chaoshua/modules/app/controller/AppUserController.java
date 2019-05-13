package io.chaoshua.modules.app.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.AppPage;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.common.utils.AppStatus;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.app.form.LowUserForm;
import io.chaoshua.modules.app.form.PageForm;
import io.chaoshua.modules.app.service.SmsCodeService;
import io.chaoshua.modules.app.vo.DetailVo;
import io.chaoshua.modules.app.vo.TaskVo;
import io.chaoshua.modules.app.vo.user.*;
import io.chaoshua.modules.background.entity.*;
import io.chaoshua.modules.background.service.*;
import io.chaoshua.modules.background.service.mission.MissionService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dws on 2018/11/20 0020.
 */
@Api(value = "AppUserController", description = "刷手信息")
@RestController
@RequestMapping("/app/user")
public class AppUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private WithdrawalsService withdrawalsService;
    @Autowired
    private SmsCodeService smsCodeService;
    @Autowired
    private InvitationStepService invitationStepService;
    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/getUserInfo")
    @ApiOperation("获取我的资料")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppResult<UserInfoVo> getUserInfo(@ApiIgnore @RequestAttribute("userId") Long userId) {
        AppResult appResult = null;
        try {
            UserEntity userEntity = userService.selectById(userId);
            UserInfoVo userInfoVo = UserInfoVo.toUserInfoVo(userEntity);
            UserAddressEntity userAddress = userAddressService.selectOne(new EntityWrapper<UserAddressEntity>().eq("user_id", userId));
            if (userAddress != null) {
                userInfoVo.setAddress(userAddress.getProvince() + userAddress.getCity()
                        + userAddress.getDistrict() + userAddress.getAddress());
                // 收件人
                userInfoVo.setReceiveName(userAddress.getReceiveName());
                // 收件人电话
                userInfoVo.setReceiveMobile(userAddress.getReceiveMobile());
            }
            appResult = AppResult.success();
            appResult.setData(userInfoVo);
        } catch (Exception e) {
            e.printStackTrace();
            appResult = AppResult.error(500, "系统错误，请联系客服!");
        }
        return appResult;
    }

    @PostMapping("/getStateMoneyList")
    @ApiOperation("获取不可用佣金明细")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppResult<List<StateMoneyVo>> getStateMoneyList(@RequestBody PageForm pageForm, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            Map<String, Object> map = pageForm.toMap(pageForm);
            List<StateMoneyVo> stateMoneyVos = missionService.getStateMoneyListByUserId(map, userId);
            appResult.setData(stateMoneyVos);
        } catch (Exception e) {
            e.printStackTrace();
            appResult = AppResult.error(500, "系统错误，请联系客服!");
        }
        return appResult;
    }

    @PostMapping("/getUserMessageList")
    @ApiOperation("获取用户消息")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppResult<List<UserMessageVo>> getUserMessageList(@RequestBody PageForm pageForm, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            Map map = pageForm.toMap(pageForm);
            map.put("userId", userId);
            PageUtils page = messageService.queryPage(map);
            List<MessageEntity> list = (List<MessageEntity>) page.getList();
            List<UserMessageVo> voList = new ArrayList<>();
            for (MessageEntity messageEntity : list) {
                UserMessageVo userMessageVo = new UserMessageVo();
                userMessageVo.setContent(messageEntity.getContent());
                voList.add(userMessageVo);
            }
            appResult.setData(voList);
        } catch (Exception e) {
            e.printStackTrace();
            appResult = AppResult.error(500, "系统错误，请联系客服!");
        }
        return appResult;
    }

    @GetMapping("/getInvitationLimit")
    @ApiOperation("获取提现最低限制")
    public BigDecimal getInvitationLimit() {
        InvitationStepEntity invitationStepEntity = invitationStepService.selectById(1);
        return invitationStepEntity.getWithLimit();
    }

    @PostMapping("/getInvitation")
    @ApiOperation("获取我的邀请码")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppResult<List<UserInvitationVo>> getInvitation(@RequestBody PageForm pageForm, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            // 我的邀请码显示不准确问题
            Map map = pageForm.toMap(pageForm);
            map.put("userId", userId);
            PageUtils page = invitationService.queryPage(map);
            List<InvitationEntity> list = (List<InvitationEntity>) page.getList();
            List<UserInvitationVo> voList = new ArrayList<>();
            for (InvitationEntity invitationEntity : list) {
                UserInvitationVo userInvitationVo = UserInvitationVo.toVo(invitationEntity);
                voList.add(userInvitationVo);
            }
            appResult.setData(voList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appResult;
    }

    @PostMapping("/getUserDetail")
    @ApiOperation("获取用户明细")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppPage<DetailVo> getUserDetail(@RequestBody PageForm pageForm, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppPage<DetailVo> appPage = null;
        try {
            Map map = pageForm.toMap(pageForm);
            map.put("userId", userId);
            PageUtils page = detailService.queryPage(map);
            List<DetailEntity> list = (List<DetailEntity>) page.getList();
            List<DetailVo> voList = new ArrayList<>();
            for (DetailEntity detailEntity : list) {
                DetailVo detailVo = DetailVo.toVO(detailEntity);
                voList.add(detailVo);
            }
            appPage = AppPage.success();
            appPage.setData(voList);
            appPage.setTotal((long) page.getTotalCount());
            appPage.setPages((long) page.getTotalPage());
            appPage.setCurrent(page.getCurrPage());
        } catch (Exception e) {
            e.printStackTrace();
            appPage = AppPage.error(500, "系统错误，请联系客服!");
        }
        return appPage;
    }

    @PostMapping("/withdraw")
    @ApiOperation("用户提现")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    @Transactional
    @LocalLock
    public AppStatus withdraw(@RequestBody UserWithdrawVo userWithdrawVo, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppStatus appStatus = null;
        try {
            UserEntity userEntity = userService.selectById(userId);
            if (userEntity.getFreeMoney().compareTo(new BigDecimal(userEntity.getWithLimit())) < 0) {
                // 判断可提现金额是否达到用户提现底线
                return AppStatus.error(500, "账户可提现金额必须大于" + userEntity.getWithLimit());
            }
            if (userEntity.getFreeMoney().compareTo(userWithdrawVo.getMoney()) < 0) {
                // 判断提现金额是否超过可提现金额
                return AppStatus.error(500, "提现金额超出可提现金额!");
            }
            if (!userWithdrawVo.getCardNumber().equals(userEntity.getCardNumber())) {
                // 判断银行卡号和当前用户是否一致
                return AppStatus.error(500, "银行卡号错误!");
            }
            // 判断提现金额是否为10的倍数，提现底线=1时不用判断
            BigDecimal[] results = userWithdrawVo.getMoney().divideAndRemainder(new BigDecimal(10));
            if (userEntity.getWithLimit() != 1 && results[1].compareTo(new BigDecimal(0)) != 0) {
                return AppStatus.error(500, "不是10的倍数无法提现");
            }

            // 更新
            UserEntity user = new UserEntity();
            user.setFreeMoney(userEntity.getFreeMoney().subtract(userWithdrawVo.getMoney()));
            user.setId(userEntity.getId());
            userService.updateById(user);
            // 修改可提现金额
            WithdrawalsEntity withdrawalsEntity = new WithdrawalsEntity();
            withdrawalsEntity.setAmount(userWithdrawVo.getMoney());
            withdrawalsEntity.setUserId(userId);
            withdrawalsEntity.setMobile(userEntity.getMobile());
            withdrawalsEntity.setBank(userEntity.getBank());
            withdrawalsEntity.setTaobao(userEntity.getTaobao());
            withdrawalsEntity.setWechat(userEntity.getWechat());
            withdrawalsEntity.setCardNumber(userEntity.getCardNumber());
            withdrawalsEntity.setHolder(userEntity.getHolder());
            withdrawalsEntity.setCreateTime(new Date());
            if (withdrawalsService.insert(withdrawalsEntity)) {
                appStatus = AppStatus.success();
            } else {
                appStatus = AppStatus.error(500, "提现错误,请联系客服!");
            }
        } catch (Exception e) {
            appStatus = AppStatus.error(500, "系统错误,请联系客服!");
        }
        return appStatus;
    }

    @ApiOperation("我的下级")
    @PostMapping("/lowUser")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppResult<List<LowUserVo>> lowUser(@RequestBody LowUserForm lowUserForm, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            Map map = lowUserForm.toMap(lowUserForm);
            List<LowUserVo> list = userService.getFirstUserByUserId(map, userId, lowUserForm.getType());
            appResult.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            appResult = AppResult.error(500, "系统错误，请联系客服!");
        }
        return appResult;
    }

    @PostMapping("/forgetPassword")
    @ApiOperation("忘记密码")
    public AppStatus forgetPassword(@RequestBody UserForgetVo userForgetVo) {
        AppStatus appStatus = null;
        try {
            String smsCode = userForgetVo.getSmsCode();
            String mobile = userForgetVo.getMobile();
            String newPassword = userForgetVo.getPassword();
            String confirmPassword = userForgetVo.getConfirmPassword();
            appStatus = smsCodeService.valid(mobile, smsCode);
            if (appStatus.getCode() == 500) {
                return appStatus;
            }
            if (newPassword.equals(confirmPassword)) {
                UserEntity userEntity = userService.selectOne(new EntityWrapper<UserEntity>().eq("mobile", mobile));
                if (userEntity != null) {
                    UserEntity user = new UserEntity();
                    user.setId(userEntity.getId());
                    user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
                    userService.updateById(user);
                    appStatus = AppStatus.success();
                } else {
                    appStatus = AppStatus.error(500, "该用户不存在!");
                }

            } else {
                appStatus = AppStatus.error(500, "前后密码不一致!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appStatus;
    }
}
