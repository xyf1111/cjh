/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.chaoshua.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 注册表单
 *
 * @author dws
 * @since 3.1.0 2018-01-25
 */
@ApiModel(value = "注册表单")
public class RegisterForm {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String passwordAgain;

    @ApiModelProperty(value = "收件人姓名")
    @NotBlank(message = "收件人姓名不能为空")
    @Length(max = 20, message = "收件人姓名长度不能大于20")
    private String receiveName;

    @ApiModelProperty(value = "收件人电话")
    @NotBlank(message = "收件人电话不能为空")
    @Length(max = 20, message = "收件人电话长度不能大于11")
    private String receiveMobile;

    @ApiModelProperty(value = "地区")
    @NotBlank(message = "地区不能为空")
    private String area;

    @ApiModelProperty(value = "详细地址")
    @NotBlank(message = "详细地址不能为空")
    @Length(max = 100, message = "详细地址长度不能大于100")
    private String address;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "刷手淘宝号")
    @NotBlank(message = "刷手淘宝号不能为空")
    private String taobao;

    @NotBlank(message = "联系人姓名为空")
    @ApiModelProperty(value = "刷手姓名")
    private String name;

    @NotBlank(message = "微信号不能为空")
    @ApiModelProperty(value = "微信号")
    private String wechat;

    @NotBlank(message = "QQ号不能为空")
    @ApiModelProperty(value = "QQ号")
    private String qq;

    @ApiModelProperty(value = "银行卡持卡人姓名")
    @NotBlank(message = "银行卡持卡人姓名不能为空")
    private String holder;

    @ApiModelProperty(value = "刷手银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String cardNumber;

    @ApiModelProperty(value = "银行卡所属银行")
    @NotBlank(message = "银行卡所属银行不能为空")
    private String bank;

    @ApiModelProperty(value = "身份证正面照片")
    private String positive;

    @ApiModelProperty(value = "身份证反面照片")
    private String back;

    @ApiModelProperty(value = "上级邀请码")
    private String parentInvitation;

    @ApiModelProperty(value = "认证图片List")
    private List<UserImgVo> imgs;

    public List<UserImgVo> getImgs() {
        return imgs;
    }

    public void setImgs(List<UserImgVo> imgs) {
        this.imgs = imgs;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaobao() {
        return taobao;
    }

    public void setTaobao(String taobao) {
        this.taobao = taobao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getPositive() {
        return positive;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getParentInvitation() {
        return parentInvitation;
    }

    public void setParentInvitation(String parentInvitation) {
        this.parentInvitation = parentInvitation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
