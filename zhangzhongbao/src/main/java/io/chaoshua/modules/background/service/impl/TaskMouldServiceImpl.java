package io.chaoshua.modules.background.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.task.TaskMouldDao;
import io.chaoshua.modules.background.entity.task.TaskMouldEntity;
import io.chaoshua.modules.background.service.task.TaskMouldService;


@Service("taskMouldService")
public class TaskMouldServiceImpl extends ServiceImpl<TaskMouldDao, TaskMouldEntity> implements TaskMouldService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String shopName = null;
        String style = null;
        if (params.containsKey("shopName")){
            shopName = params.get("shopName").toString();
        }
        if (params.containsKey("style")){
            style = params.get("style").toString();
        }
        Page<TaskMouldEntity> page = this.selectPage(
                new Query<TaskMouldEntity>(params).getPage(),
                new EntityWrapper<TaskMouldEntity>()
                .eq("seller_id",params.get("sellerId"))
                .like(!StringUtils.isEmpty(shopName),"shop_name",shopName)
                .like(!StringUtils.isEmpty(style),"style",style)
                .orderBy("create_time",false)

        );

        return new PageUtils(page);
    }

}
