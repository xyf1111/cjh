package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.UserImgEntity;

import java.util.Map;

/**
 * 刷手注册认证图片信息表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-01-11 18:55:26
 */
public interface UserImgService extends IService<UserImgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

