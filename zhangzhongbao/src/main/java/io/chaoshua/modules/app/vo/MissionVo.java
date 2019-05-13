package io.chaoshua.modules.app.vo;

import io.chaoshua.common.UploadUrl.UploadUrl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dws on 2018/12/7 0007.
 */

@ApiModel("订单详情")
public class MissionVo {
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("关键词")
    private String keyWord;
    @ApiModelProperty("任务执行时间")
    private Date missionTime;
    @ApiModelProperty("搜素排序")
    private Integer order;
    @ApiModelProperty("搜素排序格式化")
    private String orderStr;
    @ApiModelProperty("排名区间起点")
    private Integer intervalBegin;
    @ApiModelProperty("排名区间终点")
    private Integer intervalEnd;
    @ApiModelProperty("价格区间（从）")
    private BigDecimal priceFrom;
    @ApiModelProperty("价格区间（至）")
    private BigDecimal priceTo;
    @ApiModelProperty("商品价格")
    private BigDecimal price;
    @ApiModelProperty("商品价格(首位标*)")
    private String priceStr;
    @ApiModelProperty("淘宝订单号")
    private String taobaoCode;
    @ApiModelProperty("产品主图一")
    private String img;
    @ApiModelProperty("产品主图二")
    private String img2;
    @ApiModelProperty("产品主图三")
    private String img3;
    @ApiModelProperty("足迹图")
    private String footprint;
    @ApiModelProperty("宝贝聊天图")
    private String chat;
    @ApiModelProperty("收藏图")
    private String collectionCom;
    @ApiModelProperty("已付款截图")
    private String paied;
    @ApiModelProperty("确认收获")
    private String receive;
    @ApiModelProperty("评价")
    private String commentImg;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Date getMissionTime() {
        return missionTime;
    }

    public void setMissionTime(Date missionTime) {
        this.missionTime = missionTime;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Integer getIntervalBegin() {
        return intervalBegin;
    }

    public void setIntervalBegin(Integer intervalBegin) {
        this.intervalBegin = intervalBegin;
    }

    public Integer getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(Integer intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTaobaoCode() {
        return taobaoCode;
    }

    public void setTaobaoCode(String taobaoCode) {
        this.taobaoCode = taobaoCode;
    }

    public String getImg() {
        if (StringUtils.isNotBlank(img)) {
            if (!img.contains("http")) {
                img = UploadUrl.getUrl() + img;
            }
        }
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BigDecimal getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(BigDecimal priceFrom) {
        this.priceFrom = priceFrom;
    }

    public BigDecimal getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(BigDecimal priceTo) {
        this.priceTo = priceTo;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getFootprint() {
        return footprint;
    }

    public void setFootprint(String footprint) {
        this.footprint = footprint;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getCollectionCom() {
        return collectionCom;
    }

    public void setCollectionCom(String collectionCom) {
        this.collectionCom = collectionCom;
    }

    public String getPaied() {
        return paied;
    }

    public void setPaied(String paied) {
        this.paied = paied;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getCommentImg() {
        return commentImg;
    }

    public void setCommentImg(String commentImg) {
        this.commentImg = commentImg;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }
}
