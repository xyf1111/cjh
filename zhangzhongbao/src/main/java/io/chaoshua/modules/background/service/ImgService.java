package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.ImgEntity;

import java.util.Map;

/**
 * 示例表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-01-09 14:08:24
 */
public interface ImgService extends IService<ImgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

