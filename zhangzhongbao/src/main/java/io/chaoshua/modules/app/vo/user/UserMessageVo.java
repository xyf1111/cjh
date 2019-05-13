package io.chaoshua.modules.app.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/11/20 0020.
 */
@ApiModel("用户消息")
public class UserMessageVo {
    @ApiModelProperty("消息ID")
    private Long messageId;
    @ApiModelProperty("消息内容")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
