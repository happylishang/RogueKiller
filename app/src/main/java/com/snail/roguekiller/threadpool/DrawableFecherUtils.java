package com.snail.roguekiller.threadpool;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Author: hzlishang
 * Data: 16/7/28 下午2:17
 * Des:
 * version:
 */
public class DrawableFecherUtils {

    public static void loadDrawable(ImageView imageView, ApplicationInfo applicationInfo) {
        Drawable drawable = LruCacheUtil.getInstance().get(applicationInfo.packageName);
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
            return;
        }
        imageView.setTag(applicationInfo);
        ThreadPoolService.getInstance().addTask(new FecherRunnable(imageView, applicationInfo));
    }
}
