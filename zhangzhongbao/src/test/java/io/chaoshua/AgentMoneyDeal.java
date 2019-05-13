package io.chaoshua;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;
import io.chaoshua.modules.agent.service.AgentSellerMoneyService;
import io.chaoshua.modules.background.entity.DetailEntity;
import io.chaoshua.modules.background.entity.IntervalStepEntity;
import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.background.entity.agent.AgentEntity;
import io.chaoshua.modules.background.entity.agent.AgentMoneyEntity;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.chaoshua.modules.background.enums.MissionStatusEnum;
import io.chaoshua.modules.background.service.DetailService;
import io.chaoshua.modules.background.service.IntervalStepService;
import io.chaoshua.modules.background.service.SellerService;
import io.chaoshua.modules.background.service.agent.AgentMoneyService;
import io.chaoshua.modules.background.service.agent.AgentService;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.modules.background.service.mission.MissionService;
import io.chaoshua.modules.background.service.task.TaskService;
import io.chaoshua.modules.background.util.ComparePrice;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代理商金额处理
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-28 9:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RenrenApplication.class})
public class AgentMoneyDeal {

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
    @Autowired
    private IntervalStepService intervalStepService;

    @Test
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
        System.out.println("耗时：" + (b - a) / 60000 + "分钟");
        System.out.println("共计修改任务数：" + totalTask);
        System.out.println("共计修改订单数：" + totalMission);
        System.out.println("共计添加明细数：" + totalDetail);
    }

    @Test
    public void money() {
        BigDecimal price = new BigDecimal(999.22);
        IntervalStepEntity intervalStepEntity = intervalStepService.selectById(1);
        BigDecimal money = ComparePrice.getSellerPayCommissionMoney(price, new BigDecimal(0), intervalStepEntity);

        //
        AgentSellerMoneyEntity agentSellerMoney = agentSellerMoneyService.selectOne(new EntityWrapper<AgentSellerMoneyEntity>()
                .eq("seller_id", "129"));
        // BigDecimal money1 = ComparePrice.balanceMoney1(price,new BigDecimal(0),agentSellerMoney);
        System.out.println(money);
        // System.out.println(money1);
    }

    @Test
    public void task() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{"3", "30", "10", "10", "30", "https://www.taobao.com/",
                "中原之雪制冷设备厂", "精刷单", "跑鞋", "1", "999.01", "备注"});

        taskService.insertBatchTask(list, 129L, "");
    }
}
