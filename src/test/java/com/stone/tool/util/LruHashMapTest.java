package com.stone.tool.util;

import org.junit.Assert;
import org.junit.Test;
import java.util.Map;
import static org.junit.Assert.*;

/**
 * @author yuanxiu
 * @date 2020/9/25
 */
public class LruHashMapTest {

    @Test
    public void test() {
        LruHashMap<Integer, Integer> map = new LruHashMap(16, 16);
        for (int i=0; i < 20; i++) {
            map.put(i, i);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("key=" + entry.getKey() + " val=" + entry.getValue());
        }
        Assert.assertEquals(map.size(), 16);
    }

}