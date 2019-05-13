package io.chaoshua.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.chaoshua.modules.sys.entity.SysUserEntity;
import io.chaoshua.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * 获取所有客服列表
	 * @param roleId
	 * @return
	 */
	List<SysUserEntity> getListByRole(Long roleId);
}
