package com.stone.export.service;

import com.stone.entity.result.Result;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
public class DirectFlushService implements FlushService<Void> {

    private HttpServletResponse response;

    public DirectFlushService(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public Result<Void> flush(File file) throws Exception {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
             BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());) {

            // 设置response的Header
            response.setContentType("application/x-excel");
            response.setCharacterEncoding("UTF-8");
            // todo 疑问：为什么要转ISO8859-1
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(file.getName().getBytes("gb2312"), "ISO8859-1"));
            response.setContentType("application/octet-stream; charset=UTF-8");

            byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len = in.read(data, 0, data.length))) {
                bos.write(data, 0, len);
            }
        }
        return Result.newSuccess();
    }
}
