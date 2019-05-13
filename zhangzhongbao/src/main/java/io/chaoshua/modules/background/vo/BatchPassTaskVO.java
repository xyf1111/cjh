package io.chaoshua.modules.background.vo;

import java.util.List;

/**
 * 批量通过任务VO
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-04-01 14:37
 */
public class BatchPassTaskVO {
    /**
     * 任务ID列表
     */
    private List<Long> taskIds;
    /**
     * 客服ID
     */
    private Long inChargeId;
    /**
     * 客服名称
     */
    private String platformName;

    public List<Long> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Long> taskIds) {
        this.taskIds = taskIds;
    }

    public Long getInChargeId() {
        return inChargeId;
    }

    public void setInChargeId(Long inChargeId) {
        this.inChargeId = inChargeId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
}
