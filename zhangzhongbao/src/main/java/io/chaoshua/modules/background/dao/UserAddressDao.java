package io.chaoshua.modules.background.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.chaoshua.modules.background.entity.UserAddressEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 刷手淘宝收货地址
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-01-11 18:55:26
 */
@Mapper
public interface UserAddressDao extends BaseMapper<UserAddressEntity> {

    UserAddressEntity selectByUserId(Long userId);

}
