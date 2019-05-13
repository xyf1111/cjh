package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.WxStepDao;
import io.chaoshua.modules.background.entity.WxStepEntity;
import io.chaoshua.modules.background.service.WxStepService;


@Service("wxStepService")
public class WxStepServiceImpl extends ServiceImpl<WxStepDao, WxStepEntity> implements WxStepService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WxStepEntity> page = this.selectPage(
                new Query<WxStepEntity>(params).getPage(),
                new EntityWrapper<WxStepEntity>()
                .orderBy("create_time",false)
        );

        return new PageUtils(page);
    }

}
