package io.chaoshua.modules.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.*;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.app.form.*;
import io.chaoshua.modules.app.vo.MissionVo;
import io.chaoshua.modules.app.vo.PicturesVo;
import io.chaoshua.modules.app.vo.UserMissionListVo;
import io.chaoshua.modules.app.vo.user.UserMissionVo;
import io.chaoshua.modules.background.entity.*;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.service.*;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.modules.background.service.mission.MissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dws on 2018/11/21 0021.
 */
@Api(value = "AppMissionController", description = "用户接单")
@RestController
@RequestMapping("/app/user/mission")
public class AppMissionController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MissionService missionService;
    @Autowired
    private MissionDetailService missionDetailService;
    @Autowired
    private PictureStepService pictureStepService;
    @Autowired
    private UserAuthImgService userAuthImgService;
    @Autowired
    private UserAddressService userAddressService;

    @ApiOperation("用户接单")
    @PostMapping("/submit/{taskId}")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    @LocalLock
    public AppResult<UserMissionVo> submit(@PathVariable("taskId") Long taskId, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppResult appResult = null;
        try {
            appResult = missionService.submitMission(taskId, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            appResult = AppResult.error(500, "系统错误,请联系客服!");
        }
        return appResult;
    }


    @PostMapping("/getListByUserId")
    @ApiOperation("获取用户接单列表")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppPage<UserMissionListVo> getListByUserId(@RequestBody MissionForm missionForm, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppPage<UserMissionListVo> appPage = null;
        try {
            appPage = AppPage.success();
            Map<String, Object> map = MissionForm.toMap(missionForm);
            map.put("userId", userId);
            Page<MissionEntity> page = missionService.getHistoryListByUserId(map);
            List<UserMissionListVo> voList = UserMissionListVo.toListVo(page.getRecords());
            appPage.setTotal(page.getTotal());
            appPage.setCurrent(page.getCurrent());
            appPage.setPages(page.getPages());
            appPage.setData(voList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            appPage = AppPage.error(500, "系统错误，请联系客服!");
        }
        return appPage;
    }

    @ApiOperation("订单详情")
    @GetMapping("/getMissionDetail/{missionId}")
    public AppResult<MissionVo> getMissionDetail(@PathVariable("missionId") Long missionId) {
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            MissionVo missionVo = missionService.getMissionDetail(missionId);
            appResult.setData(missionVo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            appResult = AppResult.error(500, "系统错误，请联系客服!");
        }
        return appResult;
    }

    @GetMapping("/getPictures")
    @ApiOperation("获取认证示例图")
    public AppResult<PicturesVo> getPictures() {
        AppResult appResult = null;
        try {
            PictureStepEntity pictureStepEntity = pictureStepService.selectById(1);
            PicturesVo picturesVo = PicturesVo.toVo(pictureStepEntity);
            appResult = AppResult.success();
            appResult.setData(picturesVo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            appResult = AppResult.error(500, "系统错误，请联系客服!");
        }
        return appResult;
    }

    @PostMapping("/updateAuth")
    @ApiOperation("认证信息上传")
    @Transactional
    @Login
    @LocalLock
    public AppStatus updateAuth(@RequestBody UserAuthImgVo vo, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppStatus appStatus = null;
        try {
            List<UserImgVo> list = vo.getAuthImgs();
            List<UserAuthImgEntity> list1 = new ArrayList<>();
            for (UserImgVo userImgVo : list) {
                UserAuthImgEntity userAuthImgEntity = new UserAuthImgEntity();
                userAuthImgEntity.setUrl(userImgVo.getImg());
                userAuthImgEntity.setName(userImgVo.getName());
                userAuthImgEntity.setUserId(userId);
                // modify by lwy 2019.01.14 begin
                userAuthImgEntity.setMissionId(vo.getMissionId());
                // modify by lwy 2019.01.14 end
                userAuthImgEntity.setCreateTime(new Date());
                list1.add(userAuthImgEntity);
            }
            userAuthImgService.insertBatch(list1);
            MissionEntity missionEntity = new MissionEntity();
            missionEntity.setStatus(2);
            missionEntity.setId(vo.getMissionId());
            if (missionService.updateById(missionEntity)) {
                MissionDetailEntity missionDetailEntity = new MissionDetailEntity();
                missionDetailEntity.setUpdateTime(new Date());
                missionDetailService.update(missionDetailEntity, new EntityWrapper<MissionDetailEntity>().eq("mission_id", vo.getMissionId()));
                appStatus = AppStatus.success();
            } else {
                appStatus = AppStatus.error(500, "认证上传错误，请联系客服!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            appStatus = AppStatus.error(500, "系统错误，请联系客服!");
        }
        return appStatus;
    }

    @PostMapping("/updateImportTask")
    @ApiOperation("任务导入")
    @Transactional
    @LocalLock
    public AppStatus updateImportTask(@RequestBody ImportTaskForm form) {
        AppStatus appStatus = null;
        try {
            MissionEntity mission = missionService.selectById(form.getMissionId());
            // 只有状态为2（待拍下）和3（已拍下）才可任务导入
            if (mission.getStatus() != 2 && mission.getStatus() != 3) {
                return AppStatus.error(500, "操作状态错误！请联系客服！");
            }
            if (mission.getIsAnnul() == 2) {
                return AppStatus.error(500, "该任务已被撤回！请联系客服！");
            }
            Integer taskStyle = mission.getTaskStyle();
            Date missionTime = mission.getMissionTime();
            Date date = new Date();
            if (taskStyle == 0) {
                if (missionTime.after(date)) {
                    return AppStatus.error(500, "未到执行时间!");
                }
            } else if (taskStyle == 1) {
                if (DateUtils.addDateDays(missionTime, 1).after(date)) {
                    return AppStatus.error(500, "未到执行时间!");
                }
            } else if (taskStyle == 2) {
                if (DateUtils.addDateDays(missionTime, 2).after(date)) {
                    return AppStatus.error(500, "未到执行时间!");
                }
            }
            // 任务导入  1足迹图 2收藏截图 3已付款图 4宝贝聊天图
            MissionDetailEntity missionDetailEntity = ImportTaskForm.toEntity(form);
            missionDetailEntity.setRemitTime(new Date());
            if (missionDetailService.update(missionDetailEntity, new EntityWrapper<MissionDetailEntity>().eq("mission_id", form.getMissionId()))) {
                MissionEntity missionEntity = new MissionEntity();
                missionEntity.setId(form.getMissionId());
                // 状态改变：订单待拍下(2) --> 订单已拍下即刷手已付款(3)
                missionEntity.setStatus(3);
                // 平台打款状态 待付款(1)
                missionEntity.setIsPay(1);
                Long userId = mission.getUserId();
                //自动填入刷手物流信息
                UserAddressEntity uae = userAddressService.selectByUserId(userId);
                if(uae != null) {
                    //收货人地址,手机号
                    missionEntity.setLogisticsAddress(uae.getProvince() + "," + uae.getCity() + "," + uae.getDistrict() + "," + uae.getAddress());
                    missionEntity.setLogisticsPhone(uae.getReceiveMobile());
                    missionEntity.setLogisticsName(uae.getReceiveName());
                } else {
                    logger.warn("刷手订单导入----用户Id(" + userId + ")无地址！自动跳过");
                }
                // 支付时间
                missionEntity.setPayTime(new Date(System.currentTimeMillis()));
                missionEntity.setTaobaoCode(form.getTaobaoCode());
                if (missionService.updateById(missionEntity)) {
                    appStatus = AppStatus.success();
                } else {
                    appStatus = AppStatus.error(500, "任务导入错误，请联系客服!");
                }
            } else {
                appStatus = AppStatus.error(500, "任务导入错误，请联系客服!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            appStatus = AppStatus.error(500, "系统错误，请联系客服!");
        }
        return appStatus;
    }

    @PostMapping("/updateReceive")
    @ApiOperation("确认收货上传")
    @Login
    @LocalLock
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public AppStatus updateReceive(@RequestBody MissionRecGoodForm form, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppStatus appStatus = null;
        try {
            appStatus = missionDetailService.userUpdateComment(form, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            appStatus = AppStatus.error(500, "系统错误，请联系客服");
        }
        return appStatus;
    }

    @ApiOperation("完成指定追评")
    @PostMapping("/addComment")
    @Login
    @LocalLock
    public AppStatus addComment(@RequestBody AddCommentForm form, @ApiIgnore @RequestAttribute("userId") Long userId) {
        AppStatus appStatus = null;
        try {
            appStatus = missionDetailService.userUpdateAddComment(form, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            appStatus = AppStatus.error(500, "系统错误!");
        }
        return appStatus;
    }


    /**
     * 用户撤销订单
     */
    @ApiOperation("用户撤销任务订单")
    @PostMapping("/updateRemoveInfo")
    @Login
    @ApiImplicitParams(value = {
        @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    @LocalLock
    public AppStatus updateRemoveInfo(@RequestBody MissionDetailEntity missionDetailEntity){
        AppStatus as = new AppStatus();
        missionDetailService.updateRemoveInfo(missionDetailEntity);
        as.setCode(0);
        as.setMsg("撤销任务成功！");
        return as;
    }


}
