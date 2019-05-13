package io.chaoshua.modules.background.controller;

import java.util.Arrays;
import java.util.Map;

import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.service.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 
 *
 * @author dws
 * @date 2018-09-20 17:52:57
 */
@RestController
@RequestMapping("background/missiondetail")
public class MissionDetailController {
    @Autowired
    private MissionDetailService missionDetailService;
    @Autowired
    private MissionService missionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = missionDetailService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        MissionDetailEntity missionDetail = missionDetailService.selectById(id);
        return R.ok().put("missionDetail", missionDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MissionDetailEntity missionDetail){
		missionDetailService.insert(missionDetail);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody MissionDetailEntity missionDetail){
        if(missionDetailService.updateById(missionDetail)){
            MissionEntity missionEntity = new MissionEntity();
            missionEntity.setId(missionDetail.getMissionId());
            missionEntity.setRole(3);
            missionService.updateById(missionEntity);
        }
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		missionDetailService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 撤销订单
     */
    @RequestMapping("/updateRemoveInfo")
    @LocalLock
    public R updateRemoveInfo(@RequestBody MissionDetailEntity missionDetailEntity){
        return  missionDetailService.updateRemoveInfo(missionDetailEntity);
    }
}
