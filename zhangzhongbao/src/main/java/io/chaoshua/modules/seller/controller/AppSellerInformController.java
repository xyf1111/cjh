package io.chaoshua.modules.seller.controller;

import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.background.entity.InformEntity;
import io.chaoshua.modules.background.service.InformService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 公告表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-27 11:29:58
 */
@RestController
@RequestMapping("/app/seller/inform")
public class AppSellerInformController {
    @Autowired
    private InformService informService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = informService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
			InformEntity inform = informService.selectById(id);

        return R.ok().put("inform", inform);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody InformEntity inform){
            inform.setCreateTime(new Date());
			informService.insert(inform);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody InformEntity inform){
            inform.setUpdateTime(new Date());
			informService.updateById(inform);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			informService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
