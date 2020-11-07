package com.stone.enums;

/**
 * 基础枚举类 用于实现 如果想获取某个枚举的value 直接调用
 *
 * @author yuanxiu
 * @date 2020/11/7
 */
public interface BaseIntegerEnum {

    /**
     * 获取当前枚举的key
     */
    Integer getKey();

    /**
     * 获取当前枚举的value
     */
    String getValue();

}

