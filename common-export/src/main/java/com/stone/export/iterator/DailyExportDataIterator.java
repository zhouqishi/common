package com.stone.export.iterator;

import com.stone.export.entity.AbstractDailyExportEntity;
import com.stone.export.entity.AbstractExportEntity;
import org.springframework.util.CollectionUtils;
import java.util.*;

/**
 * 每日迭代器
 * 处理日均/日总数据导出 （日均/日总数据需要处理）
 *
 * @author yuanxiu
 * @date 2020/11/3
 */
public class DailyExportDataIterator<E extends AbstractDailyExportEntity> implements ExportDataIterator<E> {

    private LinkedHashMap<String, List<E>> linkedHashMap;

    private Iterator<Map.Entry<String, List<E>>> entryIterator;

    private Iterator<E> iterator;

    private String currDate;

    private List<AbstractExportEntity.ExportTypeEnums> typeEnumsList;

    private Class<E> clazz;

    public DailyExportDataIterator(List<E> list, Class<E> clazz, List<AbstractExportEntity.ExportTypeEnums> typeEnumsList) {
        // 排序，分组
        Collections.sort(list, new Comparator<E>() {
            @Override
            public int compare(E o1, E o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        // 构建LinkedHashMap
        this.linkedHashMap = new LinkedHashMap<>();
        for (E data : list) {
            List<E> dataList = this.linkedHashMap.get(data.getDate());
            if (dataList == null) {
                dataList = new ArrayList<>();
                this.linkedHashMap.put(data.getDate(), dataList);
            }
            dataList.add(data);
        }
        this.entryIterator = this.linkedHashMap.entrySet().iterator();
        this.clazz = clazz;
        this.typeEnumsList = typeEnumsList;
    }

    @Override
    public boolean hasNext() {
        if (iterator == null) {
            iterator = fetchNextEntryIterator();
        } else if (iterator.hasNext()) {
            return true;
        } else {
            if (currDate == null) {
                iterator = fetchNextEntryIterator();
            } else {
                // 分隔行
                List<E> separateList = buildSeparateList();
                // 设置为null
                currDate = null;
                // 若空，则获取下一批数据
                if (CollectionUtils.isEmpty(separateList)) {
                    iterator = fetchNextEntryIterator();
                } else {
                    iterator = separateList.iterator();
                }
            }
        }
        // 这里没获取到迭代器，则为无数据
        if (iterator == null) {
            return false;
        } else {
            return iterator.hasNext();
        }
    }

    private Iterator<E> fetchNextEntryIterator() {
        if (entryIterator.hasNext()) {
            List<E> dataList = entryIterator.next().getValue();
            // 设置当前天
            currDate = dataList.get(0).getDate();
            return dataList.iterator();
        }
        return null;
    }

    private List<E> buildSeparateList() {
        List<E> separateList = new ArrayList<>();
        // 非空
        if (!CollectionUtils.isEmpty(typeEnumsList)) {
            for (AbstractExportEntity.ExportTypeEnums typeEnums : typeEnumsList) {
                try {
                    E data = clazz.newInstance();
                    data.setDate(currDate);
                    data.setExportType(typeEnums);
                    separateList.add(data);
                } catch (Exception e) {
                    throw new RuntimeException(clazz.toString() + "需要一个默认构造函数");
                }
            }
        }
        return separateList;
    }

    @Override
    public E next() {
        return iterator.next();
    }

    public List<E> getDataList(String date) {
        return linkedHashMap.get(date);
    }

}

