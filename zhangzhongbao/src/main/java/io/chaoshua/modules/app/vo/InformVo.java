package io.chaoshua.modules.app.vo;

import io.chaoshua.modules.background.entity.InformEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by dws on 2018/11/21 0021.
 */
@ApiModel("公告")
public class InformVo {
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private Long id;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("内容")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static InformVo toVo(InformEntity informEntity){
        InformVo informVo = new InformVo();
        informVo.setTitle(informEntity.getTitle());
        informVo.setId(informEntity.getId());
        informVo.setCreateTime(informEntity.getCreateTime());
        return informVo;
    }
}
