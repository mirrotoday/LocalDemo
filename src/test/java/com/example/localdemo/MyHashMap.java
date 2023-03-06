package com.example.localdemo;

import java.util.HashMap;

/**
 * @author xieteng
 * @date 2023/2/26 10:32
 * @description TODO
 */
public class MyHashMap<K> extends HashMap<K,String> {
    @Override
    public String put(K key, String value) {
        //  定义一个新的value 接收 后面put的新的value值
        String NewVaule = value;
        //containsKey  判断这个 key  是否已经存在？
        if (containsKey(key)){
            // 获得旧的value 值
            String oldValue = get(key);
            //将旧值 和 后面put 的新值拼接起来
            NewVaule = oldValue + "," +NewVaule;
        }
        // 返回拼接后的newvalue
        return super.put(key, NewVaule);
    }
}
