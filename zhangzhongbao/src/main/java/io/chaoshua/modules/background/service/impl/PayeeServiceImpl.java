package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.PayeeDao;
import io.chaoshua.modules.background.entity.PayeeEntity;
import io.chaoshua.modules.background.service.PayeeService;


@Service("payeeService")
public class PayeeServiceImpl extends ServiceImpl<PayeeDao, PayeeEntity> implements PayeeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<PayeeEntity> page = this.selectPage(
                new Query<PayeeEntity>(params).getPage(),
                new EntityWrapper<PayeeEntity>()
        );

        return new PageUtils(page);
    }

}
