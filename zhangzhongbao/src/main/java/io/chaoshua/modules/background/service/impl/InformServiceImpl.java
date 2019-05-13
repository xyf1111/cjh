package io.chaoshua.modules.background.service.impl;

import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.InformDao;
import io.chaoshua.modules.background.entity.InformEntity;
import io.chaoshua.modules.background.service.InformService;


@Service("informService")
public class InformServiceImpl extends ServiceImpl<InformDao, InformEntity> implements InformService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = null;
        if (params.containsKey("title")){
            title = params.get("title").toString();
        }
        Page<InformEntity> page = this.selectPage(
                new Query<InformEntity>(params).getPage(),
                new EntityWrapper<InformEntity>()
                .eq("receiver_list",params.get("receiverList"))
               .like(!StringUtils.isEmpty(title),"title",title)
                .orderBy("create_time",false)
        );

        return new PageUtils(page);
    }

}
