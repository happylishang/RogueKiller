package com.snail.roguekiller.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.snail.roguekiller.R;
import com.snail.roguekiller.constant.Constants;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * Created by zyl06 on 12/21/15.
 */
public class WechatPlatform {
    private IWXAPI api;
    private static final String TAG = "WechatPlatform";

    public static final int TO_FRIEND = WXShareUtil.TO_WEIXIN;
    public static final int TO_MOMENTS = WXShareUtil.TO_WEIXIN_FRIEND;

    public WechatPlatform(Context context) {
        api = WXShareUtil.createWXAPI(context, Constants.WX_APP_ID);
    }

    public static WechatPlatform getInstance(Context contex) {
        ;
        return new WechatPlatform(contex);
    }

    public boolean sendTextContent(String title, String description, int type) {
        return WXShareUtil.sendTextContent(api, title, description, type);
    }

    public boolean sendLocalBitmap(Bitmap srcBitmap, String title, String description, int type) {
        Bitmap bitmap = srcBitmap;
        boolean success = WXShareUtil.sendLocalBitmap(api, bitmap, title, description, type);
        return success;
    }

    public boolean sendLocalImage(String path, String title, String content, int type) {
        return WXShareUtil.sendLocalImage(api, path, title, content, type);
    }

    public boolean shareActionUrl(@NonNull Activity activity, String actionUrl, String title,
                                  String description, int type) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.app_icon);
        WXShareUtil.sendActionUrl(api, title, description, bitmap, actionUrl, type);
        return true;
    }
}
