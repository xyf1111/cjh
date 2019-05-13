package io.chaoshua.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-02 15:15
 */
@ApiModel("分页参数")
public class AppPage<T> extends AppStatus {
    @ApiModelProperty(value = "返回数据")
    private List<T> data;
    @ApiModelProperty(value = "总记录数")
    private Long total;
    @ApiModelProperty(value = "总页数")
    private Long pages;
    @ApiModelProperty(value = "当前页码")
    private int current;

    public AppPage() {
    }

    private AppPage(int code, String msg) {
        super(code, msg);
    }

    public static AppPage success() {
        return new AppPage(0, "success");
    }

    public static AppPage error(int code, String msg) {
        return new AppPage(code, msg);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
