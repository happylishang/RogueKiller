package com.snail.roguekiller.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by personal on 16/5/9.
 */
public class ToastUtils {

    static Context sContext = AppProfile.getContext();

    public static void show(final String content) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(sContext, content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void show(final @StringRes int stringId) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(sContext, stringId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
