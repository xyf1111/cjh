package io.chaoshua.modules.background.service.mission;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.vo.MissionVo;
import io.chaoshua.modules.app.vo.user.StateMoneyVo;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.seller.dto.ExportMissionDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-18 10:51:16
 */
public interface MissionService extends IService<MissionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户过往记录
     *
     * @param params
     * @return
     */
    PageUtils getHistoryByMissionId(Map<String, Object> params);

    /**
     * 根据UserId查询用户过往记录
     *
     * @param params
     * @return
     */
    PageUtils getHistoryByUserId(Map<String, Object> params);

    /**
     * 获取指定评价与追评列表
     *
     * @param params
     * @param params
     * @return
     */
    PageUtils getAppointList( Map<String,Object> params);

    /**
     * 撤销指定与追评评价
     *
     * @param missionEntity
     * @return
     */
    R revokeComment(MissionEntity missionEntity);

    /**
     * 审核订单
     *
     * @param missionEntity
     * @return
     */
    R updateRole(MissionEntity missionEntity);

    /**
     * 确认价格
     *
     * @param id    订单ID
     * @param price 订单价格
     */
    void confirmPrice(Long id, BigDecimal price);

    /**
     * 获取导出订单信息
     *
     * @param excelType 类型
     * @param ids       订单ID列表
     * @return
     */
    List<ExportMissionDTO> queryExportMissionList(Integer excelType, List<Long> ids);

    /******************************* app开始************************************************/
    /**
     * 根据用户ID获取不可提现列表
     *
     * @param params
     * @return
     */
    List<StateMoneyVo> getStateMoneyListByUserId(Map<String,Object> params,Long userId);


    /**
     * 用户接单
     *
     * @param taskId
     * @return
     */
    AppResult submitMission(Long taskId,Long userId);

    /**
     * 获取用户接单列表
     *
     * @param params
     * @return
     */
    Page<MissionEntity> getHistoryListByUserId(Map<String,Object> params);

    /**
     * 获取订单详情
     *
     * @param missionId
     * @return
     */
    MissionVo getMissionDetail(Long missionId);

    /**
     * 批量修改打款状态
     */
    void updates(List<MissionEntity> list);

    /**
     * 批量发货物流状态
     */
    void updateStatusAndSendLogistics(MissionEntity mission);

    /**
     * 平台确认信息
     * @param m
     */
    void platformConfirm(MissionEntity m);

    /**
     * 获取用户最近一次接单时间
     * @param userId
     * @return
     */
    Date getUserLastOrderTime(Long userId);
    /*******************************app结束************************************************/
}

