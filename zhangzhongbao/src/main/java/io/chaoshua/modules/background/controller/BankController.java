package io.chaoshua.modules.background.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.BankEntity;
import io.chaoshua.modules.background.service.BankService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 银行表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-19 16:52:51
 */
@RestController
@RequestMapping("background/bank")
public class BankController {
    @Autowired
    private BankService bankService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:bank:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bankService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/getList")
    @RequiresPermissions("background:bank:list")
    public R getList(@RequestParam Map<String, Object> params){
        List<BankEntity> list = bankService.selectList(null);

        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:bank:info")
    public R info(@PathVariable("id") Long id){
			BankEntity bank = bankService.selectById(id);

        return R.ok().put("bank", bank);
    }



    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:bank:save")
    public R save(@RequestBody BankEntity bank){
            bank.setCreateTime(new Date());
			bankService.insert(bank);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:bank:update")
    public R update(@RequestBody BankEntity bank){
            bank.setUpdateTime(new Date());
			bankService.updateById(bank);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:bank:delete")
    public R delete(@RequestBody Long[] ids){
			bankService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
