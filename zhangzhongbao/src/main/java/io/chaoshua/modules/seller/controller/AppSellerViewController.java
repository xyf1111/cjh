package io.chaoshua.modules.seller.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.ViewEntity;
import io.chaoshua.modules.background.service.ViewService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 标签单浏览表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-07 17:26:10
 */
@RestController
@RequestMapping("background/view")
public class AppSellerViewController {
    @Autowired
    private ViewService viewService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = viewService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
			ViewEntity view = viewService.selectById(id);

        return R.ok().put("view", view);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ViewEntity view){
			viewService.insert(view);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ViewEntity view){
			viewService.updateById(view);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
			viewService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
