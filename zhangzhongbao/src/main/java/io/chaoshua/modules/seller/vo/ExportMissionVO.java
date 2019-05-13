package io.chaoshua.modules.seller.vo;

import java.util.List;

/**
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2019-03-27 14:40
 */
public class ExportMissionVO {
    private Integer excelType;
    private List<Long> ids;

    public Integer getExcelType() {
        return excelType;
    }

    public void setExcelType(Integer excelType) {
        this.excelType = excelType;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "ExportMissionVO{" +
                "excelType=" + excelType +
                ", ids=" + ids +
                '}';
    }
}
