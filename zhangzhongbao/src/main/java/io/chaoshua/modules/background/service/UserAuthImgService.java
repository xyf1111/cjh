package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.UserAuthImgEntity;

import java.util.Map;

/**
 * 用户每日认证上传图片表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2019-01-11 21:09:11
 */
public interface UserAuthImgService extends IService<UserAuthImgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

