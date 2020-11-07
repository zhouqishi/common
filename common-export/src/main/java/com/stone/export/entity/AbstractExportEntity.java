package com.stone.export.entity;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
@Data
public abstract class AbstractExportEntity {

    protected ExportTypeEnums exportType;

    public AbstractExportEntity() {
        this.exportType = ExportTypeEnums.normal;
    }

    public AbstractExportEntity(ExportTypeEnums exportType) {
        this.exportType = exportType;
    }

    public enum ExportTypeEnums {
        normal("正常"),
        empty("空"),
        total("总计"),
        avg("平均"),
        ;

        private String value;

        ExportTypeEnums(String value) {
            this.value = value;
        }
    }

    public static List<ExportTypeEnums> buidTotal() {
        return Arrays.asList(ExportTypeEnums.total, ExportTypeEnums.empty);
    }

    public static List<ExportTypeEnums> buidAvg() {
        return Arrays.asList(ExportTypeEnums.avg, ExportTypeEnums.empty);
    }

    public static List<ExportTypeEnums> buidEmpty() {
        return Arrays.asList(ExportTypeEnums.empty);
    }

}

