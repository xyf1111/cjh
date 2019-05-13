package io.chaoshua.modules.background.service.impl;

import io.chaoshua.common.utils.*;
import io.chaoshua.modules.agent.entity.AgentSellerMoneyEntity;
import io.chaoshua.modules.agent.service.AgentSellerMoneyService;
import io.chaoshua.modules.app.form.TaskForm;
import io.chaoshua.modules.app.vo.TaskDetailVo;
import io.chaoshua.modules.app.vo.TaskVo;
import io.chaoshua.modules.background.dao.DetailDao;
import io.chaoshua.modules.background.entity.*;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.entity.task.KeyWordEntity;
import io.chaoshua.modules.background.service.*;
import io.chaoshua.modules.background.service.agent.AgentMoneyService;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.modules.background.service.mission.MissionService;
import io.chaoshua.modules.background.service.task.KeyWordService;
import io.chaoshua.modules.background.util.ComparePrice;
import io.chaoshua.modules.sys.entity.SysUserEntity;
import io.chaoshua.modules.sys.entity.SysUserRoleEntity;
import io.chaoshua.modules.sys.service.SysUserRoleService;
import javassist.compiler.ast.Keyword;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.chaoshua.modules.background.dao.task.TaskDao;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.chaoshua.modules.background.service.task.TaskService;
import org.springframework.transaction.annotation.Transactional;


