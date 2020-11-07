package com.stone.export.service.excel;

import com.stone.entity.result.Result;
import com.stone.export.service.ExportService;
import com.stone.export.service.FlushService;
import com.stone.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
@Slf4j
public class ExcelExportService<R> implements ExportService<R> {

    protected String fileName;

    protected List<ExcelSheetExportService> excelSheetExportServices;

    protected FlushService<R> flushService;

    // xlsx poi缓存数量
    public static final int POI_BATCH_DEAL_SIZE = 1000;

    public ExcelExportService(String fileName, ExcelSheetExportService excelSheetExportService, FlushService<R> flushService) {
        this.fileName = fileName;
        this.excelSheetExportServices = Arrays.asList(excelSheetExportService);
        this.flushService = flushService;
    }

    public ExcelExportService(String fileName, List<ExcelSheetExportService> excelSheetExportServices, FlushService<R> flushService) {
        this.fileName = fileName;
        this.excelSheetExportServices = excelSheetExportServices;
        this.flushService = flushService;
    }

    public String getFileName() {
        return fileName;
    }

    public List<ExcelSheetExportService> getExcelSheetExportServices() {
        return excelSheetExportServices;
    }

    @Override
    public Result<R> export() {
        // 临时文件
        File file = FileUtils.createEmptyExcelFile(getFileName());
        try (OutputStream out = new FileOutputStream(file)) {
            // 生成excl
            Workbook workbook = new SXSSFWorkbook(POI_BATCH_DEAL_SIZE);;
            // 生成sheet
            doSheetExport(workbook);
            // 写入文件
            workbook.write(out);
            // do flush file
            Result result = flushService.flush(file);
            file.delete();
            return result;
        } catch (Exception e) {
            log.error("导出error :", e);
            return Result.newFailure("导出文件失败");
        }
    }

    /**
     * sheet内数据操作
     * @param workbook
     * @throws Exception
     */
    public void doSheetExport(Workbook workbook) throws Exception {
        for (ExcelSheetExportService sheetExportService : excelSheetExportServices) {
            sheetExportService.doSheetExport(workbook);
        }
    }


}
