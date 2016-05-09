package com.snail.roguekiller.utils;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class ResourcesUtil {
    public static final String DRAWABLE = "drawable";

    public static String getString(@StringRes int stringId) {
        return AppProfile.getContext().getResources().getString(stringId);
    }

    public static String[] getStringArray(@ArrayRes int arrayId) {
        return AppProfile.getContext().getResources().getStringArray(arrayId);
    }

    public static String stringFormat(@StringRes int stringId, Object... objects) {
        String formatString = getString(stringId);
        return String.format(formatString, objects);
    }

    public static int getColor(@ColorRes int colorId) {
        return AppProfile.getContext().getResources().getColor(colorId);
    }

    public static float getDimen(@DimenRes int dimenId) {
        return AppProfile.getContext().getResources().getDimension(dimenId);
    }

    public static int getDimenPxSize(@DimenRes int dimenId) {
        return AppProfile.getContext().getResources().getDimensionPixelSize(dimenId);
    }

    public static Drawable getDrawable(@DrawableRes int drawableID) {

        return AppProfile.getContext().getResources().getDrawable(drawableID);
    }


    public static String getUrl(int resourceId) {
        Resources r = AppProfile.getContext().getResources();
        String url = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resourceId) + "/"
                + r.getResourceTypeName(resourceId) + "/"
                + r.getResourceEntryName(resourceId);
        return url;
    }

    public static String getDrawableUrl(int resourceId) {
        String url = ResourcesUtil.DRAWABLE + "://"
                + resourceId;

        return url;
    }

    public static Uri getUri(int resourceId) {
        String url = getUrl(resourceId);
        return Uri.parse(url);
    }

    public static Resources getResources() {
        return AppProfile.getContext().getResources();
    }
}
