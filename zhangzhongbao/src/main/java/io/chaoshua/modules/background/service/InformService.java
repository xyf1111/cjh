package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.InformEntity;

import java.util.Map;

/**
 * 公告表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-27 11:29:58
 */
public interface InformService extends IService<InformEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

