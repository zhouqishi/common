package com.stone.export.service;

import com.stone.entity.result.Result;

import java.io.File;

/**
 * 文件输出服务
 *
 * @author yuanxiu
 * @date 2020/11/7
 */
public interface FlushService<R> {

    Result<R> flush(File file) throws Exception;

}
