package io.chaoshua.modules.background.service;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.MessageEntity;

import java.util.Map;

/**
 * 消息表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-11-20 15:51:03
 */
public interface MessageService extends IService<MessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

