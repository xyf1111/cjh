package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by dws on 2018/11/21 0021.
 */
@ApiModel(value = "任务列表表单")
public class TaskForm {
    @ApiModelProperty(value = "页码", example = "1")
    private String page = "1";
    @ApiModelProperty(value = "每页条数", example = "10")
    private String size = "10";
    @ApiModelProperty(value = "任务编号")
    private String taskId;
    @ApiModelProperty(value = "时间排序（1：倒叙，2：正序）")
    private String orderStyle;
    @ApiModelProperty("任务类型（0：精刷单,1：标签2天,2：标签3天）")
    private String  taskStyle;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOrderStyle() {
        return orderStyle;
    }

    public void setOrderStyle(String orderStyle) {
        this.orderStyle = orderStyle;
    }

    public String getTaskStyle() {
        return taskStyle;
    }

    public void setTaskStyle(String taskStyle) {
        this.taskStyle = taskStyle;
    }

    public static Map<String,Object> toMap(TaskForm taskForm){
        Map<String,Object> map = new HashedMap();
        map.put("page",taskForm.getPage());
        map.put("limit",taskForm.getSize());
        if (!StringUtils.isEmpty(taskForm.getTaskId())){
            map.put("taskId",taskForm.getTaskId());
        }
        if (!StringUtils.isEmpty(taskForm.getOrderStyle())){
            map.put("orderStyle",taskForm.getOrderStyle());
        }
        if (!StringUtils.isEmpty(taskForm.getTaskStyle())){
            map.put("taskStyle",taskForm.getTaskStyle());
        }
        return map;
    }
}
