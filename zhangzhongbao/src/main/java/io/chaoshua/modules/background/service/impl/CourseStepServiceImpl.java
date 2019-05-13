package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.CourseStepDao;
import io.chaoshua.modules.background.entity.CourseStepEntity;
import io.chaoshua.modules.background.service.CourseStepService;


@Service("courseStepService")
public class CourseStepServiceImpl extends ServiceImpl<CourseStepDao, CourseStepEntity> implements CourseStepService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CourseStepEntity> page = this.selectPage(
                new Query<CourseStepEntity>(params).getPage(),
                new EntityWrapper<CourseStepEntity>()
        );

        return new PageUtils(page);
    }

}
