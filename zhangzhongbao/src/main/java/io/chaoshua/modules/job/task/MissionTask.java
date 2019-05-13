package io.chaoshua.modules.job.task;

import com.baomidou.mybatisplus.plugins.Page;
import io.chaoshua.common.utils.DateUtils;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;
import io.chaoshua.modules.background.dao.mission.MissionDao;
import io.chaoshua.modules.background.dao.mission.MissionDetailDao;
import io.chaoshua.modules.background.entity.InvitationStepEntity;
import io.chaoshua.modules.background.entity.UserEntity;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.service.InvitationStepService;
import io.chaoshua.modules.background.service.UserService;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.modules.background.service.mission.MissionService;
import io.chaoshua.modules.job.entity.TaskMissionEntity;
import io.chaoshua.modules.weixin.service.SendInfoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单定时提醒处理
 * Created by dws on 2018/12/5 0005.
 */
@Component("MissionTask")
public class MissionTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MissionDao missionDao;
    @Autowired
    private MissionDetailDao missionDetailDao;
    @Autowired
    private InvitationStepService invitationStepService;
    @Autowired
    private UserService userService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private MissionDetailService missionDetailService;
    @Autowired
    private SendInfoController sendInfoController;

    public void roleTask(){
        logger.info("订单执行提醒处理正在被执行!");
        Thread thread = new Thread(new Runnable() {
            public void run() {
                InvitationStepEntity invitationStepEntity = invitationStepService.selectById(1);
                Map map = new HashMap();
                map.put("role",1);
                map.put("status",1);
                map.put("time",invitationStepEntity.getAuthTime());
                List<TaskMissionEntity> list = missionDao.getAllListByRole(map);
                if (list.size()>0){
                    for (TaskMissionEntity missionEntity:list){
                        UserEntity userEntity = userService.selectById(missionEntity.getUserId());
                        MissionEntity mission = new MissionEntity();
                        mission.setId(missionEntity.getId());
                        mission.setUserId(missionEntity.getUserId());
                        mission.setTaskId(missionEntity.getTaskId());
                        mission.setMissionCode(missionEntity.getMissionCode());
                        mission.setMissionTime(missionEntity.getMissionTime());
                        mission.setShopCategory(missionEntity.getShopCategory());
                        mission.setRole(3);
                        mission.setRefuseNote("未进行认证被系统自动拒绝");
                        missionService.updateRole(mission);//更变订单状态
                        Date date = new Date();
                        if (userEntity.getOpenId() != null){
                            sendInfoController.sendInfo(userEntity.getOpenId(),"未进行认证被系统自动拒绝",missionEntity.getMissionCode(),
                                    DateUtils.format(date,DateUtils.DATE_TIME_PATTERN),"由于长时间未上传认证图片，后台已自动拒绝了您的订单。");
                        }
                    }
                }
            }
        });
        thread.start();
    }

    public void missionTask(){
        logger.info("订单执行时间已到提醒处理正在被执行!");
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Date date = new Date();
                Map map = new HashMap();
                map.put("role",2);
                map.put("missionTime",date);
                List<TaskMissionEntity> list = missionDao.getAllListByRole(map);
                for (TaskMissionEntity missionEntity :list) {
                    UserEntity userEntity = userService.selectById(missionEntity.getUserId());
                    if (userEntity.getOpenId() != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("您好，您有订单（类目为：").append(missionEntity.getShopCategory()).append(")").append("已到执行时间，请在30分钟内完成任务并上传图片。");
                        sendInfoController.sendInfo(userEntity.getOpenId(), stringBuilder.toString(), missionEntity.getMissionCode(),
                                DateUtils.format(date, DateUtils.DATE_TIME_PATTERN), "注意：请用平台注册的淘宝号执行任务。如果发现换号现象，每次扣6金币。");
                    }
            }
            }
        });
        thread.start();
    }

    public void viewTask(){
        logger.info("订单浏览1时间已到提醒处理正在被执行!");
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Map map = new HashMap();
                map.put("role",2);
                map.put("missionTime",DateUtils.addDateDays(new Date(),1));
                List<TaskMissionEntity> list = missionDao.getAllListByRole(map);
                Date date = new Date();
                for (TaskMissionEntity missionEntity :list) {
                    UserEntity userEntity = userService.selectById(missionEntity.getUserId());
                    if (userEntity.getOpenId() != null){
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("您好，您有订单（类目为：").append(missionEntity.getShopCategory()).append(")").append("已到浏览时间，请在30分钟内完成浏览并上传截图。");
                        sendInfoController.sendInfo(userEntity.getOpenId(), stringBuilder.toString(), missionEntity.getMissionCode(),
                                DateUtils.format(date, DateUtils.DATE_TIME_PATTERN)+"(浏览1时间)", "注意：请用平台注册的淘宝号执行任务。如果发现换号现象，每次扣6金币。");
                    }
                }
            }
        });
        thread.start();
    }

    public void receiveGood(){
        logger.info("商家自动发货正在被执行!");
        try {
            //missionDao.updateStatusReceiveGood();
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * crontab: 0 0/5 * * * ?
     */
    public void autoTimeoutMission(){
        logger.info("刷手接单后45分钟未处理视为自动弃单，自动返回任务大厅！");
        try {
            Date nowDate = new Date(System.currentTimeMillis());
            Map<String, Object> params =  new HashMap<>();
            //未报错已通过审核的被刷手接单的订单
            params.put("role", 2);
            params.put("status", 2);
            params.put("isBad", 1);
            params.put("taskStyle", 0);
            params.put("isAnnul", 1);
            params.put("limit", "999");
            // 获取已审核的订单
            Page<MissionEntity> page = new Query<MissionEntity>(params).getPage();
            List<MissionEntity> missionEntities = missionDao.getList(page, params);
            for(MissionEntity mission : missionEntities) {
                Date orderTime = mission.getOrderTime();
                if (orderTime == null) continue;
                //超过1小时自动撤回大厅
                if(nowDate.getTime() - orderTime.getTime() >= 45 * 60 * 1000) {
                    logger.warn("订单编号(" + mission.getMissionCode() + ")的刷手:" + mission.getUserName() +  "，处理超时！自动返回大厅！");
                    long missionId = mission.getId();
                    //根据missionId获取MissionDetailEntity
                    MissionDetailEntity missionDetailEntity = missionDetailDao.select(missionId);
                    //自动任务撤销返回大厅
                    missionDetailEntity.setIsBack(1);
                    missionDetailService.updateRemoveInfo(missionDetailEntity);
                }
            }
        } catch (Exception e) {
            logger.error("刷手接单后1个小时未处理视为自动弃单，自动返回任务大厅处理失败！异常原因：" + e.getMessage());
            logger.error(e.getMessage(), e);
        }
    }


}
