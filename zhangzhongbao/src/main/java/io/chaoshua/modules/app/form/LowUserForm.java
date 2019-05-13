package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by dws on 2018/11/23 0023.
 */
@ApiModel("浏览导入")
public class LowUserForm {
    @ApiModelProperty(value = "页码",example = "1")
    private String page = "1";
    @ApiModelProperty(value = "每页条数",example = "10")
    private String size = "10";
    @ApiModelProperty(value = "下级类型(1:下级，2：下下级")
    private Integer type;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static Map<String,Object> toMap(LowUserForm LowUserForm){
        Map<String,Object> map = new HashedMap();
        map.put("page",LowUserForm.getPage());
        map.put("limit",LowUserForm.getSize());
        map.put("type",LowUserForm.getType());
        return map;
    }

}
