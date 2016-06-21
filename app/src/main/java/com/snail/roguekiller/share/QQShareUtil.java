package com.snail.roguekiller.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.snail.roguekiller.R;
import com.snail.roguekiller.constant.Constants;
import com.snail.roguekiller.utils.AppProfile;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Author: hzlishang
 * Data: 16/6/21 上午11:08
 * Des:
 * version:
 */
public class QQShareUtil {
    private Tencent mTencent;

    /**
     * qq 分享
     * @param context
     * @param title     标题
     * @param targetUrl 跳转地址
     * @param imageUrl  图片url
     * @param summary   摘要
     * @param appName   应用名
     */

    /**
     * QQSDK登陆入口
     */

    public static QQShareUtil getInstance() {
        return new QQShareUtil();
    }

    public void shareToQQ(Activity context, String title, String targetUrl, String imageUrl,
                          String summary, String appName) {
        mTencent = Tencent.createInstance(Constants.QQ_LOGIN_APP_ID, context.getApplicationContext());
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
        mTencent.shareToQQ(context, params, mBaseUiListener);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mTencent != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mBaseUiListener);
        }
    }

    private BaseUiListener mBaseUiListener = new BaseUiListener();

    public void releaseResource() {
        if (mTencent != null)
            mTencent.releaseResource();
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onCancel() {
        }

        @Override
        public void onComplete(Object response) {
            doComplete((JSONObject) response);

        }

        protected void doComplete(JSONObject obj) {
            Toast.makeText(AppProfile.getContext(), AppProfile.getContext().getString(R.string.share_ok), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError arg0) {
            Toast.makeText(AppProfile.getContext(), arg0.errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

}
