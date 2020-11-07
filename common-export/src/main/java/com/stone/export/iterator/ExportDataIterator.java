package com.stone.export.iterator;

import com.stone.export.entity.AbstractExportEntity;

/**
 * @author yuanxiu
 * @date 2020/11/7
 *
 * 实现参考迭代器（Iterator）
 * while(hasNext()) {
 *     E data = next()
 *     // do something
 * }
 *
 * 封装迭代器，来处理数据
 * 1. 全量数据场景 DefaultExportDataIterator
 * 2. 分页数据场景 todo 建议自定义实现hasNext()，去查询分页情况，放入迭代器，然后再用next去迭代器中获取数据 （参考hbase scan写法）
 *
 */
public interface ExportDataIterator<E extends AbstractExportEntity> {

    boolean hasNext();

    E next();

}
