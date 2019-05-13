package io.chaoshua.modules.app.vo;

import com.alibaba.druid.util.StringUtils;
import io.chaoshua.common.UploadUrl.UploadUrl;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dws on 2018/12/5 0005.
 */
@ApiModel("用户已接订单")
public class UserMissionListVo {
    @ApiModelProperty("订单ID")
    private Long missionId;
    @ApiModelProperty("任务编号")
    private String missionCode;
    @ApiModelProperty("发布时间")
    private Date publishTime;
    @ApiModelProperty("任务类型")
    private Integer taskStyle;
    @ApiModelProperty("任务类型")
    private String taskStyleStr;
    @ApiModelProperty("执行时间")
    private Date missionTime;
    @ApiModelProperty("商品价格")
    private BigDecimal price;
    @ApiModelProperty("服务佣金")
    private BigDecimal userPay;
    @ApiModelProperty("状态 订单状态：1订单待认证,2订单待拍下,3订单已拍下,4商家已汇款,5刷手已付款,6商家已发货,7刷手已收货（结单）")
    private Integer status;
    @ApiModelProperty("状态格式化")
    private String statusStr;
    @ApiModelProperty("任务ID")
    private Long taskId;
    @ApiModelProperty("订单是否撤销（1：否，2：是)")
    private Integer isAnnul;
    @ApiModelProperty("订单审核状态（0：初始状态，1、订单待审核2、审核通过、3：已拒绝）")
    private Integer role;
    @ApiModelProperty("指定评价状态(1：商家未指定评价，2：商家已指定评价，3：刷手已完成指定评价，4：追评被撤销）")
    private Integer isComment;
    @ApiModelProperty("追评状态（1：商家未指定，2商家已指定追评，3：刷手 已完成追评，4：追评被撤销）")
    private Integer isAddCom;
    @ApiModelProperty("商家上传评价文本")
    private String comment;
    @ApiModelProperty("商家上传评价截图")
    private String[] imgList;
    @ApiModelProperty("商家上传评价小视频")
    private String smallVideo;
    @ApiModelProperty("商家上传指定追评内容")
    private String addCom;
    @ApiModelProperty("商家上传指定追评截图")
    private String[] addImgList;
    @ApiModelProperty("商家上传指定追评小视频")
    private String addSmallVideo;
    @ApiModelProperty("淘宝订单号")
    private String taobaoCode;
    @ApiModelProperty("产品主图")
    private String img;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("一日浏览截图")
    private String view1;
    @ApiModelProperty("二日浏览截图")
    private String view2;
    @ApiModelProperty("一日浏览截图")
    private String[] view1List;
    @ApiModelProperty("二日浏览截图")
    private String[] view2List;
    @ApiModelProperty("待付款")
    private String paying;
    @ApiModelProperty("已付款")
    private String paied;
    @ApiModelProperty("刷手指定评价截图")
    private String commentImg;
    @ApiModelProperty("刷手追评截图")
    private String addComImg;
    @ApiModelProperty("拒絕或撤銷備注")
    private String userNote;

    public String getCommentImg() {
        if (!StringUtils.isEmpty(commentImg)) {
            if (!commentImg.contains("http")) {
                commentImg = UploadUrl.getUrl() + commentImg;
            }
        }
        return commentImg;
    }

    public Integer getIsAnnul() {
        return isAnnul;
    }

    public void setIsAnnul(Integer isAnnul) {
        this.isAnnul = isAnnul;
    }

    public void setCommentImg(String commentImg) {
        this.commentImg = commentImg;
    }

    public String getAddComImg() {
        if (!StringUtils.isEmpty(addComImg )){
            if (!addComImg.contains("http")){
                addComImg = UploadUrl.getUrl()+addComImg;
            }
        }
        return addComImg;
    }

    public void setAddComImg(String addComImg) {
        this.addComImg = addComImg;
    }

