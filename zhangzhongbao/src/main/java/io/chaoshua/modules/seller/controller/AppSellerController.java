package io.chaoshua.modules.seller.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.utils.AppStatus;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.app.service.SmsCodeService;
import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.background.service.SellerService;
import io.chaoshua.modules.seller.vo.BindAgentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.Arrays;
import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-27 15:45:30
 */
@RestController
@RequestMapping("/app/seller/user")
public class AppSellerController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    SmsCodeService smsCodeService;

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        SellerEntity sellerEntity = sellerService.selectById(id);

        return R.ok().put("user", sellerEntity);
    }
    /**
     * 获取商家信息
     */
    @RequestMapping("/getBalance")
    @Login
    public R getBalance(@ApiIgnore @RequestAttribute("userId") Long sellerId) {
        SellerEntity sellerEntity = sellerService.selectById(sellerId);
        return R.ok().put("seller", sellerEntity);
    }

    /**
     * 保存(商家注册)
     */
    @RequestMapping("/save")
    public R save(@RequestBody Map<String,Object> params) {
        return sellerService.insertByMap(params);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Login
    public R update(@RequestBody Map<String,Object> params,@RequestAttribute("userId") Long sellerId) {
        String newPassword = params.get("newPassword").toString();
        String password = params.get("password").toString();
        String confirmPassword = params.get("confirmPassword").toString();
        SellerEntity sellerEntity = sellerService.selectById(sellerId);
        if (!BCrypt.checkpw(password,sellerEntity.getPassword())){
            return R.error(500,"旧密码错误!");
        }else {
            if (newPassword.equals(confirmPassword)){
                SellerEntity sellerEntity1 = new SellerEntity();
                sellerEntity1.setId(sellerEntity.getId());
                sellerEntity1.setPassword(BCrypt.hashpw(newPassword,BCrypt.gensalt()));
                sellerService.updateById(sellerEntity1);
                return R.ok();
            }else {
                return R.error(500,"前后密码不一致!");
            }
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        sellerService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 忘记密码
     * @param params
     * @return
     */
    @RequestMapping("/forgetPassword")
    public AppStatus forgetPassword(@RequestBody Map<String,Object> params){
        AppStatus appStatus = null;
        String smsCode = params.get("smsCode").toString();
        String mobile = params.get("mobile").toString();
        String newPassword = params.get("newPassword").toString();
        String confirmPassword = params.get("confirmPassword").toString();

         if(smsCodeService.valid(mobile,smsCode).getCode() == 500){
             return smsCodeService.valid(mobile,smsCode);
         }
        if (newPassword.equals(confirmPassword)){
            SellerEntity sellerEntity = sellerService.selectOne(new EntityWrapper<SellerEntity>().eq("mobile",mobile));
            if (sellerEntity != null){
               SellerEntity seller = new SellerEntity();
                seller.setId(sellerEntity.getId());
                seller.setPassword(BCrypt.hashpw(newPassword,BCrypt.gensalt()));
               sellerService.updateById(seller);
             appStatus = AppStatus.success();
            }else {
              appStatus =  appStatus.error(500,"该用户不存在!");
            }

        }else {
            appStatus = appStatus.error(500,"前后密码不一致!");
        }
        return appStatus;
    }

    /**
     * 绑定代理商校验
     *
     * @param mobile 商家手机号码
     * @return
     */
    @GetMapping(value = "/bindAgentCheck/{mobile}")
    public R bindAgentCheck(@PathVariable("mobile") String mobile){
        // 是否已存在商家（可以绑定） 1 是 0 否
        Integer isPermitBind = sellerService.bindAgentCheck(mobile);
        return R.ok().put("isPermitBind", isPermitBind);
    }

    /**
     * 绑定代理商
     *
     * @return
     */
    @PostMapping(value = "bindAgent")
    public R bindAgent(@RequestBody BindAgentVO params) {
        sellerService.bindAgent(params);
        return R.ok();
    }
}
