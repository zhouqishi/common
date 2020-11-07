package com.stone.util;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
public class LruHashMapTest {

    @Test
    public void test() {
        LruHashMap<Integer,Integer> lruHashMap = new LruHashMap(16, 16);
        for (int i=0; i<20; i++) {
            lruHashMap.put(i, i);
        }
        Assert.assertEquals(lruHashMap.size(), 16);
        lruHashMap.entrySet().forEach(entry -> System.out.println(entry.getKey()));
    }

}