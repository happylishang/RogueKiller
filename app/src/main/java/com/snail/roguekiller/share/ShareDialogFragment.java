package com.snail.roguekiller.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.snail.roguekiller.R;
import com.snail.roguekiller.constant.Constants;

/**
 * Author: hzlishang
 * Data: 16/6/20 下午9:03
 * Des:
 * version:
 */
public class ShareDialogFragment extends DialogFragment implements View.OnClickListener {

    private View mShareToFriend;
    private QQShareUtil mQQShareUtil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_wechat).setOnClickListener(this);
        view.findViewById(R.id.btn_wechat_time).setOnClickListener(this);
        view.findViewById(R.id.btn_qq).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_wechat:
                WechatPlatform.getInstance(getActivity()).shareActionUrl(getActivity(), Constants.APP_URL,
                        getString(R.string.app_name), getString(R.string.slogen), WXShareUtil.TO_WEIXIN);
                break;
            case R.id.btn_wechat_time:
                WechatPlatform.getInstance(getActivity()).shareActionUrl(getActivity(), Constants.APP_URL,
                        getString(R.string.app_name), getString(R.string.slogen), WXShareUtil.TO_WEIXIN_FRIEND);
                break;
            case R.id.btn_qq:
                mQQShareUtil = QQShareUtil.getInstance();
                mQQShareUtil.shareToQQ(getActivity(), getString(R.string.app_name), Constants.APP_URL,
                        Constants.APP_LOGO_URL, getString(R.string.slogen), getString(R.string.app_name));
                break;
            default:
                break;

        }
        dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mQQShareUtil != null) {
            mQQShareUtil.onActivityResult(requestCode, resultCode, data);
        }
        dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mQQShareUtil != null)
            mQQShareUtil.releaseResource();
    }

}
