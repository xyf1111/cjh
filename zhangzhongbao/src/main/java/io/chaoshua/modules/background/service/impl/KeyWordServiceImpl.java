package io.chaoshua.modules.background.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.modules.background.dao.task.KeyWordDao;
import io.chaoshua.modules.background.entity.task.KeyWordEntity;
import io.chaoshua.modules.background.service.task.KeyWordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("keyWordService")
public class KeyWordServiceImpl extends ServiceImpl<KeyWordDao, KeyWordEntity> implements KeyWordService {


    @Override
    public List<KeyWordEntity> findByTaskId(Long taskId) {
        Map<String, Object> m = new HashMap<>();
        m.put("taskId", taskId);
        return baseMapper.getTaskKeyWordById(m);
    }

    @Override
    @Transactional
    public void saveByTaskMouldIId(Long taskMouldIId, String keywords) {
        //刪除
        Map<String, Object> m = new HashMap<>();
        m.put("taskMouldId", taskMouldIId);
        baseMapper.deleteByTaskId(m);
        //插入新的
        List<KeyWordEntity> list = new ArrayList<>();
        String[] kys = keywords.split(";");
        for(int i = 0 ; i < kys.length ; i++) {
            KeyWordEntity kwe = new KeyWordEntity();
            kwe.setKeyWord(kys[i]);
            kwe.setSort(i);
            kwe.setTaskMouldId(taskMouldIId);
            kwe.setCreateTime(new Date(System.currentTimeMillis()));
            list.add(kwe);
        }
        this.insertBatch(list);
    }

    @Override
    @Transactional
    public void saveByTaskId(Long taskId, String keywords, String keywordCounts) {
        //插入新的
        List<KeyWordEntity> list = new ArrayList<>();
        String[] kys = keywords.split(";");
        String[] counts = keywordCounts.split(";");
        for(int i = 0 ; i < kys.length ; i++) {
            int count = Integer.parseInt(counts[i]);
            if (count == 0) {
                continue;
            }
            KeyWordEntity kwe = new KeyWordEntity();
            kwe.setKeyWord(kys[i]);
            kwe.setAmount(count);
            kwe.setSort(i);
            kwe.setTaskId(taskId);
            kwe.setCreateTime(new Date(System.currentTimeMillis()));
            list.add(kwe);
        }
        this.insertBatch(list);
    }



}
