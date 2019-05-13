package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.BankDao;
import io.chaoshua.modules.background.entity.BankEntity;
import io.chaoshua.modules.background.service.BankService;


@Service("bankService")
public class BankServiceImpl extends ServiceImpl<BankDao, BankEntity> implements BankService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BankEntity> page = this.selectPage(
                new Query<BankEntity>(params).getPage(),
                new EntityWrapper<BankEntity>()
                .orderBy("create_time",false)
        );

        return new PageUtils(page);
    }

}
