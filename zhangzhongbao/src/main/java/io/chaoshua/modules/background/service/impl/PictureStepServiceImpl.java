package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.PictureStepDao;
import io.chaoshua.modules.background.entity.PictureStepEntity;
import io.chaoshua.modules.background.service.PictureStepService;


@Service("pictureStepService")
public class PictureStepServiceImpl extends ServiceImpl<PictureStepDao, PictureStepEntity> implements PictureStepService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PictureStepEntity> page = this.selectPage(
                new Query<PictureStepEntity>(params).getPage(),
                new EntityWrapper<PictureStepEntity>()
        );

        return new PageUtils(page);
    }

}
