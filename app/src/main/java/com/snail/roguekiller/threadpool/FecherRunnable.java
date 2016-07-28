package com.snail.roguekiller.threadpool;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.snail.roguekiller.utils.AppProfile;

/**
 * Author: hzlishang
 * Data: 16/7/28 下午2:19
 * Des:
 * version:
 */
public class FecherRunnable implements Runnable {


    private ImageView mImageView;
    private ApplicationInfo mApplicationInfo;

    public FecherRunnable(ImageView imageView, ApplicationInfo applicationInfo) {
        mImageView = imageView;
        mApplicationInfo = applicationInfo;
    }

    @Override
    public void run() {
        final Drawable appIcon = mApplicationInfo.loadIcon(AppProfile.getContext().getPackageManager());
        LruCacheUtil.getInstance().put(mApplicationInfo.packageName,appIcon);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Object object = mImageView.getTag();
                if (object instanceof ApplicationInfo) {
                    ApplicationInfo applicationInfo = (ApplicationInfo) object;
                    if (applicationInfo.packageName.endsWith(mApplicationInfo.packageName)) {
                        mImageView.setImageDrawable(appIcon);
                    }
                }
            }
        });
    }
}
