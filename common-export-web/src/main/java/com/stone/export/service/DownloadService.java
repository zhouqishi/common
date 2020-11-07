package com.stone.export.service;

import com.stone.entity.param.Parameter;
import com.stone.export.entity.ExportFlushHolder;
import com.stone.export.factory.ExportFactory;
import com.stone.export.service.excel.entity.DemoEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
@Service
public class DownloadService {

    public void downloadDemo(FlushService<Void> flushService) {
        ExportService exportService = ExportFactory.buildDemoExportService(buildEntityList(), flushService);
        exportService.export();
    }

    public void downloadDailyDemo(FlushService<Void> flushService) {
        ExportService exportService = ExportFactory.buildDailyDemoExportService(buildEntityList(), flushService);
        exportService.export();
    }

    public void downloadMultipleDemo(FlushService<Void> flushService) {
        ExportService exportService = ExportFactory.downloadMultipleDemo(buildEntityList(), buildEntityList(), flushService);
        exportService.export();
    }

    private List<DemoEntity> buildEntityList() {
        List<DemoEntity> entityList = new ArrayList<>();
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setFleetName("xxx车队");
        demoEntity.setDate("2020-11-07");
        demoEntity.setTotal(100);
        demoEntity.setOnline(10);
        demoEntity.setOffline(90);
        demoEntity.setActivityRate(new BigDecimal("90"));
        entityList.add(demoEntity);
        return entityList;
    }

}
