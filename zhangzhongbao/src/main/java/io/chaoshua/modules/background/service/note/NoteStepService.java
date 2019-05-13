package io.chaoshua.modules.background.service.note;

import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.background.entity.note.NoteStepEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-25 16:41:54
 */
public interface NoteStepService extends IService<NoteStepEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

