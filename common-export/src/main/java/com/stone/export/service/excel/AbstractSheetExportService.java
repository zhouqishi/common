package com.stone.export.service.excel;

import com.stone.enums.BaseEnum;
import com.stone.export.entity.AbstractExportEntity;
import com.stone.export.entity.ExportRowIndexHolder;
import com.stone.export.iterator.ExportDataIterator;
import com.stone.utils.BeanPropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
public abstract class AbstractSheetExportService<E extends AbstractExportEntity> implements ExcelSheetExportService {

    protected String sheetName;

    protected LinkedHashMap<String, String> headerMap;

    protected ExportDataIterator<E> iterator;

    protected ExportRowIndexHolder exportRowIndexHolder;

    public AbstractSheetExportService(String sheetName, ExportDataIterator<E> iterator) {
        this.sheetName = sheetName;
        this.headerMap = buildHeader();
        this.exportRowIndexHolder = new ExportRowIndexHolder();
        this.iterator = iterator;
    }

    public String getSheetName() {
        return sheetName;
    }

    public LinkedHashMap<String, String> getHeader() {
        return headerMap;
    }

    public ExportDataIterator<E> getIterator() {
        return iterator;
    }

    public ExportRowIndexHolder getExportRowIndexHolder() {
        return exportRowIndexHolder;
    }

    @Override
    public void doSheetExport(Workbook workbook) throws Exception {
        // 生成sheet
        Sheet sheet;
        if (StringUtils.isNotEmpty(getSheetName())) {
            sheet = workbook.createSheet(getSheetName());
        } else {
            sheet = workbook.createSheet();
        }
        // 表头部分
        LinkedHashMap<String, String> headerMaper = getHeader();
        doHeaderExport(sheet, headerMaper);
        // 数据部分
        while (getIterator().hasNext()) {
            E data = getIterator().next();
            doDataExport(sheet, data);
        }
    }

    public void doHeaderExport(Sheet sheet, LinkedHashMap<String, String> headerMap) throws Exception {
        Set<Map.Entry<String, String>> entrySet = headerMap.entrySet();
        Row row = sheet.createRow(getExportRowIndexHolder().getAndMoveNext());
        int colIndex = 0;
        for (Map.Entry<String, String> entry : entrySet) {
            String value = entry.getValue();
            Cell cell = row.createCell(colIndex++);
            cell.setCellValue(value);
        }
    }

    public void doDataExport(Sheet sheet, E data) throws Exception {
        switch (data.getExportType()) {
            case normal:
                doNormalDataExport(sheet, data);
                break;
            case empty:
                doEmptyDataExport(sheet, data);
                break;
            default:
                doCustomDataExport(sheet, data);
                break;
        }
    }

    public void doNormalDataExport(Sheet sheet, E data) {
        LinkedHashMap<String, String> headerMaper = getHeader();
        Map<String, String> dataMap = buildContent(data);
        Set<Map.Entry<String, String>> entrySet = headerMaper.entrySet();
        Row row = sheet.createRow(getExportRowIndexHolder().getAndMoveNext());
        int colIndex = 0;
        for (Map.Entry<String, String> entry : entrySet) {
            String value = dataMap.get(entry.getKey());
            Cell cell = row.createCell(colIndex++);
            cell.setCellValue(value == null ? "" : value);
        }
    }

    public void doEmptyDataExport(Sheet sheet, E data) {
        getExportRowIndexHolder().getAndMoveNext();
    }

    public void doCustomDataExport(Sheet sheet, E data) {
    }

    public LinkedHashMap<String, String> buildHeader() {
        LinkedHashMap<String, String> headerMap = new LinkedHashMap<>();
        BaseEnum[] exportBaseEnums = getExportBaseEnums();
        for (BaseEnum enums : exportBaseEnums) {
            headerMap.put(enums.getKey(), enums.getValue());
        }
        return headerMap;
    }

    public abstract BaseEnum[] getExportBaseEnums();

    /**
     * 常规为属性映射，非常规请走自定义
     * @param data
     * @return
     */
    public Map<String, String> buildContent(E data) {
        Map<String, Object> valuesMap = BeanPropertyUtils.getPropertyValues(data);
        BaseEnum[] exportBaseEnums = getExportBaseEnums();
        Map<String, String> map = new HashMap<>();
        for (BaseEnum exportBaseEnum : exportBaseEnums) {
            Object obj = valuesMap.get(exportBaseEnum.getKey());
            map.put(exportBaseEnum.getKey(), obj == null ? "" : obj.toString());
        }
        return map;
    }
}
