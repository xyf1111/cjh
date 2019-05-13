package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.ViewDao;
import io.chaoshua.modules.background.entity.ViewEntity;
import io.chaoshua.modules.background.service.ViewService;


@Service("viewService")
public class ViewServiceImpl extends ServiceImpl<ViewDao, ViewEntity> implements ViewService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ViewEntity> page = this.selectPage(
                new Query<ViewEntity>(params).getPage(),
                new EntityWrapper<ViewEntity>()
        );

        return new PageUtils(page);
    }

}
