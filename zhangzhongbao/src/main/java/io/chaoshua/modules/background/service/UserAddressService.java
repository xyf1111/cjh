package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.UserAddressEntity;
import io.chaoshua.modules.background.entity.UserImgEntity;

import java.util.Map;

/**
 * 刷手地址
 *
 * @author lwy
 * @email 825068490@gmail.com
 * @date 2019-01-11 18:55:26
 */
public interface UserAddressService extends IService<UserAddressEntity> {

    UserAddressEntity selectByUserId(Long userId);

}

