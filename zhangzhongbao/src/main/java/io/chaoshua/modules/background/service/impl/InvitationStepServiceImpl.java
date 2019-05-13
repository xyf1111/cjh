package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.InvitationStepDao;
import io.chaoshua.modules.background.entity.InvitationStepEntity;
import io.chaoshua.modules.background.service.InvitationStepService;


@Service("invitationStepService")
public class InvitationStepServiceImpl extends ServiceImpl<InvitationStepDao, InvitationStepEntity> implements InvitationStepService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<InvitationStepEntity> page = this.selectPage(
                new Query<InvitationStepEntity>(params).getPage(),
                new EntityWrapper<InvitationStepEntity>()
        );

        return new PageUtils(page);
    }

}
