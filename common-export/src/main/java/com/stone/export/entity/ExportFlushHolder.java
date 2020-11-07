package com.stone.export.entity;

import lombok.Data;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
@Data
public class ExportFlushHolder {

    /**
     * 输出对象封装类
     * 例如输出到页面 httpServletResponse
     */
    private Object flushObj;

    public static ExportFlushHolder build(Object flushObj) {
        ExportFlushHolder holder = new ExportFlushHolder();
        holder.setFlushObj(flushObj);
        return holder;
    }

}
