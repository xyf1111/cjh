package io.chaoshua.modules.background.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.chaoshua.modules.app.vo.ImgVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.ImgEntity;
import io.chaoshua.modules.background.service.ImgService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 示例表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-01-09 14:08:24
 */
@RestController
@RequestMapping("background/img")
public class ImgController {
    @Autowired
    private ImgService imgService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:img:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = imgService.queryPage(params);
        List<ImgEntity> list = (List<ImgEntity>) page.getList();
        for (ImgEntity imgEntity :list){
            imgEntity.setImgList(imgEntity.getImg().split(","));
        }
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:img:info")
    public R info(@PathVariable("id") Long id){
        ImgEntity img = imgService.selectById(id);
        return R.ok().put("img", img);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:img:save")
    public R save(@RequestBody ImgEntity img){
			imgService.insert(img);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:img:update")
    public R update(@RequestBody ImgEntity img){
			imgService.updateById(img);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:img:delete")
    public R delete(@RequestBody Long[] ids){
			imgService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
