package com.stone.export.entity;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
public class ExportRowIndexHolder {

    private int rowIndex = 0;

    public int get() {
        return rowIndex;
    }

    public int getAndMoveNext() {
        return rowIndex++;
    }

}

