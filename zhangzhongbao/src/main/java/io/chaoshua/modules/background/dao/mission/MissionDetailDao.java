package io.chaoshua.modules.background.dao.mission;

import io.chaoshua.modules.app.form.AuthPictureForm;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-20 17:52:57
 */
@Mapper
public interface MissionDetailDao extends BaseMapper<MissionDetailEntity> {

    /**
     * 根据订单id获取认证信息
     *
     * @param missionId
     * @return
     */
    MissionDetailEntity getAuthByMissionId(Long missionId);

    /**
     * 获取订单详情信息
     *
     * @param id
     * @return
     */
    MissionDetailEntity select(Long id);

    /**
     * 获取用户银行卡信息
     * @param missionId
     * @return
     */
    MissionDetailEntity getBankByMissionId(Long missionId);

    /**
     * 更新认证信息
     * @param authPictureForm
     * @return
     */
    int updateAuthByMissionId(AuthPictureForm authPictureForm);
}
