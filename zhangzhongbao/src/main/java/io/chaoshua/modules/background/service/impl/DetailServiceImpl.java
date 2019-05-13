package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.DetailDao;
import io.chaoshua.modules.background.entity.DetailEntity;
import io.chaoshua.modules.background.service.DetailService;


@Service("detailService")
public class DetailServiceImpl extends ServiceImpl<DetailDao, DetailEntity> implements DetailService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DetailEntity> page =  new Query<DetailEntity>(params).getPage();
        page.setRecords(baseMapper.getList(page,params));
        return new PageUtils(page);
    }

    @Override
    public PageUtils getListByPid(Map<String, Object> params) {
        Page<DetailEntity> page =  new Query<DetailEntity>(params).getPage();
        page.setRecords(baseMapper.getListByPid(page,params));
        return new PageUtils(page);
    }
}
