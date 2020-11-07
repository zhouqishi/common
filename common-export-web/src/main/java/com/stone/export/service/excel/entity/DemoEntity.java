package com.stone.export.service.excel.entity;

import com.stone.export.entity.AbstractDailyExportEntity;
import lombok.Data;
import java.math.BigDecimal;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
@Data
public class DemoEntity extends AbstractDailyExportEntity {

    private String fleetName;

    private Integer total;

    private Integer online;

    private Integer offline;

    private BigDecimal activityRate;

}
