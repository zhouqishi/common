package com.stone.export.service.excel.impl;

import com.stone.enums.BaseEnum;
import com.stone.export.entity.AbstractExportEntity;
import com.stone.export.iterator.DailyExportDataIterator;
import com.stone.export.iterator.ExportDataIterator;
import com.stone.export.service.excel.AbstractSheetExportService;
import com.stone.export.service.excel.entity.DemoEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
public class DemoSheetExportService extends AbstractSheetExportService<DemoEntity> {

    public DemoSheetExportService(String sheetName, ExportDataIterator iterator) {
        super(sheetName, iterator);
    }

    @Override
    public BaseEnum[] getExportBaseEnums() {
        return HeaderEnums.values();
    }

    @Override
    public void doCustomDataExport(Sheet sheet, DemoEntity data) {
        if (AbstractExportEntity.ExportTypeEnums.total != data.getExportType()) {
            return;
        }
        if (iterator instanceof DailyExportDataIterator) {
            DailyExportDataIterator dailyExportDataIterator = (DailyExportDataIterator) iterator;
            List<DemoEntity> entityList = dailyExportDataIterator.getDataList(data.getDate());

            List<String> valueList = new ArrayList<>();
            Integer total = 0;
            Integer online = 0;
            Integer offline = 0;
            for (DemoEntity demoEntity : entityList) {
                total += demoEntity.getTotal();
                online += demoEntity.getOnline();
                offline += demoEntity.getOffline();
            }
            BigDecimal activityRate = BigDecimal.ZERO;
            if (total > 0) {
                activityRate = BigDecimal.valueOf(online * 100).divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);
            }
            valueList.add(data.getDate());
            valueList.add("总计");
            valueList.add(total.toString());
            valueList.add(online.toString());
            valueList.add(offline.toString());
            valueList.add(activityRate.toString());

            int colIndex = 0;
            int rowIndex = getExportRowIndexHolder().getAndMoveNext();
            Row row = sheet.createRow(rowIndex);
            for (String value : valueList) {
                Cell cell = row.createCell(colIndex++);
                cell.setCellValue(value);
            }
        }
    }

    public enum HeaderEnums implements BaseEnum {
        date("date","日期"),
        fleet_name("fleetName","车队"),
        total("total","车辆总数"),
        online("online","上线车辆数"),
        offline("offline","未上线车辆数"),
        activity_rate("activityRate","上线率"),
        ;
        private String key;
        private String value;
        HeaderEnums(String key, String value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public String getKey() {
            return this.key;
        }
        @Override
        public String getValue() {
            return this.value;
        }
    }
}



