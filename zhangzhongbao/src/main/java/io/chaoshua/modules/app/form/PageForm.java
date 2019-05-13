package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 分页表单
 *
 * @author dws
 * @date 2018-04-17 9:31
 */
@ApiModel(value = "分页表单")
public class PageForm {
    @ApiModelProperty(value = "页码",example = "1")
    private String page = "1";
    @ApiModelProperty(value = "每页条数",example = "10")
    private String size = "10";
    @ApiModelProperty(value = "搜索参数")
    private String keywords;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public static Map<String,Object> toMap(PageForm pageForm){
        Map<String,Object> map = new HashedMap();
        map.put("page",pageForm.getPage());
        map.put("limit",pageForm.getSize());
        if (StringUtils.isNotBlank(pageForm.getKeywords())){
            map.put("key",pageForm.getKeywords());
        }
        return map;
    }

    @Override
    public String toString() {
        return "PageForm{" +
                "page='" + page + '\'' +
                ", size='" + size + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
