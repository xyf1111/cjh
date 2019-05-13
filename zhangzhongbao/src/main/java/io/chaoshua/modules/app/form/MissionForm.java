package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by dws on 2018/11/21 0021.
 */
@ApiModel(value = "订单列表表单")
public class MissionForm  {
    @ApiModelProperty(value = "页码", example = "1")
    private String page = "1";
    @ApiModelProperty(value = "每页条数", example = "10")
    private String size = "10";
    @ApiModelProperty(value = "任务编号")
    private String missionCode;
    @ApiModelProperty(value = "任务类型（0：精刷单，1：标签二天，2：标签三天 ")
    private Integer taskStyle;
    @ApiModelProperty(value = "任务状态 1:未完成，2：已完成，3：已撤销")
    private Integer status;
    @ApiModelProperty(value = "时间排序（1：倒叙，2：正序）")
    private String orderStyle;

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

    public String getMissionCode() {
        return missionCode;
    }

    public void setMissionCode(String missionCode) {
        this.missionCode = missionCode;
    }

    public Integer getTaskStyle() {
        return taskStyle;
    }

    public void setTaskStyle(Integer taskStyle) {
        this.taskStyle = taskStyle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderStyle() {
        return orderStyle;
    }

    public void setOrderStyle(String orderStyle) {
        this.orderStyle = orderStyle;
    }


    public static Map<String,Object> toMap(MissionForm missionForm){
        Map<String,Object> map = new HashedMap();
        map.put("page",missionForm.getPage());
        map.put("limit",missionForm.getSize());
        if (!StringUtils.isEmpty(missionForm.getMissionCode())){
            map.put("missionCode",missionForm.getMissionCode());
        }
        if (missionForm.getTaskStyle() != null){
            map.put("taskStyle",missionForm.getTaskStyle());
        }
        if (missionForm.getStatus() != null){
            Integer status = missionForm.getStatus();
            if(status == 1){
                map.put("status1",1);
            }else if(status == 2){
                map.put("status1",2);
            }else if (status == 3){
                map.put("isAnnul",2);
            }else if (status == 4){
                map.put("role",3);
            }
        }
        if (!StringUtils.isEmpty(missionForm.getOrderStyle())){
            map.put("orderStyle",missionForm.getOrderStyle());
        }
        return map;
    }
}
