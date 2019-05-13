package io.chaoshua.modules.background.service.mission;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.AppStatus;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.form.AddCommentForm;
import io.chaoshua.modules.app.form.AuthPictureForm;
import io.chaoshua.modules.app.form.MissionRecGoodForm;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;

import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-20 17:52:57
 */
public interface MissionDetailService extends IService<MissionDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据订单id获取认证信息
     *
     * @param missionId
     * @return
     */
    MissionDetailEntity getAuthByMissionId(Long missionId);


    /**
     * 更新认证信息
     *
     * @param authPictureForm
     * @return
     */
    int updateAuthByMissionId(AuthPictureForm authPictureForm);


    /**
     * 撤销
     * @param missionDetailEntity
     * @return
     */
    R updateRemoveInfo(MissionDetailEntity missionDetailEntity);

    /***************************商家端开始***********************************************/
    /**
     * 指定评价/指定追评
     * @param missionDetailEntity
     * @return
     */
    R isComment(MissionDetailEntity missionDetailEntity);


    /***************************商家端结束***********************************************/

    /***************************刷手端开始***********************************************/
    /**
     * 完成指定追评
     * @param form
     * @return
     */
    AppStatus userUpdateAddComment(AddCommentForm form,Long userId);

    AppStatus userUpdateComment(MissionRecGoodForm form,Long userId);
    /***************************刷手端结束***********************************************/
}

