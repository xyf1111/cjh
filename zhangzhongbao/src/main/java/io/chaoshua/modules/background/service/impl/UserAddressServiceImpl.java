package io.chaoshua.modules.background.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.modules.background.dao.UserAddressDao;
import io.chaoshua.modules.background.entity.UserAddressEntity;
import io.chaoshua.modules.background.service.UserAddressService;
import org.springframework.stereotype.Service;

@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressDao, UserAddressEntity> implements UserAddressService {


    public UserAddressEntity selectByUserId(Long userId) {
        return baseMapper.selectByUserId(userId);
    }


}
