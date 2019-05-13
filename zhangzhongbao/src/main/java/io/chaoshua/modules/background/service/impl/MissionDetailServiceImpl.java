package io.chaoshua.modules.background.service.impl;

import io.chaoshua.common.utils.*;
import io.chaoshua.modules.agent.service.AgentSellerMoneyService;
import io.chaoshua.modules.app.form.AddCommentForm;
import io.chaoshua.modules.app.form.AuthPictureForm;
import io.chaoshua.modules.app.form.MissionRecGoodForm;
import io.chaoshua.modules.background.entity.*;
import io.chaoshua.modules.background.entity.agent.AgentEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.chaoshua.modules.background.service.*;
import io.chaoshua.modules.background.service.agent.AgentService;
import io.chaoshua.modules.background.service.mission.MissionService;
import io.chaoshua.modules.background.service.task.TaskService;
import io.chaoshua.modules.background.util.ComparePrice;
import io.chaoshua.modules.weixin.service.SendInfoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.chaoshua.modules.background.dao.mission.MissionDetailDao;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import org.springframework.transaction.annotation.Transactional;


@Service("missionDetailService")
public class MissionDetailServiceImpl extends ServiceImpl<MissionDetailDao, MissionDetailEntity> implements MissionDetailService {

    @Autowired
    private IntervalStepService intervalStepService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private UserService userService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentSellerMoneyService agentSellerMoneyService;
    @Autowired
    private InvitationStepService invitationStepService;
    @Autowired
    private SendInfoController sendInfoController;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MissionDetailEntity> page = this.selectPage(
                new Query<MissionDetailEntity>(params).getPage(),
                new EntityWrapper<MissionDetailEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public MissionDetailEntity getAuthByMissionId(Long missionId) {
        return baseMapper.getAuthByMissionId(missionId);
    }

    @Override
    public int updateAuthByMissionId(AuthPictureForm authPictureForm) {

        return baseMapper.updateAuthByMissionId(authPictureForm);
    }

    @Override
    @Transactional
    public R isComment(MissionDetailEntity missionDetailEntity) {
        Integer flag = missionDetailEntity.getFlag();//1:指定评价，2：指定追评
        MissionEntity missionEntity = missionService.selectById(missionDetailEntity.getMissionId());
        SellerEntity sellerEntity = sellerService.selectById(missionEntity.getSellerId());
        IntervalStepEntity intervalStepEntity = intervalStepService.selectById(1);
        if (flag == 1) {
            if (sellerEntity.getBalance().compareTo(intervalStepEntity.getCommentSeller()) < 0) {
                return R.error(500, "余额不足，请充值!");
            }
            missionDetailEntity.setCommentPay(intervalStepEntity.getCommentSeller());
            missionDetailEntity.setCommentUser(intervalStepEntity.getCommentUser());
            missionDetailEntity.setIsComment(2);
        } else if (flag == 2) {
            if (sellerEntity.getBalance().compareTo(intervalStepEntity.getAddCommentSeller()) < 0) {
                return R.error(500, "余额不足，请充值!");
            }
            missionDetailEntity.setAddComPay(intervalStepEntity.getAddCommentSeller());
            missionDetailEntity.setAddComUser(intervalStepEntity.getAddCommentUser());
            missionDetailEntity.setIsAddcom(2);
        }

        if (baseMapper.update(missionDetailEntity, new EntityWrapper<MissionDetailEntity>().eq("mission_id", missionDetailEntity.getMissionId())) > 0) {
            SellerEntity seller = new SellerEntity();
            if (flag == 1) {
                seller.setBalance(sellerEntity.getBalance().subtract(intervalStepEntity.getCommentSeller()));
            } else {
                seller.setBalance(sellerEntity.getBalance().subtract(intervalStepEntity.getAddCommentSeller()));
            }
            seller.setId(sellerEntity.getId());
            if (sellerService.updateById(seller)) {//修改商家余额
                DetailEntity detailEntity = new DetailEntity();
                detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
                detailEntity.setIsSeller(1);
                detailEntity.setUserId(sellerEntity.getId());
                detailEntity.setUserName(sellerEntity.getContact());
                detailEntity.setMobile(sellerEntity.getMobile());
                detailEntity.setType(4);
                if (flag == 1) {
                    detailEntity.setAmount(new BigDecimal(0).subtract(intervalStepEntity.getCommentSeller()));
                } else {
                    detailEntity.setAmount(new BigDecimal(0).subtract(intervalStepEntity.getAddCommentSeller()));
                }
                detailEntity.setBalance(seller.getBalance());
                detailEntity.setCode(missionEntity.getMissionCode());
                if (flag == 1) {
                    detailEntity.setNote("商家指定评价支付" + intervalStepEntity.getCommentSeller() + "元");
                } else {
                    detailEntity.setNote("商家指定追评支付" + intervalStepEntity.getAddCommentSeller() + "元");
                }
                if (detailService.insert(detailEntity)) {//新增商家指定评价流水
                    // 指定评价和指定追评加到可体现里面去
                    UserEntity userEntity = userService.selectById(missionEntity.getUserId());
                    UserEntity user = new UserEntity();
                    BigDecimal freeMoney;
                    if (flag == 1) {
                        freeMoney = userEntity.getFreeMoney().add(intervalStepEntity.getCommentUser());
                        user.setFreeMoney(freeMoney);
                    } else {
                        freeMoney = userEntity.getFreeMoney().add(intervalStepEntity.getAddCommentUser());
                        user.setFreeMoney(freeMoney);
                    }
                    user.setId(missionEntity.getUserId());
                    if (userService.updateById(user)) {//修改刷手不可用金额
                        DetailEntity detail = new DetailEntity();
                        detail.setIsSeller(2);
                        detail.setUserName(userEntity.getName());
                        detail.setMobile(userEntity.getMobile());
                        detail.setUserId(userEntity.getId());
                        detail.setType(4);
                        if (flag == 1) {//1:指定评价，2：指定追评
                            detail.setAmount(intervalStepEntity.getCommentUser());
                        } else {
                            detail.setAmount(intervalStepEntity.getAddCommentUser());
                        }
                        detail.setBeforeStock(userEntity.getStockMoney());
                        detail.setBeforeFree(userEntity.getFreeMoney());
                        detail.setFree(freeMoney);
                        detail.setCode(missionEntity.getMissionCode());
                        detail.setCreateTime(new Date(System.currentTimeMillis()));
                        if (flag == 1) {
                            detail.setNote("刷手执行指定评价获取" + intervalStepEntity.getCommentUser() + "元");
                        } else {
                            detail.setNote("刷手执行指定追评获取" + intervalStepEntity.getAddCommentUser() + "元");
                        }
                        if (detailService.insert(detail)) {//生成刷手指定评价流水
                            if (flag == 1) {
                                sendInfoController.sendInfo(userEntity.getOpenId(), "您好,您有订单已由商家指定评价,请在收货导入中查看指定评价内容", missionDetailEntity.getMissionCode(),
                                        DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN), "请在收货导入中查看指定评价内容");
                            } else {
                                sendInfoController.sendInfo(userEntity.getOpenId(), "您好,您有订单已由商家指定追评,请在追评导入中查看指定追评内容", missionDetailEntity.getMissionCode(),
                                        DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN), "请在追评导入中查看指定评价内容");
                            }
                            return R.ok();
                        }
                    }
                }
            }
        }
        return R.error(500, "系统错误，请联系客服!");
    }

    @Override
    @Transactional
    public AppStatus userUpdateAddComment(AddCommentForm form, Long userId) {
        AppStatus appStatus = null;
        MissionDetailEntity missionDetailEntity = new MissionDetailEntity();
        missionDetailEntity.setAddComImg(form.getAddComImg());
        missionDetailEntity.setIsAddcom(3);
        missionDetailEntity.setFinishAddComTime(new Date());
        if (baseMapper.update(missionDetailEntity, new EntityWrapper<MissionDetailEntity>().eq("mission_id", form.getMissionId())) > 0) {
            MissionEntity missionEntity = missionService.selectById(form.getMissionId());
            MissionDetailEntity missionDetailEntity1 = new MissionDetailEntity();
            missionDetailEntity1.setMissionId(form.getMissionId());
            MissionDetailEntity missionDetail = baseMapper.selectOne(missionDetailEntity1);
            BigDecimal addCommentUser = missionDetail.getAddComUser();
            UserEntity user = userService.selectById(userId);
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            userEntity.setStockMoney(user.getStockMoney().subtract(addCommentUser));
            userEntity.setFreeMoney(user.getFreeMoney().add(addCommentUser));
            if (userService.updateById(userEntity)) {//修改刷手余额
                DetailEntity detailEntity = new DetailEntity();
                detailEntity.setUserId(userId);
                detailEntity.setIsSeller(2);
                detailEntity.setAmount(missionDetail.getCommentUser());
                detailEntity.setCreateTime(new Date());
                detailEntity.setCode(missionEntity.getMissionCode());
                detailEntity.setUserName(missionEntity.getUserName());
                detailEntity.setMobile(missionEntity.getMobile());
                detailEntity.setBeforeStock(user.getStockMoney());
                detailEntity.setStock(userEntity.getStockMoney());
                detailEntity.setBeforeFree(user.getFreeMoney());
                detailEntity.setFree(userEntity.getFreeMoney());
                detailEntity.setNote("完成指定追评获取" + addCommentUser + "元");
                if (detailService.insert(detailEntity)) {
                    appStatus = AppStatus.success();
                } else {
                    appStatus = AppStatus.error(500, "完成指定追评错误");
                }
            }
        }
        return appStatus;
    }

    @Override
    @Transactional
    public AppStatus userUpdateComment(MissionRecGoodForm form, Long userId) {
        AppStatus appStatus = null;
        MissionEntity missionEntity = missionService.selectById(form.getMissionId());
        IntervalStepEntity intervalStepEntity = intervalStepService.selectById(1);
        if (missionEntity.getStatus() != 6) {
            return AppStatus.error(500, "操作有误!");
        }
        MissionDetailEntity tmpMissionDetail = new MissionDetailEntity();
        tmpMissionDetail.setMissionId(form.getMissionId());
        MissionDetailEntity missionDetailEntity1 = baseMapper.selectOne(tmpMissionDetail);
        InvitationStepEntity invitationStepEntity = invitationStepService.selectById(1);//获取收货限制
        Date date = DateUtils.addDateDays(missionDetailEntity1.getRemitTime(), invitationStepEntity.getReceiveGood());
        if (date.after(new Date())) {
            return AppStatus.error(500, "请在付款" + invitationStepEntity.getReceiveGood() + "天之后在上传截图!");
        }
        MissionDetailEntity missionDetailEntity = new MissionDetailEntity();
        missionDetailEntity.setReceive(form.getReceive());
        if (missionDetailEntity1.getIsComment() != null) {
            missionDetailEntity.setIsComment(3);
            missionDetailEntity.setCommentImg(form.getCommentImg());
            missionDetailEntity.setCommentTime(new Date());
        }
        BigDecimal commentUser = missionDetailEntity1.getCommentUser();
        BigDecimal agentMoney = missionDetailEntity1.getAgentMoney();
        missionDetailEntity.setId(missionDetailEntity1.getId());
        if (baseMapper.updateById(missionDetailEntity) > 0) {
            missionEntity.setStatus(7);
            if (missionService.updateById(missionEntity)) {
                // 完成刷手佣金、刷手上级、刷手上上级佣金金额变化
                // 2019-04-16 本佣模式变化 去掉冻结资金，直接在提现金额上面提现本佣
                UserEntity userEntity = userService.selectById(userId);
                //BigDecimal stockMoney = userEntity.getStockMoney();
                BigDecimal freeMoney = userEntity.getFreeMoney();
                //佣金
                BigDecimal money = missionDetailEntity1.getUserPay();
                userEntity.setFinishNumber(userEntity.getFinishNumber() == null ? 1 : userEntity.getFinishNumber() + 1);
                if (missionDetailEntity1.getIsComment() == 2) {
                    //userEntity.setStockMoney(userEntity.getStockMoney().subtract(money).subtract(commentUser));
                    userEntity.setFreeMoney(userEntity.getFreeMoney().add(money).add(commentUser));
                    money = money.add(commentUser);
                } else {
                    //userEntity.setStockMoney(userEntity.getStockMoney().subtract(money));
                    userEntity.setFreeMoney(userEntity.getFreeMoney().add(money));
                    money = money.add(commentUser);
                }
                if (userService.updateById(userEntity)) {//修改刷手可提现金额
                    DetailEntity detailEntity = new DetailEntity();
                    detailEntity.setIsSeller(2);
                    detailEntity.setUserId(userEntity.getId());
                    detailEntity.setUserName(userEntity.getName());
                    detailEntity.setMobile(userEntity.getMobile());
                    detailEntity.setType(5);
                    detailEntity.setBeforeStock(new BigDecimal(0));
                    detailEntity.setStock(userEntity.getStockMoney());
                    detailEntity.setBeforeFree(freeMoney);
                    detailEntity.setFree(userEntity.getFreeMoney());
                    detailEntity.setAmount(new BigDecimal("+" + money));
                    detailEntity.setCode(missionEntity.getMissionCode());
                    detailEntity.setNote("订单完成收货导入获得元可提现佣金：" + money.setScale(2) + "元");
                    detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
                    if (detailService.insert(detailEntity)) {
                        Long sellerId = missionEntity.getSellerId();
                        SellerEntity sellerEntity = sellerService.selectById(sellerId);
                        if (sellerEntity != null) {
                            if (sellerEntity.getPid() != null) {
                                if (sellerEntity.getIsSeller() == 1) {
                                    // 商家要请商家提成
                                    insertPseller(sellerEntity.getPid(), missionEntity.getMissionCode(), intervalStepEntity.getSellerMoney());
                                } else if (sellerEntity.getIsSeller() == 2) {
                                    // 刷手要请商家提成
                                    insertPuser(sellerEntity.getPid(), missionEntity.getMissionCode(), intervalStepEntity.getSellerMoney());
                                } else if (sellerEntity.getIsSeller() == 3) {
                                    // 代理商要请商家提成
                                    updateAgentMoney(sellerEntity.getPid(), missionEntity.getMissionCode(), agentMoney);
                                }
                            }
                        }
                        // highUserId 空值判断 否则会报空指针异常 modify by lwy 2019.01.14
                        if (userEntity.getHighUserId() == null) {
                            return AppStatus.success();
                        }
                        UserEntity firstHigh = userService.selectById(Long.parseLong(userEntity.getHighUserId()));
                        if (firstHigh == null) {
                            return AppStatus.success();
                        }
                        //一代百分比
                        BigDecimal money1 = intervalStepEntity.getMissionUser();
                        BigDecimal m1 = money.multiply(money1).divide(new BigDecimal(100));
                        UserEntity first = new UserEntity();
                        first.setId(firstHigh.getId());
                        first.setFreeMoney(firstHigh.getFreeMoney().add(m1));
                        if (userService.updateById(first)) {//修改上级可提现金额
                            DetailEntity firstDetail = new DetailEntity();
                            firstDetail.setUserId(firstHigh.getId());
                            firstDetail.setIsSeller(2);
                            firstDetail.setUserName(firstHigh.getName());
                            firstDetail.setMobile(firstHigh.getMobile());
                            firstDetail.setType(8);
                            firstDetail.setAmount(new BigDecimal("+" + m1));
                            firstDetail.setBeforeStock(firstHigh.getStockMoney());
                            firstDetail.setStock(firstHigh.getStockMoney());
                            firstDetail.setBeforeFree(firstHigh.getFreeMoney());
                            firstDetail.setFree(first.getFreeMoney());
                            firstDetail.setCode(missionEntity.getMissionCode());
                            firstDetail.setNote("编号为" + userEntity.getId() + "的下家一级代理完成了一个订单,给您带来了佣金收益" + m1.setScale(2) + "元");
                            firstDetail.setCreateTime(new Date(System.currentTimeMillis()));
                            if (detailService.insert(firstDetail)) {//生成上级佣金流水
                                if (firstHigh.getHighUserId() != null) {
                                    UserEntity secondHigh = userService.selectById(Long.parseLong(firstHigh.getHighUserId()));
                                    if (secondHigh != null) {
                                        //二代百分比
                                        BigDecimal money2 = intervalStepEntity.getSecMissionUser();
                                        BigDecimal m2 = money.multiply(money2).divide(new BigDecimal(100));
                                        UserEntity second = new UserEntity();
                                        second.setId(secondHigh.getId());
                                        second.setFreeMoney(secondHigh.getFreeMoney().add(m2));
                                        if (userService.updateById(second)) {//修改上上级可提现金额
                                            DetailEntity secondDetail = new DetailEntity();
                                            secondDetail.setUserId(secondHigh.getId());
                                            secondDetail.setIsSeller(2);
                                            secondDetail.setUserName(secondHigh.getName());
                                            secondDetail.setMobile(secondHigh.getMobile());
                                            secondDetail.setType(8);
                                            secondDetail.setAmount(new BigDecimal("+" + m2));
                                            secondDetail.setBeforeStock(secondHigh.getStockMoney());
                                            secondDetail.setStock(secondHigh.getStockMoney());
                                            secondDetail.setBeforeFree(secondHigh.getFreeMoney());
                                            secondDetail.setFree(second.getFreeMoney());
                                            secondDetail.setCode(missionEntity.getMissionCode());
                                            secondDetail.setNote("编号为" + userEntity.getId() + "的下下家二级代理完成了一个订单,给您带来了佣金收益" + m2.setScale(2) + "元");
                                            secondDetail.setCreateTime(new Date(System.currentTimeMillis()));
                                            if (detailService.insert(secondDetail)) {//生成上上级佣金流水
                                                appStatus = AppStatus.success();
                                            }
                                        }
                                    } else {
                                        appStatus = AppStatus.success();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return appStatus;
    }

    @Override
    @Transactional
    public R updateRemoveInfo(MissionDetailEntity missionDetailEntity) {
        boolean flag = false;
        Integer type = missionDetailEntity.getIsBack();
        Long missionId = missionDetailEntity.getMissionId();
        MissionEntity missionEntity = missionService.selectById(missionId);
        // 判断是否已经撤销处理
        if (missionEntity.getIsAnnul() == 2) {
            return R.error(500, "此单已撤销，请勿重复操作");
        }
        MissionEntity missionEntity1 = new MissionEntity();
        missionEntity1.setId(missionEntity.getId());
        missionEntity1.setIsAnnul(2);
        //    missionEntity1.setUserNote(missionDetailEntity.getCancelNote());
        missionService.updateById(missionEntity1);
        MissionDetailEntity missionDetailEntity1 = new MissionDetailEntity();
        missionDetailEntity1.setMissionId(missionDetailEntity.getMissionId());
        MissionDetailEntity missionDetailEntity2 = baseMapper.selectOne(missionDetailEntity1);
        IntervalStepEntity intervalStepEntity = intervalStepService.selectById(1);
        Long userId1 = missionEntity.getUserId();
        BigDecimal userPay = missionDetailEntity2.getUserPay();
        TaskEntity taskEntity = taskService.selectById(missionEntity.getTaskId());
        String missionCode = missionEntity.getMissionCode();
        TaskEntity entity = new TaskEntity();//修改任务剩余单数和已接单数
        if (type == 1) {//撤销类型（1：撤销返还大厅，2：撤销返还商家）
            entity.setRemain(taskEntity.getRemain() + 1);
        } else if (type == 2) {
            entity.setRemove(taskEntity.getRemove() + 1);
        }
        entity.setAccept(taskEntity.getAccept() - 1);
        entity.setId(taskEntity.getId());
        String ids = taskEntity.getUserIdList();//修改任务订单刷手Ids
        String userId = missionEntity.getUserId() + ",";
        if (ids.contains(userId)) {
            ids = ids.replace(userId, " ").trim();
        }
        entity.setUserIdList(ids);
        taskService.updateById(entity);
        if (baseMapper.update(missionDetailEntity, new EntityWrapper<MissionDetailEntity>().eq("mission_id", missionId)) > 0) {
            MissionDetailEntity missionDetailEntity3 = new MissionDetailEntity();
            missionDetailEntity3.setId(missionDetailEntity2.getId());
            missionDetailEntity3.setIsBack(1);
            if (type == 1) {
                //撤销回任务大厅
                baseMapper.updateById(missionDetailEntity3);
                //重新生成订单
                missionEntity = missionEntity.missionInit();
                if (missionService.insert(missionEntity)) {
                    //重新生成订单详情
                    MissionDetailEntity missionDetailEntity4 = new MissionDetailEntity();
                    missionDetailEntity4.setMissionId(missionEntity.getId());
                    missionDetailEntity4.setStyle(missionDetailEntity2.getStyle());
                    missionDetailEntity4.setOrder(missionDetailEntity2.getOrder());
                    missionDetailEntity4.setIntervalEnd(missionDetailEntity2.getIntervalEnd());
                    missionDetailEntity4.setIntervalBegin(missionDetailEntity2.getIntervalBegin());
                    missionDetailEntity4.setNote(missionDetailEntity2.getNote());
                    missionDetailEntity4.setOtherNote(missionDetailEntity2.getOtherNote());
                    missionDetailEntity4.setImg(missionDetailEntity2.getImg());
                    missionDetailEntity4.setUserPay(missionDetailEntity2.getUserPay());
                    missionDetailEntity4.setUrl(missionDetailEntity2.getUrl());
                    if (missionDetailEntity2.getIsComment() == 2) {
                        missionDetailEntity4.setIsComment(missionDetailEntity2.getIsComment());
                        missionDetailEntity4.setComment(missionDetailEntity2.getComment());
                        missionDetailEntity4.setImgList(missionDetailEntity2.getImgList());
                        missionDetailEntity4.setCommentPay(missionDetailEntity2.getCommentPay());
                        missionDetailEntity4.setCommentUser(missionDetailEntity2.getCommentUser());
                    }
                    baseMapper.insert(missionDetailEntity4);
                    flag = false;
                }
            } else if (type == 2) {//修改商家余额并生成流水
                //撤销回商家
                SellerEntity sellerEntity = sellerService.selectById(missionEntity.getSellerId());
                BigDecimal money = null;
                /*if (sellerEntity.getIsSeller() == 3) {
                    AgentSellerMoneyEntity agentSellerMoneyEntity = agentSellerMoneyService.selectOne(new EntityWrapper<AgentSellerMoneyEntity>().eq("seller_id", sellerEntity.getId()));
                    money = ComparePrice.balanceMoney1(taskEntity.getPrice(), sellerEntity.getBenefit(), agentSellerMoneyEntity);//获取代理下商家每单精刷单商家所支付佣金
                } else {
                    money = ComparePrice.getSellerPayCommissionMoney(taskEntity.getPrice(), sellerEntity.getBenefit(), intervalStepEntity);//获取平台下商家每单精刷单商家所支付佣金
                }*/
                money = ComparePrice.getSellerPayCommissionMoney(taskEntity.getPrice(), sellerEntity.getBenefit(), intervalStepEntity);//获取平台下商家每单精刷单商家所支付佣金
                BigDecimal price = taskEntity.getPrice(); //本金
                /*if (taskEntity.getTaskStyle().equals(1)) {
                    money = money.add(intervalStepEntity.getAppointOneSeller());//标签2天商家所支付的佣金
                } else if (taskEntity.getTaskStyle().equals(2)) {
                    money = money.add(intervalStepEntity.getAppointTwoSeller());//标签3天商家所支付的佣金
                }*/
                if (missionDetailEntity2.getIsComment() == 2) {
                    money = money.add(intervalStepEntity.getCommentSeller());//指定评价商家所支付的佣金
                }
                BigDecimal balance = sellerEntity.getBalance().add(money.add(price));
                sellerEntity.setBalance(balance);
                if (sellerService.updateById(sellerEntity)) {
                    DetailEntity detailEntity = new DetailEntity();
                    detailEntity.setBalance(balance);
                    detailEntity.setIsSeller(1);
                    detailEntity.setUserId(sellerEntity.getId());
                    detailEntity.setUserName(sellerEntity.getContact());
                    detailEntity.setMobile(sellerEntity.getMobile());
                    detailEntity.setType(7);
                    detailEntity.setAmount(money.add(price));
                    //本佣模式 本金也一并退给商家
                    detailEntity.setNote("任务撤销返回本佣金：" + money.add(price) + "元");
                    detailEntity.setCode(missionCode);
                    detailEntity.setCreateTime(new Date());
                    detailService.insert(detailEntity);
                }
                flag = false;
            }
            if (flag) {//修改刷手余额并生成流水(本拥模式不需要了)
                UserEntity userEntity = userService.selectById(userId1);
                UserEntity user = new UserEntity();
                user.setId(userEntity.getId());
                BigDecimal money = userEntity.getStockMoney().subtract(userPay);
                BigDecimal amount = userPay;
                if (missionDetailEntity2.getCommentUser() != null) {
                    money = money.subtract(missionDetailEntity2.getCommentUser());
                    amount = userPay.add(missionDetailEntity2.getCommentUser());
                }
                user.setStockMoney(money);
                userService.updateById(user);
                DetailEntity detailEntity = new DetailEntity();
                detailEntity.setIsSeller(2);
                detailEntity.setUserId(userEntity.getId());
                detailEntity.setMobile(userEntity.getMobile());
                detailEntity.setUserName(userEntity.getName());
                detailEntity.setType(7);
                detailEntity.setAmount(new BigDecimal(0).subtract(amount));
                detailEntity.setBeforeFree(userEntity.getFreeMoney());
                detailEntity.setFree(userEntity.getFreeMoney());
                detailEntity.setBeforeStock(userEntity.getStockMoney());
                detailEntity.setStock(money);
                detailEntity.setNote("刷手订单被撤销刷手佣金扣除" + amount + "元");
                detailEntity.setCode(missionCode);
                detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
                if (detailService.insert(detailEntity)) {
                    return R.ok();
                }
            }
            return R.ok();
        }
        return R.error(500, "系统错误，请联系管理员!");
    }

    /**
     * 修改上级商家余额
     * @param sellerId
     * @param missionCode
     * @param sellerMoney
     */
    public void insertPseller(Long sellerId, String missionCode, BigDecimal sellerMoney) {
        SellerEntity seller = sellerService.selectById(sellerId);
        SellerEntity sellerEntity = new SellerEntity();
        sellerEntity.setBalance(seller.getBalance().add(sellerMoney));
        sellerEntity.setId(sellerId);
        sellerService.updateById(sellerEntity);
        DetailEntity detailEntity = new DetailEntity();
        detailEntity.setBalance(sellerEntity.getBalance());
        detailEntity.setMobile(seller.getMobile());
        detailEntity.setUserName(seller.getContact());
        detailEntity.setCode(missionCode);
        detailEntity.setType(12);
        detailEntity.setIsSeller(1);
        detailEntity.setUserId(sellerId);
        detailEntity.setAmount(sellerMoney);
        detailEntity.setNote("商家下级商家订单完成获取提成：" + sellerMoney + "元");
        detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
        detailService.insert(detailEntity);
    }

    /**
     * 修改上级刷手余额
     * @param userId
     * @param missionCode
     * @param sellerMoney
     */
    public void insertPuser(Long userId, String missionCode, BigDecimal sellerMoney) {
        UserEntity userEntity = userService.selectById(userId);
        UserEntity user = new UserEntity();
        user.setFreeMoney(userEntity.getFreeMoney().add(sellerMoney));
        user.setId(userId);
        userService.updateById(user);
        DetailEntity detailEntity = new DetailEntity();
        detailEntity.setCode(missionCode);
        detailEntity.setType(13);
        detailEntity.setIsSeller(2);
        detailEntity.setUserId(userId);
        detailEntity.setUserName(userEntity.getName());
        detailEntity.setMobile(userEntity.getMobile());
        detailEntity.setBeforeFree(userEntity.getFreeMoney());
        detailEntity.setBeforeStock(userEntity.getStockMoney());
        detailEntity.setFree(userEntity.getFreeMoney().add(sellerMoney));
        detailEntity.setStock(user.getStockMoney());
        detailEntity.setNote("刷手下级商家订单完成获取提成：" + sellerMoney + "元");
        detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
        detailService.insert(detailEntity);
    }

    /**
     * 修改代理商余额
     *
     * @param agentId
     * @param missionCode
     * @param agentMoney
     */
    public void updateAgentMoney(Long agentId, String missionCode, BigDecimal agentMoney) {
        if (agentMoney.doubleValue() > 0) {
            AgentEntity agent = agentService.selectById(agentId);
            agent.setBalance(agent.getBalance().add(agentMoney));
            agent.setUpdateTime(new Date());
            agentService.updateById(agent);
            DetailEntity detailEntity = new DetailEntity();
            detailEntity.setIsSeller(3);
            detailEntity.setType(14);
            detailEntity.setUserId(agent.getId());
            detailEntity.setUserName(agent.getName());
            detailEntity.setMobile(agent.getMobile());
            detailEntity.setAmount(agentMoney);
            detailEntity.setCode(missionCode);
            detailEntity.setNote("下级商家订单完成获取奖励金" + agentMoney + "元");
            detailEntity.setBalance(agent.getBalance());
            detailEntity.setCreateTime(new Date());
            detailService.insert(detailEntity);
        }
    }
}
