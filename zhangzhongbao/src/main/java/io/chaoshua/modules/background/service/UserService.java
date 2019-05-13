package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.form.LowUserForm;
import io.chaoshua.modules.app.vo.user.LowUserVo;
import io.chaoshua.modules.background.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-27 15:45:30
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据用户id查询所有上级
     * @param id
     * @return
     */
    List<UserEntity> selectByUserId(Long id);

    /**
     * 根据用户id查询一级下线
     * @param id
     * @return
     */
    List<UserEntity> selectLowUserByUserId(Long id);

    /**
     * 根据用户id查询二级下线
     * @param id
     * @return
     */
    List<UserEntity> selectLowerUserByUserId(Long id);

    /**
     * 根据条件查询用户列表
     * @param params
     * @return
     */
    PageUtils getList(Map<String,Object> params);

    /**
     * 批量修改黑白名单与是否禁用
     * @param params
     */
    R batchUpdate(Map<String,Object> params);

    /**
     * 获取最高级用户
     * @param highUserId
     * @return
     */
    List<UserEntity> getHighUserId(Long highUserId);

    /**
     * 修改刷手个人信息
     * @param userEntity
     * @return
     */
    R updateByUserId(UserEntity userEntity);

/************************app开始**************************************/
    /**
     * 获取下级列表
     * @param params
     * @param userId
     * @param type
     * @return
     */
    List<LowUserVo> getFirstUserByUserId(Map<String,Object> params,Long userId,Integer type);
/*********************************app结束**************************************************/
}

