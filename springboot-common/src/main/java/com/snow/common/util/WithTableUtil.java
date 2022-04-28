package com.snow.common.util;


import com.alibaba.fastjson.JSON;
import com.snow.common.pojo.PublicResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ike
 * @since 2019-05-21 16:48
 */

public class WithTableUtil {


    private static final String GETTABLE = "getTable";

    /**
     * 根据编码获取数据库表全限定名
     * @return String 数据库表全限定名
     */
    public static String getTableByCode(PublicResult publicResult) {
        try {
            Map map = getTableObjectByCode(publicResult);
            if (map.containsKey(WTConstants.TABLE) && !ObjectUtil.isEmpty(map.get(WTConstants.TABLE))) {
                return (String) map.get(WTConstants.TABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据编码获取表名
     * @return String 表名
     */
    public static String getTableNameByCode(PublicResult publicResult) {
        try {
            Map map = getTableObjectByCode(publicResult);
            if (map.containsKey(WTConstants.TABLENAME) && !ObjectUtil.isEmpty(map.get(WTConstants.TABLENAME))) {
                return (String) map.get(WTConstants.TABLENAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据编码获取数据库名称
     * @return String 数据库名称
     */
    public static String getDBNameByCode(PublicResult publicResult) {
        try {
            Map map = getTableObjectByCode(publicResult);
            if (map.containsKey(WTConstants.DATABASENAME) && !ObjectUtil.isEmpty(map.get(WTConstants.DATABASENAME))) {
                return (String) map.get(WTConstants.DATABASENAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据编码获取数据库对象

     * @return Map 数据库表对象
     */
    public static Map<String,String> getTableObjectByCode(PublicResult publicResult) {
        Object object = publicResult.getData();
        ArrayList list = JSON.parseObject(JSON.toJSONString(object), ArrayList.class);
        if (!list.isEmpty()) {
            return (Map<String,String>) list.get(0);
        } else {
            return new HashMap<>(1);
        }
    }

    /**
     * 根据编码获取NOLOCK数据库全限定名
     * @return String 数据库表全限定名
     */
    public static String getNoLockTable(PublicResult publicResult) {
        String table = getTableByCode(publicResult);
        if (!ObjectUtil.isEmpty(table)) {
            return table + WTConstants.WITH_NOLOCK;
        }
        return "";
    }

    /**
     * 根据编码获取流水号
     * @return String 流水号
     */
    public static String getSerialNumber(PublicResult result) {
        try {
            String serial = (String) result.getData();
            if (ObjectUtil.isEmpty(serial)) {

            } else {
                return serial;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
