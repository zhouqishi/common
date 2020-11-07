package com.stone.enums;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
public enum DefaultResultEnum implements BaseResultEnum {
    success(10000, "成功"), failure(99999, "失败");

    private Integer key;
    private String value;

    DefaultResultEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
