package io.chaoshua.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by dws on 2018/10/23 0023.
 */
@ApiModel("银行")
public class BankVo {

    @ApiModelProperty("银行ID")
    private Long bankId;
    @ApiModelProperty("银行名称")
    private String name;


    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
