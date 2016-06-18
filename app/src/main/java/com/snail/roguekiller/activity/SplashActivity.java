package com.snail.roguekiller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.framework.BaseActivityPresenter;

public class SplashActivity extends BaseActivity {

    @Override
    protected BaseActivityPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        HideStatusBar();
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.startActivity(SplashActivity.this);
                finish();
            }
        }, 100);
    }

    private void HideStatusBar() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window myWindow = this.getWindow();
        myWindow.setFlags(flag, flag);
    }

}
