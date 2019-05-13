package io.chaoshua.modules.job.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;
import io.chaoshua.modules.agent.service.AgentSellerMoneyService;
import io.chaoshua.modules.background.entity.DetailEntity;
import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.background.entity.agent.AgentEntity;
import io.chaoshua.modules.background.entity.agent.AgentMoneyEntity;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.chaoshua.modules.background.enums.MissionStatusEnum;
import io.chaoshua.modules.background.service.DetailService;
import io.chaoshua.modules.background.service.SellerService;
import io.chaoshua.modules.background.service.agent.AgentMoneyService;
import io.chaoshua.modules.background.service.agent.AgentService;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.modules.background.service.mission.MissionService;
import io.chaoshua.modules.background.service.task.TaskService;
import io.chaoshua.modules.background.util.ComparePrice;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-28 17:30
 */
@Component("agentMoneyTask")
public class AgentMoneyTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentMoneyService agentMoneyService;
    @Autowired
    private AgentSellerMoneyService agentSellerMoneyService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private MissionDetailService missionDetailService;
    @Autowired
    private DetailService detailService;

    public void deal() {
        Map<Long, AgentMoneyEntity> agentMoneyMap = new HashedMap();
        Map<Long, AgentSellerMoneyEntity> agentSellerMoneyMap = new HashedMap();

        Long a = System.currentTimeMillis();
        // 先清空所有代理明显记录
        detailService.delete(new EntityWrapper<DetailEntity>().eq("is_seller", "3"));

        // 代理商家余额清零
        List<AgentEntity> agentList = agentService.selectList(null);
        for (AgentEntity agent : agentList) {
            agent.setBalance(new BigDecimal(0));
        }
        agentService.updateBatchById(agentList);

        // 获取所有代理下的商家
        List<SellerEntity> sellerList = sellerService.selectList(
                new EntityWrapper<SellerEntity>().eq("is_seller", 3));

        int totalTask = 0;
        int totalMission = 0;
        int totalDetail = 0;

        for (SellerEntity seller : sellerList) {
            // 代理发单金额
            AgentMoneyEntity agentMoney = null;
            if (agentMoneyMap.containsKey(seller.getPid())) {
                agentMoney = agentMoneyMap.get(seller.getPid());
            } else {
                agentMoney = agentMoneyService.selectOne(new EntityWrapper<AgentMoneyEntity>()
                        .eq("agent_id", seller.getPid()));
                agentMoneyMap.put(agentMoney.getAgentId(), agentMoney);
            }

            // 代理下商家发单金额
            // 代理发单金额
            AgentSellerMoneyEntity agentSellerMoney = null;
            if (agentSellerMoneyMap.containsKey(seller.getId())) {
                agentSellerMoney = agentSellerMoneyMap.get(seller.getId());
            } else {
                agentSellerMoney = agentSellerMoneyService.selectOne(new EntityWrapper<AgentSellerMoneyEntity>()
                        .eq("seller_id", seller.getId()));
                agentSellerMoneyMap.put(seller.getId(), agentSellerMoney);
            }

            if (agentSellerMoney == null) {
                continue;
            }

            // 获取代理下商家发布的任务
            List<TaskEntity> taskList = taskService.selectList(new EntityWrapper<TaskEntity>()
                    .eq("seller_id", seller.getId()).gt("create_time", agentSellerMoney.getCreateTime()));
            if (taskList == null || taskList.size() == 0) {
                continue;
            }
            // 计算每个任务代理商家每单获取佣金
            for (TaskEntity task : taskList) {
                // 计算任务每单代理商获得佣金
                BigDecimal agentMoneyValue = ComparePrice.getAgentMoney(task.getPrice(), agentSellerMoney, agentMoney);
                // 更新每单金额
                task.setAgentMoney(agentMoneyValue);
                // 更新任务 代理商佣金
                taskService.updateById(task);
                totalTask++;
                // 修改已通过任务下的订单，代理商佣金
                if (task.getRole() == 2) {
                    List<MissionEntity> missionList = missionService.selectList(new EntityWrapper<MissionEntity>()
                            .eq("task_id", task.getId()));
                    for (MissionEntity mission : missionList) {
                        // 更新订单详情中 agentMoney字段
                        // 只处理未拒绝以及未撤销的订单
                        if (mission.getRole() != 3 && mission.getIsAnnul() != 2) {
                            MissionDetailEntity missionDetail = missionDetailService.selectOne(
                                    new EntityWrapper<MissionDetailEntity>().eq("mission_id", mission.getId()));
                            if (missionDetail != null) {
                                missionDetail.setAgentMoney(agentMoneyValue);
                                missionDetailService.updateById(missionDetail);
                                totalMission++;
                                if (MissionStatusEnum.USER_RECEIVE.getValue().equals(mission.getStatus())
                                        && missionDetail.getAgentMoney().doubleValue() > 0) {
                                    // 刷手已收货（订单已完成）
                                    // 更新余额
                                    AgentEntity agent = agentService.selectById(seller.getPid());
                                    agent.setBalance(agent.getBalance().add(missionDetail.getAgentMoney()));
                                    agent.setUpdateTime(new Date());
                                    agentService.updateById(agent);
                                    // 添加记录
                                    DetailEntity detailEntity = new DetailEntity();
                                    detailEntity.setIsSeller(3);
                                    detailEntity.setType(14);
                                    detailEntity.setUserId(agent.getId());
                                    detailEntity.setUserName(agent.getName());
                                    detailEntity.setMobile(agent.getMobile());
                                    detailEntity.setAmount(missionDetail.getAgentMoney());
                                    detailEntity.setCode(mission.getMissionCode());
                                    detailEntity.setNote("下级商家订单完成获取奖励金" + missionDetail.getAgentMoney() + "元");
                                    detailEntity.setBalance(agent.getBalance());
                                    detailEntity.setCreateTime(missionDetail.getCommentTime() == null ? new Date() : missionDetail.getCommentTime());
                                    detailService.insert(detailEntity);
                                    totalDetail++;
                                }
                            }
                        }
                    }
                }
            }
        }
        Long b = System.currentTimeMillis();
        logger.info("[AgentMoneyTask]耗时：" + (b - a) / 60000 + "分钟");
        logger.info("[AgentMoneyTask]共计修改任务数：" + totalTask);
        logger.info("[AgentMoneyTask]共计修改订单数：" + totalMission);
        logger.info("[AgentMoneyTask]共计添加明细数：" + totalDetail);
    }
}
