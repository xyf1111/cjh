package io.chaoshua.modules.background.dao.mission;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.chaoshua.modules.app.vo.MissionVo;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.chaoshua.modules.job.entity.TaskMissionEntity;
import io.chaoshua.modules.seller.dto.ExportMissionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-18 10:51:16
 */
@Mapper
public interface MissionDao extends BaseMapper<MissionEntity> {

    /**
     * 查询列表
     *
     * @param pagination
     * @param params
     * @return
     */
    List<MissionEntity> getList(Pagination pagination, Map<String, Object> params);

    /**
     * 查询用户过往记录
     *
     * @param params
     * @return
     */
    List<MissionEntity> getHistoryByMissionId(Pagination pagination, Map<String, Object> params);

    /**
     * 查询用户根据userId过往记录
     *
     * @param userId
     * @return
     */
    List<MissionEntity> getHistoryByUserId(Pagination pagination, Long userId);

    /**
     * 修改发货状态
     */
    void updateStatusReceiveGood();


    /**
     * 获取指定评价与追评列表
     *
     * @param pagination
     * @param params
     * @return
     */

    List<MissionEntity> getAppointList(Pagination pagination, Map<String, Object> params);

    /**
     * 批量修改打款状态
     *
     * @param missionEntities
     */
    void updates(List<MissionEntity> missionEntities);

    /**
     * 根据role与status获取列表
     *
     * @param params
     * @return
     */

    List<TaskMissionEntity> getAllListByRole(Map params);

    /**
     * 导出列表
     *
     * @param excelType 导出类型
     * @param ids       要导出订单ID
     * @return
     */
    List<ExportMissionDTO> queryExportMissionList(@Param(value = "excelType") Integer excelType, @Param(value = "ids") List<Long> ids);
    /*******************************app开始************************************************/
    /**
     * 根据用户ID获取不可提现列表
     *
     * @param userId
     * @return
     */
    List<MissionEntity> getStateMoneyListByUserId(Pagination pagination, Long userId);

    /**
     * 根据用户条件查询订单列表
     *
     * @param pagination
     * @param params
     * @return
     */
    List<MissionEntity> getVoListByMap(Pagination pagination, Map<String, Object> params);

    /**
     * 根据用户Id查询过往记录
     *
     * @return
     */
    List<MissionEntity> getHistoryListByUserId(Pagination pagination, Map<String, Object> params);

    /**
     * 根据用户Id查询过往记录
     *
     * @return
     */
    List<MissionEntity> getHistoryListByUserId1(Map<String, Object> params);

    /**
     * 获取订单详情
     *
     * @param missionId
     * @return
     */
    MissionVo getMissionDetail(Long missionId);

    /**
     * 获取用户最近一次接单时间
     * @param userId
     * @return
     */
    Date getUserLastOrderTime(Long userId);

    /*******************************app结束************************************************/

}
