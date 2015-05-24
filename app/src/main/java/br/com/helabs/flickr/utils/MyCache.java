package br.com.helabs.flickr.utils;

import android.content.Context;

import java.io.Serializable;

public class MyCache {

    public static void add(String key, Serializable obj, Context context) {
        DiskCache diskCache = DiskCache.get(context);
        if (diskCache.getAsObject(key) == null) {
            diskCache.put(key, obj, DiskCache.TIME_MINUTE * 30);
        }
    }

    public static boolean has(String key, Context context) {
        DiskCache diskCache = DiskCache.get(context);
        return diskCache.getAsObject(key) != null;
    }

    public static Object get(String key, Context context) {
        DiskCache diskCache = DiskCache.get(context);
        if (diskCache.getAsObject(key) == null)
            return false;
        else
            return diskCache.getAsObject(key);
    }

    public static void clear(String key, Context context) {
        DiskCache diskCache = DiskCache.get(context);
        if (MyCache.has(key, context)) {
            diskCache.remove(key);
        }
    }

    public static void clearAll(Context context) {
        DiskCache diskCache = DiskCache.get(context);
        diskCache.clear();
    }
}
