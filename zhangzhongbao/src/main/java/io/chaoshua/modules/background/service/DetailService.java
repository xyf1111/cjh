package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.DetailEntity;

import java.util.List;
import java.util.Map;

/**
 * 流水表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-18 11:54:46
 */
public interface DetailService extends IService<DetailEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 根据父级id获取流水列表
     * @param params
     * @return
     */
    PageUtils getListByPid(Map<String, Object> params);
}

