package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.UserAuthImgDao;
import io.chaoshua.modules.background.entity.UserAuthImgEntity;
import io.chaoshua.modules.background.service.UserAuthImgService;


@Service("userAuthImgService")
public class UserAuthImgServiceImpl extends ServiceImpl<UserAuthImgDao, UserAuthImgEntity> implements UserAuthImgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserAuthImgEntity> page = this.selectPage(
                new Query<UserAuthImgEntity>(params).getPage(),
                new EntityWrapper<UserAuthImgEntity>()
        );

        return new PageUtils(page);
    }

}
