package com.snail.roguekiller.threadpool;

import android.graphics.drawable.Drawable;
import android.util.LruCache;

/**
 * Author: hzlishang
 * Data: 16/7/28 下午2:37
 * Des:
 * version:
 */
public class LruCacheUtil extends LruCache<String, Drawable> {

    static int MAX_SIZE = (int) (Runtime.getRuntime().maxMemory() / 8);

    public LruCacheUtil() {
        super(MAX_SIZE);
    }

    public static LruCacheUtil getInstance() {
        return new LruCacheUtil();
    }

    @Override
    protected int sizeOf(String key, Drawable value) {
        return value.getIntrinsicHeight() * value.getIntrinsicWidth();
    }
}
