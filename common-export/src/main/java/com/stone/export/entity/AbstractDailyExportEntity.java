package com.stone.export.entity;

import lombok.Data;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
@Data
public abstract class AbstractDailyExportEntity extends AbstractExportEntity {

    /**
     * 日期
     */
    protected String date;

}
