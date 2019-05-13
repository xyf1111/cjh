package io.chaoshua.modules.background.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.chaoshua.common.utils.ShiroUtils;
import io.chaoshua.modules.background.entity.UserEntity;
import io.chaoshua.modules.background.service.UserService;
import io.chaoshua.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.DetailEntity;
import io.chaoshua.modules.background.service.DetailService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 流水表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-18 11:54:46
 */
@RestController
@RequestMapping("background/detail")
public class DetailController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DetailService detailService;
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:detail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = detailService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:detail:info")
    public R info(@PathVariable("id") Long id){
		DetailEntity detail = detailService.selectById(id);
        return R.ok().put("detail", detail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @Transactional
    public R save(@RequestBody DetailEntity detail){
        UserEntity userEntity = userService.selectById(detail.getUserId());
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        logger.warn(sysUserEntity.getUsername() + "(" + sysUserEntity.getUserId() +
                ")修改刷手(" + detail.getUserId()  + ")金额：" + detail.getAmount());
        BigDecimal money = userEntity.getFreeMoney().add(detail.getAmount());
        detail.setCode(detail.getCode() + ";" + sysUserEntity.getUserId());
        if (money.compareTo(new BigDecimal(0)) < 0){
            return R.error(500,"修改余额错误!");
        }
        // 创建时间
        detail.setCreateTime(new Date(System.currentTimeMillis()));
        detailService.insert(detail);
        UserEntity user = new UserEntity();
        user.setFreeMoney(userEntity.getFreeMoney().add(detail.getAmount()));
        user.setId(userEntity.getId());
        user.setAmount(userEntity.getAmount()+Integer.parseInt(detail.getAmount().toString()));
        userService.updateById(user);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:detail:update")
    public R update(@RequestBody DetailEntity detail){
        detailService.updateById(detail);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:detail:delete")
    public R delete(@RequestBody Long[] ids){
			detailService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
