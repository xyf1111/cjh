package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.ViewEntity;

import java.util.Map;

/**
 * 标签单浏览表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-12-07 17:26:10
 */
public interface ViewService extends IService<ViewEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

