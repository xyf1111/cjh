package io.chaoshua.modules.background.service.impl;

import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.ImgDao;
import io.chaoshua.modules.background.entity.ImgEntity;
import io.chaoshua.modules.background.service.ImgService;


@Service("imgService")
public class ImgServiceImpl extends ServiceImpl<ImgDao, ImgEntity> implements ImgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String type = null;
        String status = null;
        String name = null;
        if (params.containsKey("type")){
            type = params.get("type").toString();
        }
        if (params.containsKey("status")){
            status = params.get("status").toString();
        }
        if (params.containsKey("name")){
            name = params.get("name").toString();
        }
        Page<ImgEntity> page = this.selectPage(
                new Query<ImgEntity>(params).getPage(),
                new EntityWrapper<ImgEntity>()
                .eq(!StringUtils.isEmpty(type),"type",type)
                .eq(!StringUtils.isEmpty(status),"status",status)
                .eq(!StringUtils.isEmpty(name),"name",name)
                .orderBy("sort",true)
        );

        return new PageUtils(page);
    }

}
