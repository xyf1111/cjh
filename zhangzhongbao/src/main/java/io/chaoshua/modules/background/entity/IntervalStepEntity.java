package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 全局设置表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-22 14:10:32
 */
@TableName("t_interval_step")
public class IntervalStepEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 商品价格在0-99元时，商家需要支付的佣金数
     */
    private BigDecimal one;
    /**
     * 商品价格在100-199元时，商家需要支付的佣金数
     */
    private BigDecimal two;
    /**
     * 商品价格在200-299元时，商家需要支付的佣金数
     */
    private BigDecimal three;
    /**
     * 商品价格在300-499元时，商家需要支付的佣金数
     */
    private BigDecimal four;
    /**
     * 在500 ~ 999元时，商家需要支付的佣金数
     */
    private BigDecimal five;
    /**
     * 商品价格在1000 ~ 1999元时，商家需要支付的佣金数
     */
    private BigDecimal six;
    /**
     * 商品价格在2000 ~ 2999元时，商家需要支付的佣金数
     */
    private BigDecimal seven;
    /**
     * 单个商品价格在3000 ~ 3999元以上时，商家需要支付的佣金数
     */
    private BigDecimal eight;
    /**
     * 单个商品价格在4000 ~ 4999元以上时，商家需要支付的佣金数
     */
    private BigDecimal nine;
    /**
     * 单个商品价格在5000元以上时，商家需要支付的佣金数
     */
    private BigDecimal ten;
    /**
     * 刷手在接商品单价在0~1000元的单时，可获得的佣金
     */
    private BigDecimal fir;
    /**
     * 刷手在接商品单价在1000~2000元的单时，可获得的佣金
     */
    private BigDecimal sec;
    /**
     * 刷手在接商品单价在2000以上的单时，可获得的佣金
     */
    private BigDecimal thi;
    /**
     * 刷手在接商品单价在2000以上的单时，可获得的佣金
     */
    private BigDecimal fourth;
    /**
     * 刷手在接商品单价在2000以上的单时，可获得的佣金
     */
    private BigDecimal fifth;
    /**
     * 对于预约任务（便签两天），商家需要支付的浏览佣金
     */
    private BigDecimal appointOneSeller;
    /**
     * 对于预约任务（便签两天），刷手可以获得的浏览佣金
     */
    private BigDecimal appointOneUser;
    /**
     * 对于预约任务（便签三天），商家需要支付的浏览佣金
     */
    private BigDecimal appointTwoSeller;
    /**
     * 对于预约任务（便签两天），刷手可以获得的浏览佣金
     */
    private BigDecimal appointTwoUser;
    /**
     * 商家指定订单评价，需要支付的佣金
     */
    private BigDecimal commentSeller;
    /**
     * 刷手在完成订单指定评价，可以获得的佣金
     */
    private BigDecimal commentUser;
    /**
     * 商家指定订单追评，需要支付的佣金
     */
    private BigDecimal addCommentSeller;
    /**
     * 刷手在完成订单指定追评，可以获得的佣金
     */
    private BigDecimal addCommentUser;
    /**
     * 刷手在邀请别人注册成功后，获得的佣金
     */
    private BigDecimal registerUser;
    /**
     * 下一级刷手完成一个刷单任务的时候，上级刷手可以获得的佣金
     */
    private BigDecimal missionUser;
    /**
     * 下二级刷手完成一个刷单任务的时候，最上级刷手可以获得的佣金
     */
    private BigDecimal secMissionUser;
    /**
     * 下级商家完成一个刷单任务的时候，最上级商家(刷手)可以获得的佣金
     */
    private BigDecimal sellerMoney;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission1;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission2;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission3;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission4;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission5;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission6;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission7;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission8;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission9;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission10;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission11;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission12;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission13;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission14;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission15;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission16;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission17;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission18;
    /**
     * 参考ComparePrice的getSellerPayCommissionMoney方法
     */
    private BigDecimal sellerCommission19;

    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission1;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission2;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission3;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission4;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission5;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission6;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission7;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission8;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission9;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission10;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission11;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission12;
    /**
     * 参考ComparePrice的getUserPayCommissionMoney方法
     */
    private BigDecimal userCommission13;

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：商品价格在0~300元时，商家需要支付的佣金数
     */
    public void setOne(BigDecimal one) {

        this.one = one;
    }

    /**
     * 获取：商品价格在0~300元时，商家需要支付的佣金数
     */
    public BigDecimal getOne() {
        return one;
    }

    /**
     * 设置：商品价格在300~500元时，商家需要支付的佣金数
     */
    public void setTwo(BigDecimal two) {
        this.two = two;
    }

    /**
     * 获取：商品价格在300~500元时，商家需要支付的佣金数
     */
    public BigDecimal getTwo() {
        return two;
    }

    /**
     * 设置：商品价格在500~1000元时，商家需要支付的佣金数
     */
    public void setThree(BigDecimal three) {

        this.three = three;
    }

    /**
     * 获取：商品价格在500~1000元时，商家需要支付的佣金数
     */
    public BigDecimal getThree() {
        return three;
    }

    /**
     * 设置：商品价格在1000~2000元时，商家需要支付的佣金数
     */
    public void setFour(BigDecimal four) {
        this.four = four;
    }

    /**
     * 获取：商品价格在1000~2000元时，商家需要支付的佣金数
     */
    public BigDecimal getFour() {
        return four;
    }

    /**
     * 设置：在2000~3000元时，商家需要支付的佣金数
     */
    public void setFive(BigDecimal five) {
        this.five = five;
    }

    /**
     * 获取：在2000~3000元时，商家需要支付的佣金数
     */
    public BigDecimal getFive() {
        return five;
    }

    /**
     * 设置：商品价格在3000~4000元时，商家需要支付的佣金数
     */
    public void setSix(BigDecimal six) {
        this.six = six;
    }

    /**
     * 获取：商品价格在3000~4000元时，商家需要支付的佣金数
     */
    public BigDecimal getSix() {
        return six;
    }

    /**
     * 设置：商品价格在4000~5000元时，商家需要支付的佣金数
     */
    public void setSeven(BigDecimal seven) {
        this.seven = seven;
    }

    /**
     * 获取：商品价格在4000~5000元时，商家需要支付的佣金数
     */
    public BigDecimal getSeven() {
        return seven;
    }

    /**
     * 设置：单个商品价格在5000元以上时，商家需要支付的佣金数
     */
    public void setEight(BigDecimal eight) {
        this.eight = eight;
    }

    /**
     * 获取：单个商品价格在5000元以上时，商家需要支付的佣金数
     */
    public BigDecimal getEight() {
        return eight;
    }

    /**
     * 设置：刷手在接商品单价在0~1000元的单时，可获得的佣金
     */
    public void setFir(BigDecimal fir) {
        this.fir = fir;
    }

    /**
     * 获取：刷手在接商品单价在0~1000元的单时，可获得的佣金
     */
    public BigDecimal getFir() {
        return fir;
    }

    /**
     * 设置：刷手在接商品单价在1000~2000元的单时，可获得的佣金
     */
    public void setSec(BigDecimal sec) {
        this.sec = sec;
    }

    /**
     * 获取：刷手在接商品单价在1000~2000元的单时，可获得的佣金
     */
    public BigDecimal getSec() {
        return sec;
    }

    /**
     * 设置：刷手在接商品单价在2000以上的单时，可获得的佣金
     */
    public void setThi(BigDecimal thi) {
        this.thi = thi;
    }

    /**
     * 获取：刷手在接商品单价在2000以上的单时，可获得的佣金
     */
    public BigDecimal getThi() {
        return thi;
    }

    /**
     * 设置：对于预约任务（便签两天），商家需要支付的浏览佣金
     */
    public void setAppointOneSeller(BigDecimal appointOneSeller) {
        this.appointOneSeller = appointOneSeller;
    }

    /**
     * 获取：对于预约任务（便签两天），商家需要支付的浏览佣金
     */
    public BigDecimal getAppointOneSeller() {
        return appointOneSeller;
    }

    /**
     * 设置：对于预约任务（便签两天），刷手可以获得的浏览佣金
     */
    public void setAppointOneUser(BigDecimal appointOneUser) {
        this.appointOneUser = appointOneUser;
    }

    /**
     * 获取：对于预约任务（便签两天），刷手可以获得的浏览佣金
     */
    public BigDecimal getAppointOneUser() {
        return appointOneUser;
    }

    /**
     * 设置：对于预约任务（便签三天），商家需要支付的浏览佣金
     */
    public void setAppointTwoSeller(BigDecimal appointTwoSeller) {
        this.appointTwoSeller = appointTwoSeller;
    }

    /**
     * 获取：对于预约任务（便签三天），商家需要支付的浏览佣金
     */
    public BigDecimal getAppointTwoSeller() {
        return appointTwoSeller;
    }

    /**
     * 设置：对于预约任务（便签两天），刷手可以获得的浏览佣金
     */
    public void setAppointTwoUser(BigDecimal appointTwoUser) {
        this.appointTwoUser = appointTwoUser;
    }

    /**
     * 获取：对于预约任务（便签两天），刷手可以获得的浏览佣金
     */
    public BigDecimal getAppointTwoUser() {
        return appointTwoUser;
    }

    /**
     * 设置：商家指定订单评价，需要支付的佣金
     */
    public void setCommentSeller(BigDecimal commentSeller) {
        this.commentSeller = commentSeller;
    }

    /**
     * 获取：商家指定订单评价，需要支付的佣金
     */
    public BigDecimal getCommentSeller() {
        return commentSeller;
    }

    /**
     * 设置：刷手在完成订单指定评价，可以获得的佣金
     */
    public void setCommentUser(BigDecimal commentUser) {
        this.commentUser = commentUser;
    }

    /**
     * 获取：刷手在完成订单指定评价，可以获得的佣金
     */
    public BigDecimal getCommentUser() {
        return commentUser;
    }

    /**
     * 设置：商家指定订单追评，需要支付的佣金
     */
    public void setAddCommentSeller(BigDecimal addCommentSeller) {
        this.addCommentSeller = addCommentSeller;
    }

    /**
     * 获取：商家指定订单追评，需要支付的佣金
     */
    public BigDecimal getAddCommentSeller() {
        return addCommentSeller;
    }

    /**
     * 设置：刷手在完成订单指定追评，可以获得的佣金
     */
    public void setAddCommentUser(BigDecimal addCommentUser) {
        this.addCommentUser = addCommentUser;
    }

    /**
     * 获取：刷手在完成订单指定追评，可以获得的佣金
     */
    public BigDecimal getAddCommentUser() {
        return addCommentUser;
    }

    /**
     * 设置：刷手在邀请别人注册成功后，获得的佣金
     */
    public void setRegisterUser(BigDecimal registerUser) {
        this.registerUser = registerUser;
    }

    /**
     * 获取：刷手在邀请别人注册成功后，获得的佣金
     */
    public BigDecimal getRegisterUser() {
        return registerUser;
    }

    /**
     * 设置：下一级刷手完成一个刷单任务的时候，上级刷手可以获得的佣金
     */
    public void setMissionUser(BigDecimal missionUser) {
        this.missionUser = missionUser;
    }

    /**
     * 获取：下一级刷手完成一个刷单任务的时候，上级刷手可以获得的佣金
     */
    public BigDecimal getMissionUser() {
        return missionUser;
    }

    /**
     * 设置：下二级刷手完成一个刷单任务的时候，最上级刷手可以获得的佣金
     */
    public void setSecMissionUser(BigDecimal secMissionUser) {
        this.secMissionUser = secMissionUser;
    }

    /**
     * 获取：下二级刷手完成一个刷单任务的时候，最上级刷手可以获得的佣金
     */
    public BigDecimal getSecMissionUser() {
        return secMissionUser;
    }

    /**
     * 设置：
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    public BigDecimal getNine() {
        return nine;
    }

    public void setNine(BigDecimal nine) {
        this.nine = nine;
    }

    public BigDecimal getFourth() {
        return fourth;
    }

    public void setFourth(BigDecimal fourth) {
        this.fourth = fourth;
    }

    public BigDecimal getFifth() {
        return fifth;
    }

    public void setFifth(BigDecimal fifth) {
        this.fifth = fifth;
    }

    public BigDecimal getTen() {
        return ten;
    }

    public void setTen(BigDecimal ten) {
        this.ten = ten;
    }

    public BigDecimal getSellerMoney() {
        return sellerMoney;
    }

    public void setSellerMoney(BigDecimal sellerMoney) {
        this.sellerMoney = sellerMoney;
    }

    public BigDecimal getSellerCommission1() {
        return sellerCommission1;
    }

    public void setSellerCommission1(BigDecimal sellerCommission1) {
        this.sellerCommission1 = sellerCommission1;
    }

    public BigDecimal getSellerCommission2() {
        return sellerCommission2;
    }

    public void setSellerCommission2(BigDecimal sellerCommission2) {
        this.sellerCommission2 = sellerCommission2;
    }

    public BigDecimal getSellerCommission3() {
        return sellerCommission3;
    }

    public void setSellerCommission3(BigDecimal sellerCommission3) {
        this.sellerCommission3 = sellerCommission3;
    }

    public BigDecimal getSellerCommission4() {
        return sellerCommission4;
    }

    public void setSellerCommission4(BigDecimal sellerCommission4) {
        this.sellerCommission4 = sellerCommission4;
    }

    public BigDecimal getSellerCommission5() {
        return sellerCommission5;
    }

    public void setSellerCommission5(BigDecimal sellerCommission5) {
        this.sellerCommission5 = sellerCommission5;
    }

    public BigDecimal getSellerCommission6() {
        return sellerCommission6;
    }

    public void setSellerCommission6(BigDecimal sellerCommission6) {
        this.sellerCommission6 = sellerCommission6;
    }

    public BigDecimal getSellerCommission7() {
        return sellerCommission7;
    }

    public void setSellerCommission7(BigDecimal sellerCommission7) {
        this.sellerCommission7 = sellerCommission7;
    }

    public BigDecimal getSellerCommission8() {
        return sellerCommission8;
    }

    public void setSellerCommission8(BigDecimal sellerCommission8) {
        this.sellerCommission8 = sellerCommission8;
    }

    public BigDecimal getSellerCommission9() {
        return sellerCommission9;
    }

    public void setSellerCommission9(BigDecimal sellerCommission9) {
        this.sellerCommission9 = sellerCommission9;
    }

    public BigDecimal getSellerCommission10() {
        return sellerCommission10;
    }

    public void setSellerCommission10(BigDecimal sellerCommission10) {
        this.sellerCommission10 = sellerCommission10;
    }

    public BigDecimal getSellerCommission11() {
        return sellerCommission11;
    }

    public void setSellerCommission11(BigDecimal sellerCommission11) {
        this.sellerCommission11 = sellerCommission11;
    }

    public BigDecimal getSellerCommission12() {
        return sellerCommission12;
    }

    public void setSellerCommission12(BigDecimal sellerCommission12) {
        this.sellerCommission12 = sellerCommission12;
    }

    public BigDecimal getSellerCommission13() {
        return sellerCommission13;
    }

    public void setSellerCommission13(BigDecimal sellerCommission13) {
        this.sellerCommission13 = sellerCommission13;
    }

    public BigDecimal getSellerCommission14() {
        return sellerCommission14;
    }

    public void setSellerCommission14(BigDecimal sellerCommission14) {
        this.sellerCommission14 = sellerCommission14;
    }

    public BigDecimal getSellerCommission15() {
        return sellerCommission15;
    }

    public void setSellerCommission15(BigDecimal sellerCommission15) {
        this.sellerCommission15 = sellerCommission15;
    }

    public BigDecimal getSellerCommission16() {
        return sellerCommission16;
    }

    public void setSellerCommission16(BigDecimal sellerCommission16) {
        this.sellerCommission16 = sellerCommission16;
    }

    public BigDecimal getSellerCommission17() {
        return sellerCommission17;
    }

    public void setSellerCommission17(BigDecimal sellerCommission17) {
        this.sellerCommission17 = sellerCommission17;
    }

    public BigDecimal getSellerCommission18() {
        return sellerCommission18;
    }

    public void setSellerCommission18(BigDecimal sellerCommission18) {
        this.sellerCommission18 = sellerCommission18;
    }

    public BigDecimal getSellerCommission19() {
        return sellerCommission19;
    }

    public void setSellerCommission19(BigDecimal sellerCommission19) {
        this.sellerCommission19 = sellerCommission19;
    }

    public BigDecimal getUserCommission1() {
        return userCommission1;
    }

    public void setUserCommission1(BigDecimal userCommission1) {
        this.userCommission1 = userCommission1;
    }

    public BigDecimal getUserCommission2() {
        return userCommission2;
    }

    public void setUserCommission2(BigDecimal userCommission2) {
        this.userCommission2 = userCommission2;
    }

    public BigDecimal getUserCommission3() {
        return userCommission3;
    }

    public void setUserCommission3(BigDecimal userCommission3) {
        this.userCommission3 = userCommission3;
    }

    public BigDecimal getUserCommission4() {
        return userCommission4;
    }

    public void setUserCommission4(BigDecimal userCommission4) {
        this.userCommission4 = userCommission4;
    }

    public BigDecimal getUserCommission5() {
        return userCommission5;
    }

    public void setUserCommission5(BigDecimal userCommission5) {
        this.userCommission5 = userCommission5;
    }

    public BigDecimal getUserCommission6() {
        return userCommission6;
    }

    public void setUserCommission6(BigDecimal userCommission6) {
        this.userCommission6 = userCommission6;
    }

    public BigDecimal getUserCommission7() {
        return userCommission7;
    }

    public void setUserCommission7(BigDecimal userCommission7) {
        this.userCommission7 = userCommission7;
    }

    public BigDecimal getUserCommission8() {
        return userCommission8;
    }

    public void setUserCommission8(BigDecimal userCommission8) {
        this.userCommission8 = userCommission8;
    }

    public BigDecimal getUserCommission9() {
        return userCommission9;
    }

    public void setUserCommission9(BigDecimal userCommission9) {
        this.userCommission9 = userCommission9;
    }

    public BigDecimal getUserCommission10() {
        return userCommission10;
    }

    public void setUserCommission10(BigDecimal userCommission10) {
        this.userCommission10 = userCommission10;
    }

    public BigDecimal getUserCommission11() {
        return userCommission11;
    }

    public void setUserCommission11(BigDecimal userCommission11) {
        this.userCommission11 = userCommission11;
    }

    public BigDecimal getUserCommission12() {
        return userCommission12;
    }

    public void setUserCommission12(BigDecimal userCommission12) {
        this.userCommission12 = userCommission12;
    }

    public BigDecimal getUserCommission13() {
        return userCommission13;
    }

    public void setUserCommission13(BigDecimal userCommission13) {
        this.userCommission13 = userCommission13;
    }
}
