package com.snow.common.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zero
 */
public class GsonUtil {

    private static Gson gson = new GsonBuilder().create();
    private GsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object 对象
     * @return json
     */
    public static String toJson(Object object) {
        if (object != null && gson != null) {
            return gson.toJson(object);
        }
        return null;
    }

    /**
     * 转成bean
     *
     * @param json json字符串
     * @param cls
     * @return
     */
    public static <T> T toBean(String json, Class<T> cls) {
        T t = null;
        if (json != null && gson != null) {
            t = gson.fromJson(json, cls);
        }
        return t;
    }

    /**
     * 转成bean
     *
     * @param obj 对象
     * @param cls class
     * @return 对象
     */
    public static <T> T toBean(Object obj, Class<T> cls) {
        T t = null;
        if (obj != null && gson != null) {
            t = gson.fromJson(toJson(obj), cls);
        }
        return t;
    }

    /**
     * 转成list
     * 解决泛型在编译期类型被擦除导致报错
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        if (StringUtil.isBlank(json)) {
            return null;
        }
        List<T> list = new ArrayList<>();
        JsonElement jsonElement = new JsonParser().parse(json);
        if (!jsonElement.isJsonArray()) {
            return null;
        }
        JsonArray array = jsonElement.getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 转成list
     * 解决泛型在编译期类型被擦除导致报错
     *
     * @param collection 集合
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(Collection collection, Class<T> cls) {
        if (CollectionUtil.isEmpty(collection)) {
            return null;
        }
        List<T> list = new ArrayList<>();
        JsonElement jsonElement = new JsonParser().parse(toJson(collection));
        if (!jsonElement.isJsonArray()) {
            return null;
        }
        JsonArray array = jsonElement.getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gson
     * @return listMap
     */
    public static <T> List<Map<String, T>> toListMaps(String gson) {
        List<Map<String, T>> list = new ArrayList<>();
        JsonElement jsonElement = new JsonParser().parse(gson);
        if (!jsonElement.isJsonArray()) {
            return null;
        }
        JsonArray array = jsonElement.getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(toMaps(toJson(elem)));
        }
        return list;
    }

    /**
     * 转成list中有map的
     *
     * @param obj obj
     * @return listMap
     */
    public static <T> List<Map<String, T>> toListMaps(Object obj) {
        List<Map<String, T>> list = new ArrayList<>();
        JsonElement jsonElement = new JsonParser().parse(toJson(obj));
        if (!jsonElement.isJsonArray()) {
            return null;
        }
        JsonArray array = jsonElement.getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(toMaps(toJson(elem)));
        }
        return list;
    }


    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <K, T> Map<K, T> toMaps(String gsonString) {
        Map<K, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<K, T>>() {
            }.getType());
        }
        return map;
    }


}

