package io.chaoshua.modules.background.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.note.NoteStepDao;
import io.chaoshua.modules.background.entity.note.NoteStepEntity;
import io.chaoshua.modules.background.service.note.NoteStepService;


@Service("noteStepService")
public class NoteStepServiceImpl extends ServiceImpl<NoteStepDao, NoteStepEntity> implements NoteStepService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<NoteStepEntity> page = this.selectPage(
                new Query<NoteStepEntity>(params).getPage(),
                new EntityWrapper<NoteStepEntity>()
                .eq("type",params.get("type"))
        );

        return new PageUtils(page);
    }

}
