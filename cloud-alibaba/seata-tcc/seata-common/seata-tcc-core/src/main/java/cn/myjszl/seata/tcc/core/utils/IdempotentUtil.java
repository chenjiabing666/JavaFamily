package cn.myjszl.seata.tcc.core.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * TCC幂等工具类
 **/
public class IdempotentUtil {

    //HashBasedTable 可以用两个key标记一个值
    private static Table<Class<?>,String,Long> map= HashBasedTable.create();

    public static void add(Class<?> clazz,String xid,Long marker){
        map.put(clazz,xid,marker);
    }

    public static Long get(Class<?> clazz,String xid){
        return map.get(clazz,xid);
    }

    public static void remove(Class<?> clazz,String xid){
        map.remove(clazz,xid);
    }
}