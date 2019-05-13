package io.chaoshua.modules.background.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.PayeeEntity;
import io.chaoshua.modules.background.service.PayeeService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 收款人银行账号表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 17:11:25
 */
@RestController
@RequestMapping("background/payee")
public class PayeeController {
    @Autowired
    private PayeeService payeeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:payee:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = payeeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:payee:info")
    public R info(@PathVariable("id") Long id){
			PayeeEntity payee = payeeService.selectById(id);

        return R.ok().put("payee", payee);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:payee:save")
    public R save(@RequestBody PayeeEntity payee){
			payeeService.insert(payee);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:payee:update")
    public R update(@RequestBody PayeeEntity payee){
			payeeService.updateById(payee);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:payee:delete")
    public R delete(@RequestBody Long[] ids){
			payeeService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
