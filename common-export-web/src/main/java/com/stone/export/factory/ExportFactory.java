package com.stone.export.factory;

import com.stone.export.entity.AbstractExportEntity;
import com.stone.export.iterator.DailyExportDataIterator;
import com.stone.export.iterator.DefaultExportDataIterator;
import com.stone.export.iterator.ExportDataIterator;
import com.stone.export.service.DirectFlushService;
import com.stone.export.service.ExportService;
import com.stone.export.service.FlushService;
import com.stone.export.service.excel.ExcelExportService;
import com.stone.export.service.excel.ExcelSheetExportService;
import com.stone.export.service.excel.entity.DemoEntity;
import com.stone.export.service.excel.impl.DemoSheetExportService;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
public class ExportFactory {

    public final static String DEMO_FILE_NAME = "demo.xlsx";
    public final static String DEMO_SHEET_NAME = "demo";
    public final static String DEMO_SHEET_NAME_2 = "demo2";

    public static FlushService<Void> buildFlushService(HttpServletResponse response) {
        return new DirectFlushService(response);
    }

    public static ExportService buildDemoExportService(List<DemoEntity> entityList, FlushService<Void> flushService) {
        ExportDataIterator exportDataIterator = new DefaultExportDataIterator(entityList.iterator());
        DemoSheetExportService sheetExportService = new DemoSheetExportService(DEMO_SHEET_NAME, exportDataIterator);
        ExportService exportService = new ExcelExportService<Void>(DEMO_FILE_NAME, sheetExportService, flushService);
        return exportService;
    }

    public static ExportService buildDailyDemoExportService(List<DemoEntity> entityList, FlushService<Void> flushService) {
        ExportDataIterator exportDataIterator = new DailyExportDataIterator(entityList, DemoEntity.class, AbstractExportEntity.buidTotal());
        DemoSheetExportService sheetExportService = new DemoSheetExportService(DEMO_SHEET_NAME, exportDataIterator);
        ExportService exportService = new ExcelExportService<Void>(DEMO_FILE_NAME, sheetExportService, flushService);
        return exportService;
    }

    public static ExportService downloadMultipleDemo(List<DemoEntity> entityList1, List<DemoEntity> entityList2, FlushService<Void> flushService) {
        ExportDataIterator exportDataIterator1 = new DailyExportDataIterator(entityList1, DemoEntity.class, AbstractExportEntity.buidTotal());
        DemoSheetExportService sheetExportService1 = new DemoSheetExportService(DEMO_SHEET_NAME, exportDataIterator1);

        ExportDataIterator exportDataIterator2 = new DailyExportDataIterator(entityList2, DemoEntity.class, AbstractExportEntity.buidTotal());
        DemoSheetExportService sheetExportService2 = new DemoSheetExportService(DEMO_SHEET_NAME_2, exportDataIterator2);

        List<ExcelSheetExportService> excelSheetExportServices = Arrays.asList(sheetExportService1, sheetExportService2);

        ExportService exportService = new ExcelExportService<Void>(DEMO_FILE_NAME, excelSheetExportServices, flushService);
        return exportService;
    }

}
