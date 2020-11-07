package com.stone.utils;

import org.apache.commons.lang.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yuanxiu
 * @date 2020/11/7
 *
 * 注：变量的前两个字母要么全部大写，要么全部小写
 */
public class BeanPropertyUtils {

    public static <T> Map<String, Object> getPropertyValues(T t) {
        Map<String, Object> result = new HashMap<>();
        if (t == null) {
            return result;
        }

        List<Field> fieldList = new ArrayList<>();
        Class<?> tempClass = t.getClass();
        //当父类为null的时候说明到达了最上层的父类(Object类).
        while (tempClass != null && !tempClass.getName().toLowerCase().equals("java.lang.object")) {
            // 获取所有属性
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            // 得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }

        for (Field field : fieldList) {
            String fieldName = field.getName();
            Object propertyValue = getPropertyValue(t, fieldName);

            result.put(fieldName, propertyValue);
        }
        return result;
    }

    public static <T> Object getPropertyValue(T t, String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        Object value = null;

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass());
            PropertyDescriptor[] propertyDescriptorList = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor descriptor : propertyDescriptorList) {
                if (fieldName.equals(descriptor.getName())) {
                    Method method = descriptor.getReadMethod();

                    if (method != null) {
                        value = method.invoke(t, null);
                        break;
                    } else {
                        value = descriptor.getValue(fieldName);
                    }
                }
            }

        } catch (Exception e) {
        }
        return value;
    }

}
