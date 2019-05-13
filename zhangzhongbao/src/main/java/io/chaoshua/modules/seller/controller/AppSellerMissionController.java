package io.chaoshua.modules.seller.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.*;
import io.chaoshua.common.yto.YtoUtil;
import io.chaoshua.common.yto.req.OrderRequest;
import io.chaoshua.common.yto.req.OrderSubmitRequest;
import io.chaoshua.common.yto.resp.BalanceGetResponse;
import io.chaoshua.common.yto.resp.OrderSubmitResponse;
import io.chaoshua.common.yto.resp.WaybillResponse;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.dao.mission.MissionDao;
import io.chaoshua.modules.background.dao.mission.MissionDetailDao;
import io.chaoshua.modules.background.dao.task.TaskDao;
import io.chaoshua.modules.background.entity.*;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.chaoshua.modules.background.enums.MissionStatusEnum;
import io.chaoshua.modules.background.enums.TaskStyleEnum;
import io.chaoshua.modules.background.service.*;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.modules.background.service.mission.MissionService;
import io.chaoshua.modules.seller.dto.ExportMissionDTO;
import io.chaoshua.modules.seller.vo.ExportMissionVO;
import io.chaoshua.modules.weixin.service.SendInfoController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-18 10:51:16
 */
@RestController
@RequestMapping("/app/seller/mission")
public class AppSellerMissionController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MissionService missionService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private MissionDetailService missionDetailService;

    @Autowired(required = false)
    private MissionDetailDao missionDetailDao;

    @Autowired(required = false)
    private TaskDao taskDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ViewService viewService;

    @Autowired
    private SendInfoController sendInfoController;

    @Autowired
    private DetailService detailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public R list(@RequestParam Map<String, Object> params, @ApiIgnore @RequestAttribute("userId") Long sellerId) {
        params.put("sellerId", sellerId);
        PageUtils page = missionService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MissionEntity mission = missionService.selectById(id);
        MissionDetailEntity missionDetail = missionDetailService.selectOne(new EntityWrapper<MissionDetailEntity>().eq("mission_id", id));
        Map<String, Object> missions = new HashedMap();
        missions.put("mission", mission);
        missions.put("missionDetail", missionDetail);
        List<ViewEntity> list = viewService.selectList(new EntityWrapper<ViewEntity>().eq("mission_id", id).eq("user_id", mission.getUserId()));
        for (ViewEntity viewEntity : list) {
            List<String> list1 = Arrays.asList(viewEntity.getImglist().split(","));
            if (viewEntity.getDay() == 1) {
                missions.put("view1", list1);
            } else if (viewEntity.getDay() == 2) {
                missions.put("view2", list1);
            }
        }
        return R.ok().put("mission", missions);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MissionEntity mission) {
        missionService.insert(mission);
        return R.ok();
    }

    /**
     * 修改是否标记
     */
    @RequestMapping("/update")
    public R update(@RequestBody MissionEntity mission) {
        if (mission.getMark() != null) {
            mission.setIsMark(2);
        } else {
            mission.setIsMark(1);
        }
        missionService.updateById(mission);
        return R.ok();
    }

    /**
     * 修改订单
     */
    @RequestMapping("/updateRole")
    @Transactional
    public R updateRole(@RequestBody MissionEntity mission) {
        if (mission.getRole() == 2) {
            MissionDetailEntity missionDetailEntity = new MissionDetailEntity();
            missionDetailEntity.setPublishTime(new Date());
            missionDetailEntity.setMissionId(mission.getId());
            missionDetailService.updateById(missionDetailEntity);
        } else if (mission.getRole() == 3) {
            UserEntity userEntity = userService.selectById(mission.getUserId());
            if (userEntity.getOpenId() != null) {//推送消息
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("您好，您有订单（类目为:").append(mission.getShopCategory()).append(")").append("审核未通过");
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append("接单拒绝原因:").append(mission.getRefuseNote());
                sendInfoController.sendInfo(userEntity.getOpenId(), stringBuilder.toString(), mission.getMissionCode(),
                        DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN), stringBuilder1.toString());
            }
        }
        missionService.updateById(mission);
        return R.ok();
    }

    /**
     * 商家确认收货地址(废弃)
     */
    @RequestMapping("/confirmAddress")
    @Transactional
    public R confirmAddress(@RequestBody MissionEntity mission) {
        //校验地址正确性！
        String address = mission.getLogisticsAddress();
        if (address == null) {
            return R.error("收货地址必填！");
        }
        if (mission.getLogisticsPhone() == null) {
            return R.error("收货电话必填！");
        }
        if (mission.getUserName() == null) {
            return R.error("收货人必填！");
        }
        MissionEntity newMissionEntity = new MissionEntity();
        newMissionEntity.setId(mission.getId());
        newMissionEntity.setIsLogistics(mission.getIsLogistics());
        String[] addresses = address.split(",");
        if(addresses.length != 4) {
            return R.error("地址格式输入有误！地址请按照：‘省,市,县(区),详细地址’的格式中间以,隔开");
        }
        newMissionEntity.setLogisticsAddress(address);
        newMissionEntity.setLogisticsPhone(mission.getLogisticsPhone());
        newMissionEntity.setUserName(mission.getUserName());
        missionService.updateById(newMissionEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        missionService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 获取认证信息
     */
    public R getAuth(@PathVariable("missionId") Long missionId) {
        MissionDetailEntity detailEntity = missionDetailService.getAuthByMissionId(missionId);
        return R.ok().put("mission", detailEntity);
    }

    /**
     * 获取刷手过往记录
     */
    @RequestMapping("/getHistory")
    public R getHistory(@RequestParam Map<String, Object> params) {
        PageUtils page = missionService.getHistoryByMissionId(params);
        return R.ok().put("page", page);
    }

    /**
     * 根据userId获取刷手过往记录
     */
    @RequestMapping("/getHistoryByUserId")
    public R getHistoryByUserId(@RequestParam Map<String, Object> params) {
        PageUtils page = missionService.getHistoryByUserId(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取指定评价与追评列表
     */
    @RequestMapping("/getAppointList")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public R getAppointList(@RequestParam Map<String, Object> params, @ApiIgnore @RequestAttribute("userId") Long sellerId) {
        params.put("sellerId", sellerId);
        PageUtils page = missionService.getAppointList(params);
        return R.ok().put("page", page);
    }


    /**
     * 撤销评价与追评
     */
    @RequestMapping("/revokeComment")
    public R revokeComment(@RequestBody MissionEntity missionEntity) {
        return missionService.revokeComment(missionEntity);
    }

    /**
     * 修改打款状态
     */
    @RequestMapping("/updates")
    public R updates(@RequestBody Long[] ids) {
        try {
            List<MissionEntity> list = new ArrayList<>();
            for (int i = 0; i < ids.length; i++) {
                MissionEntity mission = new MissionEntity();
                mission.setId(ids[i]);
                MissionEntity missionEntity = missionService.selectById(ids[i]);
                if (MissionStatusEnum.PLATFORM_CHECK.getValue().equals(missionEntity.getStatus())) {
                    list.add(mission);
                }
                UserEntity userEntity = userService.selectById(missionEntity.getUserId());
                /*sendInfoController.sendInfo(userEntity.getOpenId(), "您好，您有订单收到商家汇款，请及时付款",
                        missionEntity.getMissionCode(), DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN), "请在5分钟内完成付款并上传付款截图");*/
                sendInfoController.sendInfo(userEntity.getOpenId(), "您好，您有订单收到平台的打款。",
                        missionEntity.getMissionCode(), DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN), "感谢您的配合！");
            }
            missionService.updates(list);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(500, "系统错误!");
        }
    }

    /**
     * 修改发货状态( `status` int(1) DEFAULT NULL COMMENT '订单状态：订单待拍下->订单已拍下(刷手已付款)->平台已确认->商家已发货->刷手已收货（结单）',)
     * @param id
     * @return
     */
    @RequestMapping("/status")
    @LocalLock
    public R status(@RequestBody String[] id) {
        List<MissionEntity> list = new ArrayList<>();
        Joiner j = Joiner.on(",");
        logger.warn("修改发货状态收到ids:" + j.join(id));
        // id去重复操作
        ImmutableList<String> ids = ImmutableSet.copyOf(id).asList();
        for(int i = 0; i < ids.size() - 1; i++) {
            String isLogistics = id[id.length - 1];
            MissionEntity mission = missionService.selectById(ids.get(i));
            if (mission.getStatus() == 6) {
                //已经是商家确认发货状态！重复提交了！
                logger.warn("mission=" + ids.get(i) + "已发经发货了！重复提交！");
                continue;
            }
            mission.setId(Long.parseLong(ids.get(i)));
            mission.setIsLogistics(Integer.parseInt(isLogistics));
            missionService.updateStatusAndSendLogistics(mission);
            list.add(mission);
            logger.warn("修改mission=" + ids.get(i) + "发货状态为商家发货！");
        }
        /*for (int i = 0; i < id.length - 1; i++) {
            String isLogistics = id[id.length - 1];
            MissionEntity mission = missionService.selectById(id[i]);
            mission.setId(Long.parseLong(id[i]));
            mission.setIsLogistics(Integer.parseInt(isLogistics));
            missionService.updateStatusAndSendLogistics(mission);
            list.add(mission);
            logger.warn("修改mission=" + id[i] + "发货状态为商家发货！");
        }*/
        missionService.updateBatchById(list);
        return R.ok();
    }

    /**
     * 打款信息导出
     *
     * @param response response
     * @throws IOException
     */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response, @RequestParam String params) throws Exception {
        params = URLDecoder.decode(params, "utf-8");
        Map<String, Object> parseMap = JsonUtils.parseMap(params);
        String str = parseMap.get("ids").toString();
        List<MissionEntity> missionEntities = new ArrayList<>();
        if (str != null && str.length() > 0) {
            str = str.replace("[", "").replace("]", "").trim();
            String[] ids = str.split(",");
            for (int i = 0; i < ids.length; i++) {
                missionEntities.add(missionService.selectById(ids[i].trim()));
            }
        }
        String fileName = null;
        List<String> headList = new ArrayList<>();
        List<String[]> list = new LinkedList<>();
        //文件名称
        fileName = "" + System.currentTimeMillis() + "-订单信息导出";
        //头标题 "任务编号", "任务类型", "宝贝款号", "淘宝账号", "任务单订单号", "淘宝订单编号", "订单金额", "任务状态", "任务生成时间", "任务执行时间","是否指定评语","是否指定追评"
        headList.add("任务编号");
        headList.add("任务类型");
        headList.add("宝贝款号");
        headList.add("淘宝账号");
        headList.add("任务单订单号");
        headList.add("淘宝订单编号");
        headList.add("订单金额");
        headList.add("任务状态");
        headList.add("任务生成时间");
        headList.add("任务执行时间");
        headList.add("是否指定评语");
        headList.add("是否指定追评");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (MissionEntity missionEntity : missionEntities) {
            //获取商品详情
            MissionDetailEntity missionDetailEntity = missionDetailDao.select(missionEntity.getId());
            list.add(new String[]{
                    missionEntity.getTaskId().toString(),
                    missionEntity.getTaskStyleStr(),
                    missionDetailEntity.getStyle(),
                    missionEntity.getTaobao(),
                    missionEntity.getTaskId().toString(),
                    missionEntity.getTaobaoCode(),
                    missionEntity.getPrice().toString(),
                    missionEntity.getStatusStr(),
                    sdf.format(missionEntity.getCreateTime()),
                    sdf.format(missionEntity.getMissionTime()),
                    missionDetailEntity.getIsCommentStr(),
                    missionDetailEntity.getIsAddComStr(),
            });
        }
        Workbook workbook = ExcelUtils.createExcelFile(fileName, headList, list);
        if (workbook != null) {
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
            workbook.write(response.getOutputStream());
        }
    }

    /**
     * 导出提现信息
     *
     * @param response response
     * @throws IOException
     */
    @RequestMapping(value = "/exportExcel1")
    public void exportExcel1(HttpServletResponse response, @RequestParam String params) throws IOException {
        params = URLDecoder.decode(params, "utf-8");
        ExportMissionVO vo = JsonUtils.parseObject(params, ExportMissionVO.class);
        if (vo == null) {
            throw new RRException("参数错误");
        }
        if (vo.getIds().size() == 0) {
            throw new RRException("选中记录为空");
        }
        Integer excelType = vo.getExcelType();
        List<ExportMissionDTO> list = missionService.queryExportMissionList(excelType, vo.getIds());

        BigDecimal sumPrice = new BigDecimal(0);
        Integer sumNumber = 0;
        BigDecimal sumPay = new BigDecimal(0);

        String fileName = null;
        List<String> headList = new ArrayList<>();
        List<String[]> list1 = new LinkedList<>();
        if (excelType == 1) {
            //文件名称
            fileName = "" + System.currentTimeMillis() + "-招商银行提现列表";
            //头标题 "收款账户", "收款户名", "转账金额", "备注", "收款银行", "收款银行支行", "收款省/直辖市", "收款市县", "转出账号/卡", "转账模式"
            headList.add("收款账户");
            headList.add("收款户名");
            headList.add("转账金额");
            headList.add("备注");
            headList.add("收款银行");
            headList.add("收款银行支行");
            headList.add("收款省/直辖市");
            headList.add("收款市县");
            headList.add("转出账号/卡");
            headList.add("转账模式");
            for (ExportMissionDTO mission : list) {
                list1.add(new String[]{
                        mission.getCardNumber(),
                        mission.getHolder(),
                        mission.getPrice().toString(),
                        "", mission.getBank(), "", "", "", "", "实时"
                });
            }
        } else if (excelType == 2) {
            //文件名称
            fileName = "" + System.currentTimeMillis() + "-浦发提现列表";
            //头标题 "银行", "卡号", "持卡人", "金额"
            headList.add("银行");
            headList.add("卡号");
            headList.add("持卡人");
            headList.add("金额");

            for (ExportMissionDTO mission : list) {
                list1.add(new String[]{
                        mission.getBank(),
                        mission.getCardNumber(),
                        mission.getHolder(),
                        mission.getPrice().toString()
                });
            }
        } else if (excelType == 3) {
            //文件名
            fileName = "" + System.currentTimeMillis() + "-任务列表导出";
            //头标题 "平台任务编号", "任务类型", "店铺", "淘宝账号", "收货人姓名", "银行卡号", "淘宝订单号", "订单金额", "任务状态", "任务生成时间", "任务执行时间", "是否指定评价", "是否指定追评", "任务佣金", "浏览佣金", "总佣金"
            headList.add("平台任务编号");
            headList.add("任务类型");
            headList.add("店铺");
            headList.add("淘宝账号");
            headList.add("收货人姓名");
            headList.add("银行卡号");
            headList.add("淘宝订单号");
            headList.add("订单金额");
            headList.add("任务状态");
            headList.add("任务生成时间");
            headList.add("任务执行时间");
            headList.add("是否指定评价");
            headList.add("是否指定追评");
            headList.add("任务佣金");
            headList.add("浏览佣金");
            headList.add("总佣金");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (ExportMissionDTO mission : list) {
                //总数
                sumNumber += mission.getAmount();
                //总商品价格
                sumPrice = sumPrice.add(mission.getPrice());
                //总佣金价格
                sumPay = sumPay.add((mission.getSellerPay().add(mission.getViewPay())).multiply(new BigDecimal(mission.getAmount())));
                list1.add(new String[]{
                        mission.getTaskId().toString(), TaskStyleEnum.getNameByValue(mission.getTaskStyle()),
                        mission.getShopName(), mission.getTaobao(), mission.getUserName(), mission.getCardNumber(),
                        mission.getTaobaoCode(), mission.getPrice().toString(),
                        MissionStatusEnum.getNameByValue(mission.getStatus()),
                        sdf.format(mission.getCreateTime()), sdf.format(mission.getMissionTime()),
                        mission.getIsCommentStr(), mission.getIsAddComStr(),
                        mission.getSellerPay().toString(), mission.getViewPay().toString(),
                        mission.getSellerPay().add(mission.getViewPay()).toString()
                });
            }
            list1.add(new String[]{
                    "总计", "", "", "", "", "", "", sumPrice.toString(), sumNumber.toString(), "", "", "", "", "", "", sumPay.toString()
            });
        } else if (excelType == 4) {
            //文件名
            fileName = "" + System.currentTimeMillis() + "-订单列表导出";
            //头标题 "平台任务编号", "任务类型", "店铺", "淘宝账号", "收货人姓名", "银行卡号", "淘宝订单号", "订单金额", "任务状态", "任务生成时间", "任务执行时间", "是否指定评价", "是否指定追评", "任务佣金", "浏览佣金", "总佣金"
            headList.add("平台任务编号");
            headList.add("任务类型");
            headList.add("店铺");
            headList.add("淘宝账号");
            headList.add("收货人姓名");
            headList.add("银行卡号");
            headList.add("淘宝订单号");
            headList.add("订单金额");
            headList.add("任务状态");
            headList.add("任务生成时间");
            headList.add("任务执行时间");
            headList.add("是否指定评价");
            headList.add("是否指定追评");
            headList.add("任务佣金");
            headList.add("浏览佣金");
            headList.add("总佣金");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 总数
            sumNumber = list.size();
            for (ExportMissionDTO mission : list) {
                // 总订单价格
                sumPrice = sumPrice.add(mission.getPrice());
                BigDecimal sellerPayPee = mission.getSellerPay().divide(new BigDecimal(mission.getAmount()), 2);
                BigDecimal viewPayPee = mission.getViewPay().divide(new BigDecimal(mission.getAmount()), 2);
                // 总佣金价格
                sumPay = sumPay.add(sellerPayPee.add(viewPayPee));

                list1.add(new String[]{
                        mission.getTaskId().toString(), TaskStyleEnum.getNameByValue(mission.getTaskStyle()),
                        mission.getShopName(), mission.getTaobao(), mission.getUserName(),
                        mission.getCardNumber(), mission.getTaobaoCode(), mission.getPrice().toString(),
                        MissionStatusEnum.getNameByValue(mission.getStatus()),
                        sdf.format(mission.getCreateTime()),
                        sdf.format(mission.getMissionTime()),
                        mission.getIsCommentStr(),
                        mission.getIsAddComStr(),
                        sellerPayPee.toString(),
                        viewPayPee.toString(),
                        sellerPayPee.add(viewPayPee).toString()
                });
            }
            list1.add(new String[]{
                    "总计", "", "", "", "", "", "", sumPrice.toString(), sumNumber.toString(), "", "", "", "", "", "", sumPay.toString()
            });
        }
        Workbook workbook = ExcelUtils.createExcelFile(fileName, headList, list1);
        if (workbook != null) {
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
            workbook.write(response.getOutputStream());
        }
    }

    /**
     * 发货管理任务信息表单导出
     *
     * @param response response
     * @throws IOException
     */
    @RequestMapping(value = "/exportExcel2")
    public void exportExcel2(HttpServletResponse response, @RequestParam String params) throws IOException {
        params = URLDecoder.decode(params, "utf-8");
        Map<String, Object> parseMap = JsonUtils.parseMap(params);
        String str = parseMap.get("ids").toString();
        List<MissionEntity> list = new ArrayList<>();
        if (str != null && str.length() > 0) {
            str = str.replace("[", "").replace("]", "").trim();
            String[] ids = str.split(",");
            for (int i = 0; i < ids.length; i++) {
                list.add(missionService.selectById(ids[i].trim()));
            }
        }
        BigDecimal sumPrice = new BigDecimal(0);
        Integer sumNumber = 0;
        BigDecimal sumPay = new BigDecimal(0);

        String fileName = null;
        List<String> headList = new ArrayList<>();
        List<String[]> list1 = new LinkedList<>();
        //文件名称
        fileName = "" + System.currentTimeMillis() + "-任务佣金信息";
        //头标题 "任务类型", "店铺", "淘宝账号", "收货人名字", "银行卡号", "淘宝订单号","订单金额","任务状态", "任务生成时间", "任务执行时间","是否指定评价","是否指定追评","任务佣金","浏览佣金","总佣金"
        headList.add("任务类型");
        headList.add("店铺");
        headList.add("淘宝账号");
        headList.add("收货人名字");
        headList.add("收货人电话");
        headList.add("收货地址");
        headList.add("物流公司");
        headList.add("物流单号");
        headList.add("银行卡号");
        headList.add("淘宝订单号");
        headList.add("任务状态");
        headList.add("订单金额");
        headList.add("任务生成时间");
        headList.add("任务执行时间");
        headList.add("是否指定评价");
        headList.add("是否指定追评");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (MissionEntity missionEntity : list) {
            TaskEntity taskEntity = taskDao.selectById(missionEntity.getTaskId());
            MissionDetailEntity missionDetailEntity = missionDetailDao.select(missionEntity.getId());
            sumNumber += taskEntity.getAmount();//总数
            sumPrice = sumPrice.add(taskEntity.getPrice().multiply(new BigDecimal(taskEntity.getAmount())));//总商品价格
            sumPay = sumPay.add((taskEntity.getSellerPay().add(taskEntity.getViewPay())).multiply(new BigDecimal(taskEntity.getAmount())));//总佣金价格
            list1.add(new String[]{
                    taskEntity.getTaskStyleStr(),
                    taskEntity.getShopName(),
                    missionEntity.getTaobao(),
                    missionEntity.getUserName(),
                    missionEntity.getLogisticsPhone(),
                    missionEntity.getLogisticsAddress(),
                    missionEntity.getLogisticsComp(),
                    missionEntity.getLogisticsNo(),
                    missionDetailEntity.getCardNumber(),
                    missionEntity.getTaobaoCode(),
                    missionEntity.getStatusStr(),
                    taskEntity.getPrice().toString(),
                    sdf.format(missionEntity.getCreateTime()),
                    sdf.format(missionEntity.getMissionTime()),
                    missionDetailEntity.getIsCommentStr(),
                    missionDetailEntity.getIsAddComStr(),
            });
        }
        Workbook workbook = ExcelUtils.createExcelFile(fileName, headList, list1);
        if (workbook != null) {
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
            workbook.write(response.getOutputStream());
        }
    }
}
