package com.stone.export.iterator;

import com.stone.export.entity.AbstractExportEntity;
import java.util.Iterator;

/**
 * 默认迭代器
 *
 * @author yuanxiu
 * @date 2020/11/7
 */
public class DefaultExportDataIterator<E extends AbstractExportEntity> implements ExportDataIterator<E> {

    private Iterator<E> iterator;

    public DefaultExportDataIterator() {
    }

    public DefaultExportDataIterator(Iterator<E> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        if (iterator == null) {
            return false;
        } else {
            return iterator.hasNext();
        }
    }

    @Override
    public E next() {
        return iterator.next();
    }

}