@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao, TaskEntity> implements TaskService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShopService shopService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private IntervalStepService intervalStepService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private DetailDao detailDao;
    @Autowired
    private UserService userService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private InvitationStepService invitationStepService;
    @Autowired
    private MissionDetailService missionDetailService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private KeyWordService keyWordService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AgentSellerMoneyService agentSellerMoneyService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long sysUserId = ShiroUtils.getUserId();
        SysUserRoleEntity sysUserRoleEntity = sysUserRoleService.selectOne(new EntityWrapper<SysUserRoleEntity>().eq("user_id", sysUserId));
        if (sysUserRoleEntity != null) {
            if (sysUserRoleEntity.getRoleId() == 2) {
                params.put("inChargeId", ShiroUtils.getUserId());
            }
        }
        params.put("order1", "1");
        Page<TaskEntity> page = new Query<TaskEntity>(params).getPage();
        List<TaskEntity> list = baseMapper.getList(page, params);
        for (TaskEntity taskEntity : list) {
            taskEntity.setAgentName(sellerService.getAgentNameBySellerId(taskEntity.getSellerId()));
            taskEntity.setSysMenuRole(1);
        }
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public PageUtils querySellerPage(Map<String, Object> params) {
        Page<TaskEntity> page = new Query<TaskEntity>(params).getPage();
        List<TaskEntity> list = baseMapper.getList(page, params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public R updateByTaskId(TaskEntity task) {
        TaskEntity taskEntity = baseMapper.selectById(task.getId());
        if (taskEntity.getRole() == 2) {
            return R.error("该任务已审核通过，请勿重复操作");
        } else if (taskEntity.getRole() == 3) {
            return R.error("该任务已拒绝，请勿重复操作");
        }
        SellerEntity sellerEntity = sellerService.selectById(taskEntity.getSellerId());
        task.setPublishTime(new Date(System.currentTimeMillis()));
        //修改状态
        baseMapper.updateById(task);
        if (task.getRole() == 2) {
            /**************生成订单开始****************/
            //获取全局配置信息
            InvitationStepEntity invitationStepEntity = invitationStepService.selectById(1);
            this.batchCreateMission(task, sellerEntity, invitationStepEntity.getIsAuth());
            /**************生成订单结束****************/
        } else if (task.getRole() == 3) {
            //这里已经是本佣模式
            BigDecimal money = taskEntity.getSellerPay().add(taskEntity.getViewPay());
            SellerEntity sellerEntity1 = new SellerEntity();
            sellerEntity1.setId(sellerEntity.getId());
            sellerEntity1.setBalance(sellerEntity.getBalance().add(money));
            if (sellerService.updateById(sellerEntity1)) {
                DetailEntity detailEntity = new DetailEntity();
                detailEntity.setUserId(sellerEntity.getId());
                detailEntity.setUserName(sellerEntity.getContact());
                detailEntity.setMobile(sellerEntity.getMobile());
                detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
                detailEntity.setIsSeller(1);
                detailEntity.setType(3);
                detailEntity.setAmount(money);
                detailEntity.setBalance(sellerEntity.getBalance());
                detailEntity.setCode(taskEntity.getId().toString());
                detailEntity.setNote("备注：" + task.getFeedback() + "---商户发布任务被拒绝退回本佣金：" + money + "元");
                detailService.insert(detailEntity);
            }
        }
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchPass(List<Long> taskIds, Long inChargeId, String platformName) {
        // 商家信息（商家ID，商家）
        Map<Long, SellerEntity> sellerMap = new HashedMap();
        //获取全局配置信息
        InvitationStepEntity invitationStepEntity = invitationStepService.selectById(1);

        for (Long taskId : taskIds) {
            TaskEntity task = baseMapper.selectById(taskId);
            // 只处理未审核的任务
            if (task != null && task.getRole() == 1) {
                // 更新任务
                task.setInChargeId(inChargeId);
                task.setPlatformName(platformName);
                task.setRole(2);
                task.setPublishTime(new Date());
                baseMapper.updateById(task);

                // 发布任务商家信息
                SellerEntity seller;
                if (sellerMap.containsKey(task.getSellerId())) {
                    seller = sellerMap.get(task.getSellerId());
                } else {
                    seller = sellerService.selectById(task.getSellerId());
                    sellerMap.put(seller.getId(), seller);
                }

                // 生成订单
                this.batchCreateMission(task, seller, invitationStepEntity.getIsAuth());
            }
        }
    }

    /**
     * 批量生成订单
     *
     * @param task   任务
     * @param seller 商家
     * @param isAuth 是否需要认证（1：是，0：否）
     */
    @Transactional(rollbackFor = Exception.class)
    private void batchCreateMission(TaskEntity task, SellerEntity seller, Integer isAuth) {
        Integer count = task.getAmount();
        Long taskId = task.getId();
        List<KeyWordEntity> keywordList = keyWordService.findByTaskId(taskId);
        //初始化关键词list
        for(KeyWordEntity kwe: keywordList) {
            kwe.setUsed(false);
        }
        Date begin = task.getBeginTime();
        if (begin == null) {
            begin = new Date();
        }
        int i = 0;
        while (i < count) {
            MissionEntity missionEntity = new MissionEntity();
            missionEntity.setTaskId(task.getId());
            missionEntity.setSellerId(seller.getId());
            missionEntity.setSellerName(seller.getContact());
            // 动态多个关键字平均分配算法改造
            // missionEntity.setKeyWord(task.getKeyword());
            boolean isAllUsed = false;
            for (KeyWordEntity kwe: keywordList) {
                if(!kwe.getUsed() && kwe.getAmount() > 0 ){
                    missionEntity.setKeyWord(kwe.getKeyWord());
                    kwe.setUsed(true);
                    kwe.setAmount(kwe.getAmount() - 1);
                    break;
                }
            }
            if(missionEntity.getKeyWord() == null) {
                isAllUsed = true;
            }
            if(isAllUsed) {
                boolean isFirst = true; //用一次 设置上就行
                for (KeyWordEntity kwe: keywordList) {
                    if(kwe.getAmount() > 0 && isFirst){
                        missionEntity.setKeyWord(kwe.getKeyWord());
                        kwe.setAmount(kwe.getAmount() - 1);
                        kwe.setUsed(true);
                        isFirst = false;
                    } else if (kwe.getAmount() > 0){
                        kwe.setUsed(false);
                    }
                }
            }

            missionEntity.setInChargeId(task.getInChargeId());
            missionEntity.setPlatformName(task.getPlatformName());
            missionEntity.setShopCategoryId(task.getShopCategoryId());
            missionEntity.setShopCategory(task.getShopCategory());
            //判断是否需要认证（1：是，0：否）
            if (isAuth == 1) {
                missionEntity.setStatus(1);
            } else if (isAuth == 2) {
                missionEntity.setStatus(2);
            }
            missionEntity.setTaskStyle(task.getTaskStyle());
            missionEntity.setShopName(task.getShopName());
            missionEntity.setShopId(task.getShopId());
            missionEntity.setPrice(task.getPrice());
            missionEntity.setWeight(task.getWeight());
            missionEntity.setMissionCode(GenerateCodeUtils.getMissionCode(task.getId()));
            missionEntity.setMissionTime(DateUtils.addDateMinutes(begin, task.getInterval() * i));
            missionEntity.setCreateTime(new Date(System.currentTimeMillis()));
            if (missionService.insert(missionEntity)) {
                //生成订单的详细信息
                MissionDetailEntity missionDetailEntity = new MissionDetailEntity();
                missionDetailEntity.setMissionId(missionEntity.getId());
                missionDetailEntity.setPublishTime(new Date(System.currentTimeMillis())); //发布时间即是审核通过时间
                missionDetailEntity.setUrl(task.getUrl());
                missionDetailEntity.setStyle(task.getStyle());
                missionDetailEntity.setOrder(task.getOrder());
                //价格区间
                missionDetailEntity.setPriceFrom(task.getPriceFrom());
                missionDetailEntity.setPriceTo(task.getPriceTo());
                // 商品主图二三
                missionDetailEntity.setImg2(task.getImg2());
                missionDetailEntity.setImg3(task.getImg3());
                missionDetailEntity.setIntervalBegin(task.getIntervalBegin());
                missionDetailEntity.setIntervalEnd(task.getIntervalEnd());
                missionDetailEntity.setImg(task.getImg());
                // 其他任务要求
                missionDetailEntity.setOtherNote(task.getOtherNote());
                missionDetailEntity.setNote(task.getNote());
                missionDetailEntity.setUserPay(task.getUserPay());
                missionDetailEntity.setAgentMoney(task.getAgentMoney());
                missionDetailEntity.setBeginTime(begin);
                if (missionDetailService.insert(missionDetailEntity)) {
                    i++;
                }
            }
        }
    }

    @Override
    @Transactional
    public R deleteTaskById(Long id) {
        TaskEntity taskEntity = baseMapper.selectById(id);
        if (taskEntity.getRemain() == 0) {
            return R.error(500, "任务已经被接完，无法撤销!");
        }
        List<MissionEntity> list = missionService.selectList(new EntityWrapper<MissionEntity>().eq("task_id", id).eq("role", 0));
        for (MissionEntity missionEntity : list) {//删除订单
            missionService.deleteById(missionEntity.getId());
            missionDetailService.delete(new EntityWrapper<MissionDetailEntity>().eq("mission_id", missionEntity.getId()));
        }
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        if (sysUserEntity != null) {
            logger.warn("任务编号：" + taskEntity.getId() + "被" + sysUserEntity.getUsername() + "(" + sysUserEntity.getUserId() + ")撤回！");
        } else {
            logger.warn("商家" + taskEntity.getSellerName() + "(" + taskEntity.getSellerId() + ")自己撤回任务：" + id);
        }
        TaskEntity entity = new TaskEntity();
        entity.setRemain(0);
        // 被移除的任务数量
        int removeCount = list.size();
        entity.setRemove(removeCount);
        entity.setId(taskEntity.getId());
        baseMapper.updateById(entity);//修改任务剩余数
        SellerEntity seller = sellerService.selectById(taskEntity.getSellerId());
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setId(taskEntity.getSellerId());
        IntervalStepEntity intervalStepEntity = intervalStepService.selectById(1);
        BigDecimal amount = null;
        if (seller.getIsSeller() == 3) {
            AgentSellerMoneyEntity agentSellerMoneyEntity = agentSellerMoneyService.selectOne(new EntityWrapper<AgentSellerMoneyEntity>().eq("seller_id", sellerEntity.getId()));
            //代理下的商家每单精刷单商家金额(佣金)
            amount = ComparePrice.balanceMoney1(taskEntity.getPrice(), seller.getBenefit(), agentSellerMoneyEntity);
        } else {
            //平台商家每单精刷单商家金额(佣金)
            amount = ComparePrice.getSellerPayCommissionMoney(taskEntity.getPrice(), seller.getBenefit(), intervalStepEntity);
        }
        /*if (taskEntity.getTaskStyle().equals(1)) {
            amount = amount.add(intervalStepEntity.getAppointOneSeller());
        } else if (taskEntity.getTaskStyle().equals(2)) {
            amount = amount.add(intervalStepEntity.getAppointTwoSeller());
        }*/
        // 所有佣金
        amount = amount.multiply(new BigDecimal(removeCount));
        // 所有本金 (本佣模式)
        BigDecimal prices = taskEntity.getPrice().multiply(new BigDecimal(removeCount));
        sellerEntity.setBalance(seller.getBalance().add(amount.add(prices)));
        sellerService.updateById(sellerEntity);//返还撤销金额给商家
        DetailEntity detailEntity = new DetailEntity();
        detailEntity.setUserId(taskEntity.getSellerId());
        detailEntity.setUserName(seller.getContact());
        detailEntity.setMobile(seller.getMobile());
        detailEntity.setIsSeller(1);
        detailEntity.setAmount(amount.add(prices));
        detailEntity.setBalance(amount.add(prices).add(seller.getBalance()));
        detailEntity.setType(7);
        detailEntity.setCode(taskEntity.getId().toString());
        detailEntity.setNote("任务编号为" + taskEntity.getId() + "被撤销返还本金(" + prices + ")+佣金(" + amount + ")共" + amount.add(prices) + "元");
        detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
        detailService.insert(detailEntity);//生成撤销剩余订单商家金额流水
        return R.ok();
    }

    /***********************************app接口开始*************************************************************/
    //本拥模式修改
    @Transactional
    public R insertTask(TaskEntity taskEntity) {
        SellerEntity sellerEntity = sellerService.selectById(taskEntity.getSellerId());
        ShopEntity shopEntity = shopService.selectById(taskEntity.getShopId());
        if (shopEntity.getCategoryId() == null) {
            return R.error(500, "请先修改店铺类目!");
        }
        BigDecimal balance = sellerEntity.getBalance();
        IntervalStepEntity intervalStepEntity = intervalStepService.selectById(1);
        if (sellerEntity.getStatus() == 2) {
            if (sellerEntity.getPublishable() == 1) {
                if (shopEntity.getRole() != 3) {
                    return R.error(500, "您的店铺没有发布权限，请联系客服!");
                }
                if (shopEntity.getStatus() == 1) {
                    if (taskEntity.getPrice().compareTo(sellerEntity.getLimit()) > 0) {
                        return R.error(500, "您已超过限制金额无法发布！");
                    }
                    if (taskEntity.getAmount() > shopEntity.getTotal() - shopEntity.getIsPublish()) {
                        return R.error(500, "您已超过店铺当天剩余数量");
                    }
                    if (balance.compareTo(new BigDecimal(0)) >= 0) {
                        BigDecimal mon = ComparePrice.getSellerPayCommissionMoney(taskEntity.getPrice(), sellerEntity.getBenefit(), intervalStepEntity);
                        // 获取商家精刷单所支付总佣金
                        BigDecimal money = mon.multiply(new BigDecimal(taskEntity.getAmount()));
                        // 获取总的本金
                        BigDecimal totalPrice = taskEntity.getPrice().multiply((new BigDecimal(taskEntity.getAmount())));
                        // 掌中宝只有精刷单viewPay=0
                        BigDecimal viewPay = ComparePrice.getViewPay(taskEntity.getTaskStyle(), taskEntity.getAmount(), intervalStepEntity);//获取商家所标签任务支付总金额
                        //BigDecimal money1 = null;
                        BigDecimal agentMoney = null;
                        Integer isSeller = sellerEntity.getIsSeller();
                        // 掌中宝没有代理商（上级类型（0：平台，1：商家，2：刷手,3:代理商））
                        /*if (isSeller == 3) {
                            AgentSellerMoneyEntity agentSellerMoneyEntity = agentSellerMoneyService.selectOne(new EntityWrapper<AgentSellerMoneyEntity>().eq("seller_id", sellerEntity.getId()));
                            AgentMoneyEntity agentMoneyEntity = agentMoneyService.selectOne(new EntityWrapper<AgentMoneyEntity>().eq("agent_id", sellerEntity.getPid()));
                            money1 = ComparePrice.balanceMoney1(taskEntity.getPrice(), sellerEntity.getBenefit(), agentSellerMoneyEntity).multiply(new BigDecimal(taskEntity.getAmount()));//获取代理商下的商家精刷单所支付总金额
                            agentMoney = ComparePrice.getAgentMoney(taskEntity.getPrice(), agentSellerMoneyEntity, agentMoneyEntity);
                        }
                        if (viewPay == null) {
                            viewPay = new BigDecimal(0);
                        }*/
                        /*BigDecimal sumMoney = null;
                        if (isSeller == 3) {
                            sumMoney = money1.add(viewPay);
                        } else {
                            sumMoney = money.add(viewPay);
                        }*/
                        BigDecimal sumMoney = money.add(totalPrice); //本佣模式 (佣金 + 本金)
                        if (balance.compareTo(sumMoney) >= 0) {
                            BigDecimal price = ComparePrice.getUserPayCommissionMoney(taskEntity.getPrice(), taskEntity.getTaskStyle(), intervalStepEntity);//获取刷手每单所得佣金
                            taskEntity.setShopCategory(shopEntity.getCategory());
                            taskEntity.setUserPay(price);
                            taskEntity.setSellerPay(money.add(totalPrice)); //改成总金额
                            taskEntity.setAgentMoney(agentMoney);  //没用
                            taskEntity.setViewPay(viewPay); //没用
                            taskEntity.setCreateTime(new Date());
                            taskEntity.setRemain(taskEntity.getAmount());
                            taskEntity.setSellerName(sellerEntity.getContact());
                            String[] categoryIds = shopEntity.getCategoryId().split(",");
                            Long categoryId = Long.parseLong(categoryIds[categoryIds.length - 1]);
                            taskEntity.setShopCategoryId(categoryId);
                            if (taskEntity.getBeginTime() == null) {
                                taskEntity.setBeginTime(new Date());
                            }
                            if (baseMapper.insert(taskEntity) > 0) {
                                sellerEntity.setBalance(sellerEntity.getBalance().subtract(sumMoney)); //本佣模式 (佣金 + 本金)
                                sellerService.updateById(sellerEntity);
                                shopEntity.setIsPublish(shopEntity.getIsPublish() + taskEntity.getAmount());
                                shopService.updateById(shopEntity);
                                DetailEntity detailEntity = new DetailEntity();
                                detailEntity.setIsSeller(1);
                                detailEntity.setUserId(sellerEntity.getId());
                                detailEntity.setUserName(sellerEntity.getContact());
                                detailEntity.setMobile(sellerEntity.getMobile());
                                // 佣金流动类型1商家充值，
                                // 2刷手接单获得不可提现佣金，
                                // 3商家发布任务被拒绝返回的佣金，
                                // 4指定评价的佣金，
                                // 5刷手订单被审核通过获得的佣金，
                                // 6刷手提现的佣金，
                                // 7订单被撤销，返回给商家的佣金或是扣除刷手的佣金，
                                // 8下家完成任务的佣金,
                                // 9后台修改，
                                // 10、指定追评佣金，
                                // 11：代理商充值
                                // 12，下级商家完成商家获取金额，
                                // 13：下级商家完成刷手获取金额
                                // 14：代理下级任务完成获取的金额
                                // 15：代理商给商家充值扣除余额
                                detailEntity.setType(2);
                                detailEntity.setAmount(sumMoney.multiply(new BigDecimal(-1)));
                                detailEntity.setNote("商户发布任务扣除本金(" + totalPrice + ")+佣金(" + money + ")共" + sumMoney + "元");
                                detailEntity.setCode(taskEntity.getId().toString());
                                detailEntity.setBalance(sellerEntity.getBalance());
                                detailEntity.setCreateTime(new Date());
                                if (detailDao.insert(detailEntity) > 0) {//生成商家发布任务金额扣除流水
                                    //搜索的关键词入库处理
                                    keyWordService.saveByTaskId(taskEntity.getId(), taskEntity.getKeyword(), taskEntity.getKeywordCounts());
                                    return R.ok();
                                } else {
                                    return R.error();
                                }
                            } else {
                                return R.error(500, "创建任务失败!");
                            }
                        } else {
                            return R.error(500, "余额不足!");
                        }
                    } else {
                        return R.error(500, "余额不足!");
                    }
                } else {
                    return R.error(500, "店铺被禁用无法发单!");
                }
            } else {
                return R.error(500, "您没有发单的权限，请联系客服!");
            }
        } else {
            return R.error(500, "您是未审核或未通过用户，请联系客服!");
        }
    }


    @Override
    @Transactional
    public R insertBatchTask(List<String[]> list, Long sellerId, String url) {
        // 待保存任务
        List<TaskEntity> taskList = new ArrayList<>();

        SellerEntity sellerEntity = sellerService.selectById(sellerId);
        IntervalStepEntity intervalStepEntity = intervalStepService.selectById(1);
        if (sellerEntity.getStatus() == 1) {
            return R.error(500, "您还是未审核商家，请联系客服!");
        }
        if (sellerEntity.getStatus() == 3) {
            return R.error(500, "您审核商家被拒，请联系客服!");
        }
        if (sellerEntity.getBalance().compareTo(new BigDecimal(0)) <= 0) {
            return R.error(500, "余额不足，无法发布任务!");
        }
        BigDecimal sumPrice = new BigDecimal(0);
        // 精刷单任务要求
        String note = "1:按指定时间进去做任务，切记一定要按要求浏览，尽量用4G；\n" +
                "2:用关键词货比一到四家(找%s元价格左右的店铺)----加购两家----提交订单;\n" +
                "3:进店禁止卡价和搜索店铺名进店----先浏览三个以上副宝贝-----再浏览主宝贝（主宝贝浏览时间要3分钟以上）-----看详情看评价---找客服聊天（聊天3句以上）---收藏店铺---加入购物车----从购物车下单---付款---（浏览时间必须在10分钟以上）;\n" +
                "4:用真实姓名电话地址需签收包裹，一定要等到收到包裹以后再确认收货或等系统自动确认收货。\n" +
                "注意：用支付宝余额或银行卡付款，不要用花呗，淘宝客淘金币和信用卡付款。";
        // 标签单任务要求
        String note1 = "切记一定要按要求浏览，用4G;\n" +
                "第一天：按指定时间进去做任务\n" +
                "1. 用第一天的关键词搜索货比一到四家(找%s元价格左右的店铺)----加购两家-----提交订单-----不进主店\n" +
                "第二天：以第一天指定的时间做任务（如果第一天任务时间是下午三点，那第二天也是下午三点\n" +
                "1，用第二天的关键词货比三家-----进其他店浏览(找%s元价格左右的店铺)----看详情看评价\n" +
                "2，进店禁止卡价和搜索店铺名进店，先浏览三个以上副宝贝-----再浏览主宝贝（主宝贝浏览时间要3分钟以上）-----看详情看评价---找客服聊天（聊天3句以上）---收藏店铺---加入购物车----从购物车下单---付款---（浏览时间必须在10分钟以上)\n" +
                "3. 用真实姓名电话地址需签收包裹，一定要等到收到包裹以后再确认收货或等系统自动确认收货\n" +
                "注意：用支付宝余额或者银行卡付款，不要用（花呗，淘宝客淘金币和信用卡）";
        //获取excel里面的内容
        for (String[] array : list) {
            if (StringUtils.isEmpty(array[0])) {
                continue;
            }
            BigDecimal sumPrice1 = new BigDecimal(0); //佣金
            BigDecimal sumPrice2 = new BigDecimal(0); //本金
            BigDecimal agentMoney = new BigDecimal(0);
            // 查询该商家是否有店铺名称对应的店铺
            ShopEntity shopEntity = shopService.selectOne(new EntityWrapper<ShopEntity>().eq("name", array[6].trim()).eq("seller_id", sellerId).eq("role", 3));
            if (shopEntity != null) {
                if (sellerEntity.getLimit().compareTo(new BigDecimal(array[10])) < 0) {
                    return R.error(500, "第" + array[6] + "金额超出商家金额限制");
                }
                if (shopEntity.getStatus() == 2) {
                    return R.error(500, "您的店铺" + array[6] + "被禁用，请联系客服!");
                } else {
                    Integer taskStyle = null;
                    /*if (sellerEntity.getIsSeller() == 3) {
                        AgentSellerMoneyEntity agentSellerMoneyEntity = agentSellerMoneyService.selectOne(new EntityWrapper<AgentSellerMoneyEntity>().eq("seller_id", sellerEntity.getId()));
                        AgentMoneyEntity agentMoneyEntity = agentMoneyService.selectOne(new EntityWrapper<AgentMoneyEntity>().eq("agent_id", sellerEntity.getPid()));
                        sumPrice1 = ComparePrice.balanceMoney1(new BigDecimal(array[10]), sellerEntity.getBenefit(), agentSellerMoneyEntity).multiply(new BigDecimal(Integer.parseInt(array[9])));
                        // 代理商下的商家精刷单价格
                        sumPrice = sumPrice.add(sumPrice1);
                        // 代理下商家所获取每单（精刷单）利润
                        agentMoney = ComparePrice.getAgentMoney(new BigDecimal(array[10]), agentSellerMoneyEntity, agentMoneyEntity);
                    } else {
                        sumPrice1 = sumPrice1.add(ComparePrice.getSellerPayCommissionMoney(new BigDecimal(array[10]), sellerEntity.getBenefit(), intervalStepEntity)).multiply(new BigDecimal(Integer.parseInt(array[9])));
                        // 平台下的商家精刷单价格
                        sumPrice = sumPrice.add(sumPrice1);
                    }*/
                    //算出佣金
                    sumPrice1 = sumPrice1.add((ComparePrice.getSellerPayCommissionMoney(new BigDecimal(array[10]), sellerEntity.getBenefit(), intervalStepEntity)).multiply(new BigDecimal(array[9])));
                    //算出本金
                    sumPrice2 = sumPrice2.add((new BigDecimal(array[10])).multiply(new BigDecimal(array[9])));
                    // 平台下的商家精刷单价格
                    // 本佣模式
                    sumPrice = sumPrice.add(sumPrice1).add(sumPrice2);
                    /*if (array[7].equals("标签2天")) {
                        taskStyle = 1;
                        sumPrice1 = sumPrice1.add(ComparePrice.getViewPay(taskStyle, Integer.parseInt(array[9]), intervalStepEntity));
                        sumPrice = sumPrice.add(sumPrice1);
                    } else if (array[7].equals("标签3天")) {
                        taskStyle = 2;
                        sumPrice1 = sumPrice1.add(ComparePrice.getViewPay(taskStyle, Integer.parseInt(array[9]), intervalStepEntity));
                        sumPrice = sumPrice.add(sumPrice1);
                    }*/
                }
            } else {
                return R.error(500, "您添加的" + array[6] + "没有相关店铺!");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            StrBuilder year = new StrBuilder(sdf.format(date));
            year.append("-").append(array[0]).append("-").append(array[1]).append(" ").append(array[2]).append(":").append(array[3]);
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setBeginTime(DateUtils.stringToDate(year.toString(), "yyyy-MM-dd HH:mm"));
            taskEntity.setInterval(Integer.parseInt(array[4]));
            taskEntity.setUrl(array[5]);
            taskEntity.setShopName(array[6]);
            if (array[7].equals("精刷单")) {
                taskEntity.setTaskStyle(0);
                taskEntity.setNote(String.format(note, array[10]));
            } else if (array[7].equals("标签2天")) {
                taskEntity.setTaskStyle(1);
                taskEntity.setNote(String.format(note1, array[10], array[10]));
            } else if (array[7].equals("标签3天")) {
                taskEntity.setTaskStyle(2);
                taskEntity.setNote(String.format(note1, array[10], array[10]));
            }
            taskEntity.setKeyword(array[8]);
            taskEntity.setAmount(Integer.parseInt(array[9]));
            taskEntity.setPrice(new BigDecimal(array[10]));
            taskEntity.setOtherNote(array[11]);
            taskEntity.setWeight(new BigDecimal(array[12]));
            CategoryEntity categoryEntity = categoryService.selectOne(new EntityWrapper<CategoryEntity>().eq("name", shopEntity.getCategory()));
            if (categoryEntity != null) {
                taskEntity.setShopCategory(shopEntity.getCategory());
                taskEntity.setShopCategoryId(categoryEntity.getId());
            } else {
                return R.error(500, "没有相关类目");
            }
            taskEntity.setSellerName(sellerEntity.getContact());
            taskEntity.setSellerId(sellerId);
            taskEntity.setImg(url);
            // 本佣模式
            taskEntity.setSellerPay(sumPrice1.add(sumPrice2));
            taskEntity.setAgentMoney(agentMoney);
            taskEntity.setRemain(Integer.parseInt(array[9]));
            taskEntity.setShopId(shopEntity.getId());
            Integer taskStyle = 0;
            if (array[7].equals("标签2天")) {
                taskStyle = 1;
                taskEntity.setViewPay(ComparePrice.getViewPay(taskStyle, Integer.parseInt(array[9]), intervalStepEntity));
                taskEntity.setUserPay(ComparePrice.getUserPayCommissionMoney(new BigDecimal(array[10]), 1, intervalStepEntity));
            } else if (array[7].equals("标签3天")) {
                taskStyle = 2;
                taskEntity.setViewPay(ComparePrice.getViewPay(taskStyle, Integer.parseInt(array[9]), intervalStepEntity));
                taskEntity.setUserPay(ComparePrice.getUserPayCommissionMoney(new BigDecimal(array[10]), 2, intervalStepEntity));
            } else if (array[7].equals("精刷单")) {
                taskEntity.setUserPay(ComparePrice.getUserPayCommissionMoney(new BigDecimal(array[10]), 0, intervalStepEntity));
            }
            taskEntity.setCreateTime(new Date());
            taskList.add(taskEntity);
        }
        if (sellerEntity.getBalance().compareTo(sumPrice) < 0) {
            //判断平台下的商家余额是否足够发布任务
            return R.error(500, "余额不足!至少需要：" + sumPrice + "元");
        }
        // 批量导入任务
        this.insertBatchTask(taskList, sellerEntity);
        return R.ok().put("msg", String.format("导入成功"));
    }

    /**
     * 保存任务
     *
     * @param taskList 任务列表
     * @param seller   商家
     */
    private void insertBatchTask(List<TaskEntity> taskList, SellerEntity seller) {
        for (TaskEntity task : taskList) {
            baseMapper.insert(task);
            // 扣除商家金额
            seller.setBalance(seller.getBalance().subtract(task.getSellerPay()));
            // 记录流水信息
            DetailEntity detailEntity = new DetailEntity();
            detailEntity.setIsSeller(1);
            detailEntity.setUserId(seller.getId());
            detailEntity.setType(2);
            detailEntity.setUserName(seller.getContact());
            detailEntity.setMobile(seller.getMobile());
            detailEntity.setCode(task.getId().toString());
            detailEntity.setBalance(seller.getBalance());
            detailEntity.setNote("商户发布任务扣除本佣金" + task.getSellerPay() + "元");
            detailEntity.setAmount(new BigDecimal(0).subtract(task.getSellerPay()));
            detailEntity.setCreateTime(new Date());
            detailService.insert(detailEntity);
        }
        // 更新商家余额
        sellerService.updateById(seller);
    }


    /***********************************app开始**************************************************************/
    @Override
    public AppPage<TaskVo> getVoList(TaskForm taskForm, Long userId) {
        AppPage<TaskVo> appPage = null;
        UserEntity userEntity = userService.selectById(userId);
        Integer dayCount = missionService.selectCount(new EntityWrapper<MissionEntity>().eq("user_id", userId).ge("create_time", DateUtils.getDay()).lt("role", 3).eq("is_annul", 1));//日单数量
        Integer monthCount = missionService.selectCount(new EntityWrapper<MissionEntity>().eq("user_id", userId).ge("create_time", DateUtils.getFirstDayOfMonth()).lt("role", 3).eq("is_annul", 1));//月单数量
        if (userEntity.getStatus() != 2) {
            return AppPage.error(500, "您没有权限接单，请联系客服!");
        }
        if (userEntity.getRole() == 2) {//黑名单
            return AppPage.error(100, "");
        }
        if (dayCount >= userEntity.getDayLimit()) {
            return AppPage.error(500, "您已超出日单限制");
        }
        if (monthCount >= userEntity.getMonthLimit()) {
            return AppPage.error(500, "您已超出月单限制");
        }
        if (userEntity.getRole() == 1) {
            Map<String, Object> params = TaskForm.toMap(taskForm);
            Page<TaskEntity> page = new Query<TaskEntity>(params).getPage();
            params.put("status", 2);
            // role = 2，任务已审核
            params.put("role", 2);
            params.put("userId", userId);
            // 用户接单金额限制
            params.put("userPriceLimit", userEntity.getLimit());
            // System.out.println(page.getTotal());
            List<TaskEntity> taskEntityList = baseMapper.getTaskListFromApp(page, params);
            List<TaskVo> voList = new ArrayList<>();
            for (TaskEntity taskEntity : taskEntityList) {
                //可用关键字查询处理
                Long taskId = taskEntity.getId();
                Map<String, Object> m = new HashMap<>();
                m.put("taskId", taskId);
                m.put("status", "2");
                m.put("isAnnul", "1");
                MissionEntity me = missionService.selectOne(new EntityWrapper<MissionEntity>()
                    .eq("task_id", taskId).eq("status", "2")
                    .eq("is_annul", "1").isNull("order_time")
                    .orderBy("mission_time", true).last(" limit 0, 1"));
                if(me != null && me.getKeyWord() != null ) {
                    taskEntity.setKeyword(me.getKeyWord());
                } else {
                    String[] k = taskEntity.getKeyword().split(";");
                    if(k.length > 0) {
                        taskEntity.setKeyword(k[0]);
                    }
                }
                voList.add(TaskVo.toVo(taskEntity));
            }
            appPage = AppPage.success();
            appPage.setData(voList);
            appPage.setTotal(page.getTotal());
            appPage.setPages(page.getPages());
            appPage.setCurrent(page.getCurrent());
        }
        return appPage;
    }

    @Override
    public TaskDetailVo getDetail(Long taskId) {
        TaskEntity taskEntity = baseMapper.getDetail(taskId);
        TaskDetailVo taskDetailVo = TaskDetailVo.toVo(taskEntity);
        return taskDetailVo;
    }

    /***********************************app结束**************************************************************/

    @Override
    public PageUtils getListByPid(Map<String, Object> params) {
        Page<TaskEntity> page = new Query<TaskEntity>(params).getPage();
        page.setRecords(baseMapper.getList(page, params));
        return new PageUtils(page);
    }
}
