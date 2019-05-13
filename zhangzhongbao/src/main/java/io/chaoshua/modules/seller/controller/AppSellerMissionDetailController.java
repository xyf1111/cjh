package io.chaoshua.modules.seller.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.utils.DateUtils;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.background.entity.UserEntity;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.service.UserService;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.modules.background.service.mission.MissionService;
import io.chaoshua.modules.weixin.service.SendInfoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-20 17:52:57
 */
@RestController
@RequestMapping("/app/seller/missiondetail")
public class AppSellerMissionDetailController {
    @Autowired
    private MissionDetailService missionDetailService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private UserService userService;
    @Autowired
    private SendInfoController sendInfoController;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = missionDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MissionDetailEntity missionDetail = missionDetailService.selectById(id);

        return R.ok().put("missionDetail", missionDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MissionDetailEntity missionDetail) {
        missionDetailService.insert(missionDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody MissionDetailEntity missionDetail) {
        if (missionDetailService.updateById(missionDetail)) {
            MissionEntity missionEntity = new MissionEntity();
            missionEntity.setId(missionDetail.getMissionId());
            missionEntity.setRole(3);
            missionService.updateById(missionEntity);
        }
        return R.ok();
    }
    /**
     * 提交报错
     */
    @RequestMapping("/updateBad")
    @Transactional
    public R updateBad(@RequestBody MissionDetailEntity missionDetail) {
        MissionEntity missionEntity = new MissionEntity();
        missionEntity.setIsBad(2);
        missionService.update(missionEntity,new EntityWrapper<MissionEntity>().eq("id",missionDetail.getMissionId()));
        if(missionDetailService.update(missionDetail,new EntityWrapper<MissionDetailEntity>().eq("mission_id",missionDetail.getMissionId()))){
            UserEntity userEntity = userService.selectById(missionDetail.getUserId());
            if (userEntity.getOpenId() != null){
                sendInfoController.sendInfo(userEntity.getOpenId(),"您好，您的订单被报错",missionDetail.getMissionCode(),
                        DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN),"报错原因:"+missionDetail.getBadInfo());
            }
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/updateRemoveInfo")
    @Transactional
    public R updateRemoveInfo(@RequestBody MissionDetailEntity missionDetail) {
        missionDetailService.update(missionDetail,new EntityWrapper<MissionDetailEntity>().eq("mission_id",missionDetail.getMissionId()));
        UserEntity userEntity = userService.selectById(missionDetail.getUserId());
        if (userEntity.getOpenId() != null){//推送通知
            sendInfoController.sendInfo(userEntity.getOpenId(),"您有已接订单被撤销",missionDetail.getMissionCode(),
                    DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN),"撤销原因："+missionDetail.getCancelNote());
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        missionDetailService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 指定评价/追评
     * @param missionDetailEntity
     * @return
     */
    @RequestMapping("/isComment")
    public  R isComment(@RequestBody MissionDetailEntity missionDetailEntity){
        return  missionDetailService.isComment(missionDetailEntity);
    }
}
