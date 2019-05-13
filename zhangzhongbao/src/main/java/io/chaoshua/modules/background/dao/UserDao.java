package io.chaoshua.modules.background.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.chaoshua.modules.background.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-27 15:45:30
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    UserEntity selectByUserId(Long id);

    /**
     * 根据用户id查询一级下线
     * @param id
     * @return
     */
    UserEntity selectLowUserByUserId(Long id);

    /**
     * 根据用户id查询二级下线
     * @param id
     * @return
     */
    UserEntity selectLowerUserByUserId(Long id);

    /**
     * 根据条件查询用户列表
     * @param params
     * @return
     */
    List<UserEntity> getList(Pagination pagination,Map<String,Object> params);

    /**
     * 获取最高级用户
     * @param highUserId
     * @return
     */
    List<UserEntity> getHighUserId(Long highUserId);

    /**
     * 获取下级列表
     * @param pagination
     * @param userId
     * @return
     */
    List<UserEntity> getFirstUserByUserId(Pagination pagination,Long userId);

    /**
     * 获取下下级列表
     * @param pagination
     * @param userId
     * @return
     */
    List<UserEntity> getSecondUserByUserId(Pagination pagination,Long userId);

    /**
     * 刷手总余额
     * @param params
     * @return
     */
    UserEntity getUserBalance(Map params);
}
