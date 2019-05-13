package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.IntervalStepDao;
import io.chaoshua.modules.background.entity.IntervalStepEntity;
import io.chaoshua.modules.background.service.IntervalStepService;


@Service("intervalStepService")
public class IntervalStepServiceImpl extends ServiceImpl<IntervalStepDao, IntervalStepEntity> implements IntervalStepService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<IntervalStepEntity> page = this.selectPage(
                new Query<IntervalStepEntity>(params).getPage(),
                new EntityWrapper<IntervalStepEntity>()
        );

        return new PageUtils(page);
    }

}
