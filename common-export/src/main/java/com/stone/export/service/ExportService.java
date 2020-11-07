package com.stone.export.service;

import com.stone.entity.result.Result;

/**
 * 文件导出服务
 *
 * @author yuanxiu
 * @date 2020/11/7
 */
public interface ExportService<R> {

    Result<R> export();

}