    public String getuserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public Long getMissionId() {
        return missionId;
    }

    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }

    public String getMissionCode() {
        return missionCode;
    }

    public void setMissionCode(String missionCode) {
        this.missionCode = missionCode;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getTaskStyle() {
        return taskStyle;
    }

    public void setTaskStyle(Integer taskStyle) {
        this.taskStyle = taskStyle;
    }

    public Date getMissionTime() {
        return missionTime;
    }

    public void setMissionTime(Date missionTime) {
        this.missionTime = missionTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getUserPay() {
        return userPay;
    }

    public void setUserPay(BigDecimal userPay) {
        this.userPay = userPay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getTaskStyleStr() {
        return taskStyleStr;
    }

    public void setTaskStyleStr(String taskStyleStr) {
        this.taskStyleStr = taskStyleStr;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    public Integer getIsAddCom() {
        return isAddCom;
    }

    public void setIsAddCom(Integer isAddCom) {
        this.isAddCom = isAddCom;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getImgList() {
        return imgList;
    }

    public void setImgList(String[] imgList) {
        this.imgList = imgList;
    }

    public String getSmallVideo() {
        return smallVideo;
    }

    public void setSmallVideo(String smallVideo) {
        this.smallVideo = smallVideo;
    }

    public String getAddCom() {
        return addCom;
    }

    public void setAddCom(String addCom) {
        this.addCom = addCom;
    }

    public String[] getAddImgList() {
        return addImgList;
    }

    public void setAddImgList(String[] addImgList) {
        this.addImgList = addImgList;
    }

    public String getAddSmallVideo() {
        return addSmallVideo;
    }

    public void setAddSmallVideo(String addSmallVideo) {
        this.addSmallVideo = addSmallVideo;
    }

    public String getTaobaoCode() {
        return taobaoCode;
    }

    public void setTaobaoCode(String taobaoCode) {
        this.taobaoCode = taobaoCode;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getView1() {
        return view1;
    }

    public void setView1(String view1) {
        this.view1 = view1;
    }

    public String getView2() {
        return view2;
    }

    public void setView2(String view2) {
        this.view2 = view2;
    }

    public String[] getView1List() {
        return view1List;
    }

    public void setView1List(String[] view1List) {
        this.view1List = view1List;
    }

    public String[] getView2List() {
        return view2List;
    }

    public void setView2List(String[] view2List) {
        this.view2List = view2List;
    }

    public String getPaying() {
        if (!StringUtils.isEmpty(paying)){
            if (!paying.contains("http")){
                paying = UploadUrl.getUrl()+paying;
            }
        }
        return paying;
    }

    public void setPaying(String paying) {
        this.paying = paying;
    }

    public String getPaied() {
        if (!StringUtils.isEmpty(paied)){
            if (!paied.contains("http")){
                paied = UploadUrl.getUrl()+paied;
            }
        }
        return paied;
    }

    public void setPaied(String paied) {
        this.paied = paied;
    }

    public static UserMissionListVo toUserMissionnListVo(MissionEntity missionEntity){
        UserMissionListVo userMissionListVo = new UserMissionListVo();
        userMissionListVo.setMissionCode(missionEntity.getMissionCode());
        userMissionListVo.setPublishTime(missionEntity.getPublishTime());
        userMissionListVo.setTaskStyle(missionEntity.getTaskStyle());
        userMissionListVo.setTaskStyleStr(missionEntity.getTaskStyleStr());
        userMissionListVo.setMissionTime(missionEntity.getMissionTime());
        userMissionListVo.setPrice(missionEntity.getPrice());
        userMissionListVo.setUserPay(missionEntity.getUserPay());
        userMissionListVo.setStatus(missionEntity.getStatus());
        userMissionListVo.setIsAnnul(missionEntity.getIsAnnul());
        userMissionListVo.setStatusStr(missionEntity.getStatusStr());
        userMissionListVo.setMissionId(missionEntity.getId());
        userMissionListVo.setTaskId(missionEntity.getTaskId());
        userMissionListVo.setRole(missionEntity.getRole());
        userMissionListVo.setIsComment(missionEntity.getIsComment());
        userMissionListVo.setIsAddCom(missionEntity.getIsAddCom());
        userMissionListVo.setComment(missionEntity.getComment());
        userMissionListVo.setAddCom(missionEntity.getAddCom());
        // 商家指定评价小视频
        userMissionListVo.setSmallVideo(missionEntity.getSmallVideo());
        // 商家指定追评小视频
        userMissionListVo.setAddSmallVideo(missionEntity.getAddSmallVideo());
        userMissionListVo.setTaobaoCode(missionEntity.getTaobaoCode());
        userMissionListVo.setImg(missionEntity.getImg());
        userMissionListVo.setView1(missionEntity.getView1());
        userMissionListVo.setView2(missionEntity.getView2());
        userMissionListVo.setPaying(missionEntity.getPaying());
        userMissionListVo.setPaied(missionEntity.getPaied());
        userMissionListVo.setCommentImg(missionEntity.getCommentImg());
        userMissionListVo.setAddComImg(missionEntity.getAddComImg());
        if (missionEntity.getImgList() != null){
            userMissionListVo.setImgList(toArray(missionEntity.getImgList()));

        }
        if(missionEntity.getAddImgList() != null){
            userMissionListVo.setAddImgList(toArray(missionEntity.getAddImgList()));
        }
        if (missionEntity.getView2() != null){
            userMissionListVo.setView2List(toArray(missionEntity.getView2()));
        }
        if (missionEntity.getView1() != null){
            userMissionListVo.setView1List(  toArray(missionEntity.getView1()));
        }
        if (missionEntity.getShopName() != null){//奇数*替换
            char [] c = missionEntity.getShopName().toCharArray();
            for (int i = 0;i<c.length;i++){
                if (i %2 == 1){
                    c[i] = '*';
                }
            }
            String str = new String(c);
            userMissionListVo.setShopName(str);
        }
        return userMissionListVo;
    }

    public static List<UserMissionListVo> toListVo(List<MissionEntity> list){
        List<UserMissionListVo> voList = new ArrayList<>();
        for (MissionEntity missionEntity :list){
            UserMissionListVo userMissionListVo = UserMissionListVo.toUserMissionnListVo(missionEntity);
            voList.add(userMissionListVo);
        }
        return voList;
    }

    public static String [] toArray(String str){
        String[] ids = str .split(",");
        for (int i = 0;i<ids.length;i++){
            if (!StringUtils.isEmpty(ids[i])){
                if (!ids[i].contains("http")){
                    ids[i] = UploadUrl.getUrl() + ids[i];
                }
            }
        }
        return ids;
    }
}
