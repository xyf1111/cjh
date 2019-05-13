package io.chaoshua.common.yto.req;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-11 17:54
 */
public class OrderRequest{

    public OrderRequest() {
        this.platform = 1;
        this.orderNo = "1_1547452046333";
        this.contact = "张三";
        this.officePhone = "0592366277";
        this.cellPhone = "13960130220";
        this.state = "福建";
        this.city = "厦门";
        this.district = "思明";
        this.address = "软件园二期观日路";
        this.sendContact = "李四";
        this.sendOfficePhone = "0592366288";
        this.sendCellPhone = "13960130221";
        this.sendState = "福建";
        this.sendCity = "厦门";
        this.sendDistrict = "集美";
        this.sendAddress = "详细地址";
        this.productTitle = "电灯";
        this.weight = new BigDecimal(2);
    }

    public OrderRequest(int platform, String orderNo, String contact, String officePhone,
                String cellPhone, String state, String city, String district, String address, String sendContact,
                String sendOfficePhone, String sendCellPhone, String sendState, String sendCity, String sendDistrict,
                String sendAddress, String productTitle, BigDecimal weight) {
        this.platform = platform;
        this.orderNo = orderNo;
        this.contact = contact;
        this.officePhone = officePhone;
        this.cellPhone = cellPhone;
        this.state = state;
        this.city = city;
        this.district = district;
        this.address = address;
        this.sendContact = sendContact;
        this.sendOfficePhone = sendOfficePhone;
        this.sendCellPhone = sendCellPhone;
        this.sendState = sendState;
        this.sendCity = sendCity;
        this.sendDistrict = sendDistrict;
        this.sendAddress = sendAddress;
        this.productTitle = productTitle;
        this.weight = weight;
    }

    /**
     * 订单来源
     * 可选值：1.全网通（含淘宝） 5.仅京东  9.仅拼多多  10.其他（非淘非京非拼）
     */
    @JSONField(name = "Platform")
    private Integer platform;
    /**
     * 订单号
     * 只能包含大小写字母， 数字和-（减号），且字符串长度不能大于35
     */
    @JSONField(name = "OrderNo")
    private String orderNo;
    /**
     * 收件人名称
     * 字符串长度不能大于30
     */
    @JSONField(name = "Contact")
    private String contact;
    /**
     * 收件人电话
     * 字符串长度不能大于20
     */
    @JSONField(name = "OfficePhone")
    private String officePhone;
    /**
     * 收件人手机
     * 有效的11位手机号码
     * 收件人手机和电话至少填一项
     */
    @JSONField(name = "CellPhone")
    private String cellPhone;
    /**
     * 收件人所在省
     * 字符串长度不能大于20
     */
    @JSONField(name = "State")
    private String state;
    /**
     * 收件人所在市
     * 字符串长度不能大于20
     */
    @JSONField(name = "City")
    private String city;
    /**
     * 收件人所在县/区
     * 字符串长度不能大于20
     */
    @JSONField(name = "District")
    private String district;
    /**
     * 收件人详细地址
     * 字符串长度不能大于100
     */
    @JSONField(name = "Address")
    private String address;
    /**
     * 发件人名称
     * 字符串长度不能大于30
     */
    @JSONField(name = "SendContact")
    private String sendContact;
    /**
     * 发件人电话
     * 字符串长度不能大于20
     */
    @JSONField(name = "SendOfficePhone")
    private String sendOfficePhone;
    /**
     * 发件人手机
     * 有效的11位手机号码
     * 收件人手机和电话至少填一项
     */
    @JSONField(name = "SendCellPhone")
    private String sendCellPhone;
    /**
     * 发件人所在省
     * 字符串长度不能大于20
     */
    @JSONField(name = "SendState")
    private String sendState;
    /**
     * 发件人所在市
     * 字符串长度不能大于20
     */
    @JSONField(name = "SendCity")
    private String sendCity;
    /**
     * 发件人所在县/区
     * 字符串长度不能大于20
     */
    @JSONField(name = "SendDistrict")
    private String sendDistrict;
    /**
     * 发件人详细地址
     * 字符串长度不能大于100
     */
    @JSONField(name = "SendAddress")
    private String sendAddress;
    /**
     * 商品名称
     * 字符串长度不能大于100
     */
    @JSONField(name = "ProductTitle")
    private String productTitle;
    /**
     * 包裹重量
     * 两位小数, 0.05kg-40kg之间
     */
    @JSONField(name = "Weight")
    private BigDecimal weight;

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSendContact() {
        return sendContact;
    }

    public void setSendContact(String sendContact) {
        this.sendContact = sendContact;
    }

    public String getSendOfficePhone() {
        return sendOfficePhone;
    }

    public void setSendOfficePhone(String sendOfficePhone) {
        this.sendOfficePhone = sendOfficePhone;
    }

    public String getSendCellPhone() {
        return sendCellPhone;
    }

    public void setSendCellPhone(String sendCellPhone) {
        this.sendCellPhone = sendCellPhone;
    }

    public String getSendState() {
        return sendState;
    }

    public void setSendState(String sendState) {
        this.sendState = sendState;
    }

    public String getSendCity() {
        return sendCity;
    }

    public void setSendCity(String sendCity) {
        this.sendCity = sendCity;
    }

    public String getSendDistrict() {
        return sendDistrict;
    }

    public void setSendDistrict(String sendDistrict) {
        this.sendDistrict = sendDistrict;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
