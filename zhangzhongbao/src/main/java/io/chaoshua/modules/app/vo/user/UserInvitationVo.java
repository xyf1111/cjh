package io.chaoshua.modules.app.vo.user;

import io.chaoshua.modules.background.entity.InvitationEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/11/20 0020.
 */
@ApiModel("我的邀请码")
public class UserInvitationVo {
    @ApiModelProperty("邀请码ID")
    private Long invitationId;
    @ApiModelProperty("编号")
    private String code;
    @ApiModelProperty("使用情况")
    private String isUse;
    @ApiModelProperty("被邀请人")
    private String userMan;

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getUserMan() {
        return userMan;
    }

    public void setUserMan(String userMan) {
        this.userMan = userMan;
    }

    public static UserInvitationVo toVo(InvitationEntity invitationEntity){
        UserInvitationVo userInvitationVo = new UserInvitationVo();
        userInvitationVo.setCode(invitationEntity.getCode());
        userInvitationVo.setInvitationId(invitationEntity.getId());
        userInvitationVo.setUserMan(invitationEntity.getUseMan());
        Integer isUser = invitationEntity.getIsUse();
        if (isUser != null){
            if (isUser == 1){
                userInvitationVo.setIsUse("否");
            }else if (isUser == 2){
                userInvitationVo.setIsUse("是");
            }
        }
        return userInvitationVo;
    }
}
