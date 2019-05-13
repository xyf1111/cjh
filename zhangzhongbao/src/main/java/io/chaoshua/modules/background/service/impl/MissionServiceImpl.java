package io.chaoshua.modules.background.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.utils.R;
import io.chaoshua.common.yto.YtoUtil;
import io.chaoshua.common.yto.req.OrderRequest;
import io.chaoshua.common.yto.req.OrderSubmitRequest;
import io.chaoshua.common.yto.resp.OrderSubmitResponse;
import io.chaoshua.common.yto.resp.WaybillResponse;
import io.chaoshua.modules.app.vo.MissionVo;
import io.chaoshua.modules.app.vo.user.UserMissionVo;
import io.chaoshua.modules.background.entity.*;
import io.chaoshua.common.utils.*;
import io.chaoshua.modules.app.vo.user.StateMoneyVo;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.chaoshua.modules.background.service.*;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.modules.background.service.task.TaskService;
import io.chaoshua.modules.seller.dto.ExportMissionDTO;
import io.chaoshua.modules.weixin.service.SendInfoController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.chaoshua.modules.background.dao.mission.MissionDao;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.service.mission.MissionService;
import org.springframework.transaction.annotation.Transactional;


@Service("missionService")
public class MissionServiceImpl extends ServiceImpl<MissionDao, MissionEntity> implements MissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InvitationStepService invitationStepService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MissionDetailService missionDetailService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private UserAuthImgService userAuthImgService;
    @Autowired
    private SendInfoController sendInfoController;
    @Autowired
    private UserAddressService userAddressService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MissionEntity> page = new Query<MissionEntity>(params).getPage();
        params.put("sort", "1"); //按照任务创建时间排序
        page.setRecords(baseMapper.getList(page, params));
        for (MissionEntity mission : page.getRecords()) {
            mission.setAgentName(sellerService.getAgentNameBySellerId(mission.getSellerId()));
        }
        return new PageUtils(page);
    }

    @Override
    public PageUtils getHistoryByMissionId(Map<String, Object> params) {
        Page<MissionEntity> page = new Query<MissionEntity>(params).getPage();
        page.setRecords(baseMapper.getHistoryByMissionId(page, params));
        return new PageUtils(page);
    }

    @Override
    public PageUtils getHistoryByUserId(Map<String, Object> params) {
        Page<MissionEntity> page = new Query<MissionEntity>(params).getPage();
        page.setRecords(baseMapper.getHistoryListByUserId(page, params));
        return new PageUtils(page);
    }

    @Override
    public PageUtils getAppointList(Map<String, Object> params) {
        Page<MissionEntity> page = new Query<MissionEntity>(params).getPage();
        page.setRecords(baseMapper.getAppointList(page, params));
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public R updateRole(MissionEntity missionEntity) {
        MissionEntity missionEntity1 = baseMapper.selectById(missionEntity.getId());
        if (missionEntity1.getRole() == 2 && missionEntity.getRole() == 2) {
            return R.error(500, "该订单已经审核通过，请勿重复操作!");
        } else if (missionEntity1.getRole() == 3) {
            return R.error(500, "该订单已经被拒绝，请勿重复操作");
        } else {
            TaskEntity taskEntity = taskService.selectById(missionEntity.getTaskId());
            if (missionEntity.getRole() == 2) {
                //审核通过
                MissionEntity mission = new MissionEntity();
                mission.setRole(2);
                mission.setStatus(2);
                mission.setIsPay(1);
                mission.setId(missionEntity.getId());
                baseMapper.updateById(mission);
                UserEntity userEntity = userService.selectById(missionEntity.getUserId());
                Long userId = userEntity.getId();
                // 修改刷手不可动佣金
                userEntity.setStockMoney(userEntity.getStockMoney().add(taskEntity.getUserPay()));
                userService.updateById(userEntity);
                MissionDetailEntity missionDetailEntity = new MissionDetailEntity();
                missionDetailEntity.setPublishTime(new Date());
                if (missionDetailService.update(missionDetailEntity, new EntityWrapper<MissionDetailEntity>().eq("mission_id", missionEntity.getId()))) {//修改订单详情
                    DetailEntity detailEntity = new DetailEntity();
                    detailEntity.setUserId(userId);
                    detailEntity.setUserName(userEntity.getName());
                    detailEntity.setMobile(userEntity.getMobile());
                    detailEntity.setIsSeller(2);
                    detailEntity.setType(2);
                    detailEntity.setAmount(taskEntity.getUserPay());
                    detailEntity.setBeforeStock(userEntity.getStockMoney().subtract(taskEntity.getUserPay()));
                    detailEntity.setStock(userEntity.getStockMoney());
                    detailEntity.setBeforeFree(userEntity.getFreeMoney());
                    detailEntity.setFree(userEntity.getFreeMoney());
                    detailEntity.setCode(missionEntity.getMissionCode());
                    detailEntity.setNote("订单审核通过获取不可提现金额" + taskEntity.getUserPay() + "元");
                    detailEntity.setCreateTime(new Date());
                    if (detailService.insert(detailEntity)) {//生成流水 并推送消息
                        if (userEntity.getOpenId() != null) {
                            sendInfoController.sendInfo(userEntity.getOpenId(), "您好,您的接单已审核通过", missionEntity.getMissionCode(), mission.getMissionTime() + "（执行时间）", "请在执行时间10分钟内完成任务导入").equals("success");
                        }
                        return R.ok();
                    }
                }
            } else if (missionEntity.getRole() == 3) {
                //审核未通过
                TaskEntity entity = new TaskEntity();
                entity.setRemain(taskEntity.getRemain() + 1);
                entity.setId(taskEntity.getId());
                entity.setAccept(taskEntity.getAccept() - 1);
                String ids = taskEntity.getUserIdList();
                String userId = missionEntity.getUserId() + ",";
                if (ids.contains(userId)) {
                    ids = ids.replace(userId, " ").trim();
                }
                entity.setUserIdList(ids);
                //修改任务剩余单数
                taskService.updateById(entity);
                missionEntity.setRole(3);
                // missionEntity.setUserNote(missionEntity.getRefuseNote());
                baseMapper.updateById(missionEntity);
                MissionEntity mission = baseMapper.selectById(missionEntity.getId());
                MissionDetailEntity missionDetailEntity1 = new MissionDetailEntity();
                missionDetailEntity1.setRefuseNote(missionEntity.getRefuseNote());
                missionDetailService.update(missionDetailEntity1, new EntityWrapper<MissionDetailEntity>().eq("mission_id", missionEntity.getId()));
                MissionDetailEntity missionDetailEntity = missionDetailService.selectOne(new EntityWrapper<MissionDetailEntity>().eq("mission_id", missionEntity.getId()));
                mission = mission.missionInit();
                if (baseMapper.insert(mission) > 0) {
                    MissionDetailEntity missionDetail = new MissionDetailEntity();
                    missionDetail.setMissionId(mission.getId());
                    missionDetail.setStyle(missionDetailEntity.getStyle());
                    missionDetail.setOrder(missionDetailEntity.getOrder());
                    missionDetail.setIntervalEnd(missionDetailEntity.getIntervalEnd());
                    missionDetail.setIntervalBegin(missionDetailEntity.getIntervalBegin());
                    missionDetail.setNote(missionDetailEntity.getNote());
                    missionDetail.setOtherNote(missionDetailEntity.getOtherNote());
                    missionDetail.setImg(missionDetailEntity.getImg());
                    missionDetail.setUserPay(missionDetailEntity.getUserPay());
                    missionDetail.setUrl(missionDetailEntity.getUrl());
                    if (missionDetailEntity.getIsComment() == 2) {
                        missionDetail.setIsComment(missionDetailEntity.getIsComment());
                        missionDetail.setComment(missionDetailEntity.getComment());
                        missionDetail.setImgList(missionDetailEntity.getImgList());
                        missionDetail.setCommentPay(missionDetailEntity.getCommentPay());
                        missionDetail.setCommentUser(missionDetailEntity.getCommentUser());
                    }
                    missionDetailService.insert(missionDetail);
                }
                return R.ok();
            }
        }
        return null;
    }

    @Override
    public void confirmPrice(Long id, BigDecimal price) {
        // 获取订单
        MissionEntity mission = this.baseMapper.selectById(id);
        if (mission == null) {
            throw new RRException("订单不存在");
        }
        if (mission.getStatus() != 3) {
            throw new RRException("当前订单状态无法修改");
        }
        // 修改价格
        mission.setPrice(price);
        // 状态更新 刷手已付款(5) -> 订单已确认(8)
        mission.setStatus(8);

        //预先写入物流信息
        Long userId = mission.getUserId();
        UserAddressEntity uae = userAddressService.selectByUserId(userId);
        if(uae != null) {
            //收货人地址,手机号
            mission.setLogisticsAddress(uae.getProvince() + "," + uae.getCity() + "," + uae.getDistrict() + "," + uae.getAddress());
            mission.setLogisticsPhone(uae.getReceiveMobile());
            mission.setLogisticsName(uae.getReceiveName());
        } else {
            logger.warn("平台确认----用户Id(" + userId + ")无地址！自动跳过");
        }

        this.baseMapper.updateById(mission);
    }

    @Override
    public void platformConfirm(MissionEntity m) {
        // 获取订单
        MissionEntity mission = this.baseMapper.selectById(m.getId());
        if (mission == null) {
            throw new RRException("订单不存在");
        }
        if (mission.getStatus() != 3) {
            throw new RRException("当前订单状态无法修改");
        }
        // 修改价格
        mission.setPrice(m.getPrice());
        // 修改货重
        mission.setWeight(m.getWeight());
        // 状态更新 刷手已付款(5) -> 订单已确认(8)
        mission.setStatus(8);

        //更新物流信息，如果有需要的话
        if(m.getIsLogistics() == 1) {
            mission.setUserName(m.getUserName());
            mission.setIsLogistics(m.getIsLogistics());
            mission.setLogisticsPhone(m.getLogisticsPhone());
            mission.setLogisticsName(m.getLogisticsName());
            mission.setLogisticsAddress(m.getLogisticsAddress());
            Long userId = mission.getUserId();
            //更新回刷手的默认地址
            UserAddressEntity uae = userAddressService.selectByUserId(userId);
            String[] address = m.getLogisticsAddress().split(",");
            if (uae != null) {
                uae.setProvince(address[0]);
                uae.setCity(address[1]);
                uae.setDistrict(address[2]);
                uae.setAddress(address[3]);
                uae.setReceiveName(m.getLogisticsName());
                uae.setReceiveMobile(m.getLogisticsPhone());
                uae.setUpdateTime(new Date(System.currentTimeMillis()));
                userAddressService.updateById(uae);
            } else {
                uae = new UserAddressEntity();
                uae.setUserId(userId);
                uae.setProvince(address[0]);
                uae.setCity(address[1]);
                uae.setDistrict(address[2]);
                uae.setAddress(address[3]);
                uae.setReceiveName(m.getLogisticsName());
                uae.setReceiveMobile(m.getLogisticsPhone());
                uae.setCreateTime(new Date(System.currentTimeMillis()));
                userAddressService.insert(uae);
            }
        }
        /*Long userId = mission.getUserId();
        UserAddressEntity uae = userAddressService.selectByUserId(userId);
        if(uae != null) {
            //收货人地址,手机号
            mission.setLogisticsAddress(uae.getProvince() + "," + uae.getCity() + "," + uae.getDistrict() + "," + uae.getAddress());
            mission.setLogisticsPhone(mission.getMobile());
        } else {
            logger.warn("平台确认----用户Id(" + userId + ")无地址！自动跳过");
        }*/

        this.baseMapper.updateById(mission);
    }

    /**
     * 获取用户最近一次接单时间
     *
     * @param userId
     * @return
     */
    @Override
    public Date getUserLastOrderTime(Long userId) {
        return this.baseMapper.getUserLastOrderTime(userId);
    }

    @Override
    public List<ExportMissionDTO> queryExportMissionList(Integer excelType, List<Long> ids) {
        return this.baseMapper.queryExportMissionList(excelType, ids);
    }

    @Override
    @Transactional
    public R revokeComment(MissionEntity missionEntity) {
        Integer flag = missionEntity.getFlag();
        MissionDetailEntity missionDetail = missionDetailService.selectOne(new EntityWrapper<MissionDetailEntity>().eq("mission_id", missionEntity.getId()));
        BigDecimal commentPay = missionDetail.getCommentPay();
        BigDecimal addComPay = missionDetail.getAddComPay();
        BigDecimal commentUser = missionDetail.getCommentUser();
        BigDecimal addComUser = missionDetail.getAddComUser();
        if (flag == 2) {//1:追评，2：评价
            missionDetail.setIsComment(4);
            missionDetail.setImgList(null);
            missionDetail.setComment(null);
            missionDetail.setCommentPay(null);
            missionDetail.setCommentUser(null);
        } else if (flag == 1) {
            missionDetail.setIsAddcom(4);
            missionDetail.setAddcom(null);
            missionDetail.setAddImglist(null);
            missionDetail.setAddComPay(null);
            missionDetail.setAddComUser(null);
        }
        if (missionDetailService.updateAllColumnById(missionDetail)) {
            //修改商家余额
            SellerEntity sellerEntity = sellerService.selectById(missionEntity.getSellerId());
            BigDecimal bigDecimal = sellerEntity.getBalance();
            if (flag == 2) {
                bigDecimal = bigDecimal.add(commentPay);
            } else if (flag == 1) {
                bigDecimal = bigDecimal.add(addComPay);
            }
            sellerEntity.setBalance(bigDecimal);
            if (sellerService.updateById(sellerEntity)) {
                //撤销评价与追评商家生成商家流水
                DetailEntity detailEntity = new DetailEntity();
                detailEntity.setCreateTime(new Date());
                detailEntity.setBalance(sellerEntity.getBalance());
                detailEntity.setType(7);
                detailEntity.setIsSeller(1);
                detailEntity.setUserId(missionEntity.getSellerId());
                StringBuilder stringBuilder = new StringBuilder();
                if (flag == 2) {
                    stringBuilder.append("指定评价任务撤销,返还").append(commentPay).append("元");
                    detailEntity.setAmount(commentPay);
                } else if (flag == 1) {
                    stringBuilder.append("指定追评任务撤销,返还").append(addComPay).append("元");
                    detailEntity.setAmount(addComPay);
                }
                detailEntity.setCode(missionEntity.getMissionCode());
                detailEntity.setNote(stringBuilder.toString());
                if (!detailService.insert(detailEntity)) {
                    return R.error(500, "生成商家流水错误!");
                }
            } else {
                return R.error(500, "修改商家余额错误!");
            }
            UserEntity userEntity = userService.selectById(missionEntity.getUserId());
            BigDecimal bigDecimal1 = userEntity.getStockMoney();
            if (flag == 2) {
                userEntity.setStockMoney(bigDecimal1.subtract(commentUser));
            } else if (flag == 1) {
                userEntity.setStockMoney(bigDecimal1.subtract(addComUser));
            }
            //修改刷手不可用佣金
            userEntity.setStockMoney(bigDecimal1);
            if (userService.updateById(userEntity)) {
                //撤销评价与追评刷手生成刷手流水
                DetailEntity detailEntity1 = new DetailEntity();
                detailEntity1.setCreateTime(new Date());
                detailEntity1.setType(7);
                detailEntity1.setIsSeller(2);
                detailEntity1.setUserId(missionEntity.getUserId());
                StringBuilder stringBuilder1 = new StringBuilder();
                if (flag == 2) {
                    stringBuilder1.append("指定评价任务撤销,撤回").append(commentUser).append("元");
                    detailEntity1.setAmount(new BigDecimal(0).subtract(commentUser));
                } else if (flag == 1) {
                    stringBuilder1.append("指定追评任务撤销,撤回").append(addComUser).append("元");
                    detailEntity1.setAmount(new BigDecimal(0).subtract(addComUser));
                }
                detailEntity1.setCode(missionEntity.getMissionCode());
                detailEntity1.setNote(stringBuilder1.toString());
                detailEntity1.setUserName(userEntity.getName());
                detailEntity1.setBeforeStock(bigDecimal1);
                detailEntity1.setStock(userEntity.getStockMoney());
                detailEntity1.setBeforeFree(userEntity.getFreeMoney());
                detailEntity1.setFree(userEntity.getFreeMoney());
                detailEntity1.setMobile(userEntity.getMobile());
                if (!detailService.insert(detailEntity1)) {
                    return R.error(500, "生成刷手流水错误!");
                }
            } else {
                return R.error(500, "修改刷手冻结金额错误!");
            }
        } else {
            return R.error(500, "修改订单状态错误!");
        }
        return R.ok();
    }

    @Override
    public List<StateMoneyVo> getStateMoneyListByUserId(Map<String, Object> params, Long userId) {
        Page<MissionEntity> page = new Query<MissionEntity>(params).getPage();
        List<MissionEntity> list = baseMapper.getStateMoneyListByUserId(page, userId);
        List<StateMoneyVo> voList = new ArrayList<>();
        for (MissionEntity missionEntity : list) {
            StateMoneyVo stateMoneyVo = StateMoneyVo.toVO(missionEntity);
            voList.add(stateMoneyVo);
        }
        return voList;
    }


    @Override
    @Transactional
    public AppResult<UserMissionVo> submitMission(Long taskId, Long userId) {
        synchronized (this) {
            AppResult appResult = null;
            UserEntity userEntity = userService.selectById(userId);
            if (userEntity.getStatus() != 2) {
                return AppResult.error(500, "您没有接单权限,请联系客服!");
            }
            if (userEntity.getRole() == 2) {
                // 您被拉入黑名单,请联系客服!
                return AppResult.error(100, "您被拉入黑名单,请联系客服!");
            }
            // 刷手在接单之后的一个小时之内不能再次接单的限制
            Date orderTime = missionService.getUserLastOrderTime(userId);
            if (orderTime != null && System.currentTimeMillis() - orderTime.getTime() <= 60 * 60 * 1000) {
                return AppResult.error(100, "您在接单之后的一个小时之内不能再次接单!");
            }
            MissionEntity missionEntity = new MissionEntity();
            missionEntity.setTaskId(taskId);
            missionEntity.setUserId(userId);
            MissionEntity missionEntity1 = baseMapper.selectOne(missionEntity);
            if (missionEntity1 != null) {
                return AppResult.error(500, "您已经接过该任务!");
            }
            TaskEntity taskEntity = taskService.selectById(taskId);
            if (userEntity.getLimit().compareTo(taskEntity.getPrice()) < 0) {
                return AppResult.error(500, "对不起,该任务超出您的接单金额限制!");
            }
            if (taskEntity.getRemain() > 0) {
                Date date = DateUtils.getDay();
                List<MissionEntity> missionEntityList = baseMapper.selectList(new EntityWrapper<MissionEntity>()
                        .eq("task_id", taskId)
                        .eq("role", 0)
                        .orderBy("mission_time", true));//获取未被接的订单，在前面的订单应该最先被接
                MissionEntity missionEntity2 = null;
                if (missionEntityList != null && missionEntityList.size() > 0) {
                    missionEntity2 = missionEntityList.get(0);
                    // 添加判断，是否到了任务接单时间
                    Date missionTime = missionEntity2.getMissionTime();
                    Date nowDate = new Date(System.currentTimeMillis());
                    if(missionTime.getTime() > nowDate.getTime()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        return AppResult.error(500, "该任务还未到执行时间(" + sdf.format(missionTime) + ")请等待！");
                    }
                } else {
                    return AppResult.error(500, "任务已被接完");
                }
                missionEntity2.setUserId(userId);
                missionEntity2.setUserName(userEntity.getName());
                missionEntity2.setMobile(userEntity.getMobile());
                missionEntity2.setTaobao(userEntity.getTaobao());
                // 修改刷手拍下直接通过审核
                missionEntity2.setRole(2);
                missionEntity2.setOrderTime(new Date());
                if (baseMapper.updateById(missionEntity2) > 0) {//刷手绑定订单
                    TaskEntity entity = new TaskEntity();
                    entity.setRemain(taskEntity.getRemain() - 1);
                    entity.setAccept(taskEntity.getAccept() + 1);
                    entity.setId(taskId);
                    String ids = taskEntity.getUserIdList();//修改任务接单刷手ID
                    if (ids == null) {
                        ids = userId.toString();
                    } else {
                        ids = ids + "," + userId;
                    }
                    entity.setUserIdList(ids);
                    taskService.updateById(entity);
                    MissionDetailEntity missionDetailEntity = new MissionDetailEntity();
                    missionDetailEntity.setHolder(userEntity.getHolder());
                    missionDetailEntity.setCardNumber(userEntity.getCardNumber());
                    missionDetailEntity.setBank(userEntity.getBank());
                    missionDetailEntity.setUserPay(taskEntity.getUserPay());
                    missionDetailEntity.setAgentMoney(taskEntity.getAgentMoney());
                    missionDetailEntity.setUserNote(userEntity.getNote());
                    missionDetailEntity.setUpdateTime(new Date());
                    missionDetailService.update(missionDetailEntity, new EntityWrapper<MissionDetailEntity>().eq("mission_id", missionEntity2.getId()));
                    Integer count = userAuthImgService.selectCount(new EntityWrapper<UserAuthImgEntity>().eq("user_id", userId).ge("create_time", date));
                    appResult = AppResult.success();
                    InvitationStepEntity invitationStepEntity = invitationStepService.selectById(1);
                    UserMissionVo missionVo = new UserMissionVo();
                    if (invitationStepEntity.getIsAuth() == 2 || count > 0) {
                        // 认证状态关闭(isAuth=2) 或者 当天已传认证图（count>0）；无需认证
                        // false 无需认证 true 要
                        missionVo.setAuth(false);
                        //修改状态
                        MissionEntity mission = new MissionEntity();
                        mission.setId(missionEntity2.getId());
                        mission.setStatus(2);
                        baseMapper.updateById(mission);
                        missionVo.setAuth(false);
                    } else {
                        missionVo.setAuth(true);
                    }
                    missionVo.setMissionId(missionEntity2.getId());
                    appResult.setData(missionVo);
                }
            } else {
                appResult = AppResult.error(500, "任务已经被接完!");
            }
            return appResult;
        }
    }

    @Override
    public Page<MissionEntity> getHistoryListByUserId(Map<String, Object> params) {
        Page<MissionEntity> page = new Query<MissionEntity>(params).getPage();
        List<MissionEntity> missionList = baseMapper.getHistoryListByUserId(page, params);
        page.setRecords(missionList);
        return page;
    }

    @Override
    public MissionVo getMissionDetail(Long missionId) {
        MissionVo missionVo = baseMapper.getMissionDetail(missionId);
        if (missionVo.getShopName() != null) {
            //偶数*替换
            char[] c = missionVo.getShopName().toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (i % 2 == 1) {
                    c[i] = '*';
                }
            }
            String str = new String(c);
            missionVo.setShopName(str);
        }
        //商品价格首位数替换
        if (missionVo.getPrice() != null) {
            String str = missionVo.getPrice().toString();
            missionVo.setPriceStr("*" + str.substring(1));
        }

        Integer order = missionVo.getOrder();
        if (order != null) {
            if (order == 1) {
                missionVo.setOrderStr("综合排序");
            } else if (order == 2) {
                missionVo.setOrderStr("销量排序");
            } else if (order == 3) {
                missionVo.setOrderStr("价格排序");
            }
        }
        return missionVo;
    }

    @Override
    public void updates(List<MissionEntity> list) {
        baseMapper.updates(list);
    }

    @Override
    @Transactional(rollbackFor=RuntimeException.class)
    public void updateStatusAndSendLogistics(MissionEntity mission) {
        long sellerId = mission.getSellerId();
        long userId = mission.getUserId();
        SellerEntity sellerEntity = sellerService.selectById(sellerId);
        int isLogistics = mission.getIsLogistics();
        mission.setStatus(6);
        if (isLogistics != 0 ) {
            long shopId = mission.getShopId();
            //说明要发送物流
            mission.setLogisticsComp("圆通速递");
            //订单号 只能包含大小写字母， 数字和-（减号），且字符串长度不能大于35
            String orderNo = mission.getMissionCode().replace("_", "-");

            // 调用物流接口，批量生产物流信息，和单号信息
            // BalanceGetResponse bgr = YtoUtil.balanceGet();
            // logger.info("查询余额接口：" + bgr.toString());
            OrderSubmitRequest osRequest = new OrderSubmitRequest();
            ShopEntity se = shopService.selectById(shopId);
            String[] sendAddress = se.getAddress().split(",");
            String[] receiveAddress = mission.getLogisticsAddress().split(",");

            //做成收发货地址
            OrderRequest or = new OrderRequest(1,
                    orderNo,
                    mission.getLogisticsName() , // 收货人
                    mission.getLogisticsPhone() , // 收货人电话
                    mission.getLogisticsPhone() , // 收货人电话
                receiveAddress[0], receiveAddress[1], receiveAddress[2], receiveAddress[3],
                    mission.getSellerName(),  // 发件人
                    sellerEntity.getMobile(), // 发件人电话
                    sellerEntity.getMobile(), // 发件人电话
                sendAddress[0] ,sendAddress[1] ,sendAddress[2], sendAddress[3], mission.getKeyWord(), mission.getWeight());
            or.setOrderNo(orderNo);
            osRequest.setLogiType(4);
            osRequest.setOrders(Arrays.asList(or));
            OrderSubmitResponse osResponse = YtoUtil.orderSubmit(osRequest);
            if (StringUtils.isNotEmpty(osResponse.getMessage())) {
                logger.warn("商家订单(" + mission.getMissionCode() + ")获取物流出问题跳过自动物流发货！原因：" + osResponse.getMessage());
            }
            List<WaybillResponse> waybillResponseList = osResponse.getData();
            if (waybillResponseList == null) {
                mission.setLogisticsNo(osResponse.getMessage());
            } else {
                WaybillResponse wbr = waybillResponseList.get(0);
                //运单号
                mission.setLogisticsNo(wbr.getWaybillNo());
                logger.warn("成功调用物流接口后余额：" + osResponse.getPayment().toString() +
                        " 订单号：" + wbr.getOrderNo() + " 运单号：" + wbr.getWaybillNo() + " errmsg:" + wbr.getRemark());
            }
        } else {
            //不发送物流信息，自动给商家账户打加2元余额，生成流水
            DetailEntity detailEntity = new DetailEntity();
            BigDecimal b = new BigDecimal(2);
            detailEntity.setUserId(sellerId);
            detailEntity.setUserName(sellerEntity.getContact());
            detailEntity.setMobile(sellerEntity.getMobile());
            detailEntity.setIsSeller(1);
            detailEntity.setType(16);
            detailEntity.setAmount(b);
            detailEntity.setBalance(sellerEntity.getBalance().add(b));
            detailEntity.setCode(mission.getMissionCode());
            detailEntity.setNote("商家订单(" + mission.getMissionCode() + ")未发物流返回金额" + 2 + "元");
            detailEntity.setCreateTime(new Date(System.currentTimeMillis()));
            detailService.insert(detailEntity); //生成流水

            SellerEntity sellerEntity1 = new SellerEntity();
            sellerEntity1.setId(sellerEntity.getId());
            sellerEntity1.setBalance(sellerEntity.getBalance().add(b));
            //更新商家余额
            sellerService.updateById(sellerEntity1);
        }

        UserEntity userEntity = userService.selectById(userId);
        BigDecimal stockMoney = userEntity.getStockMoney();
        BigDecimal freeMoney = userEntity.getFreeMoney();
        BigDecimal price = mission.getPrice();
        //加入商品的价格
        userEntity.setFreeMoney(userEntity.getFreeMoney().add(price));
        if (userService.updateById(userEntity)) {
            // 给刷手账户可体现余额加上商品价格的余额
            DetailEntity detailEntity2 = new DetailEntity();
            detailEntity2.setIsSeller(2);
            detailEntity2.setUserId(userEntity.getId());
            detailEntity2.setUserName(userEntity.getName());
            detailEntity2.setMobile(userEntity.getMobile());
            detailEntity2.setType(17);
            detailEntity2.setBeforeStock(stockMoney);
            detailEntity2.setStock(userEntity.getStockMoney());
            detailEntity2.setBeforeFree(freeMoney);
            detailEntity2.setFree(userEntity.getFreeMoney());
            detailEntity2.setAmount(new BigDecimal("+" + price));
            detailEntity2.setCode(mission.getMissionCode());
            detailEntity2.setNote("商家订单(" + mission.getMissionCode() + ")确认发货刷手可提现余额增加的商品金额：" + price + "元");
            detailEntity2.setCreateTime(new Date(System.currentTimeMillis()));
            detailService.insert(detailEntity2);
        }



    }
}
