package com.stone.export.controller;

import com.stone.export.factory.ExportFactory;
import com.stone.export.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
@RestController
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @RequestMapping("/demo")
    public void downloadDemo(HttpServletRequest request, HttpServletResponse response) {
        downloadService.downloadDemo(ExportFactory.buildFlushService(response));
    }

    @RequestMapping("/daily/demo")
    public void downloadDailyDemo(HttpServletRequest request, HttpServletResponse response) {
        downloadService.downloadDailyDemo(ExportFactory.buildFlushService(response));
    }

    @RequestMapping("/multiple/demo")
    public void downloadMultipleDemo(HttpServletRequest request, HttpServletResponse response) {
        downloadService.downloadMultipleDemo(ExportFactory.buildFlushService(response));
    }

}
