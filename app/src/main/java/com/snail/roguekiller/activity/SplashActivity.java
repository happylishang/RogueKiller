package com.snail.roguekiller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.framework.BaseActivityPresenter;

public class SplashActivity extends BaseActivity {

    @Override
    protected BaseActivityPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                HomeActivity.startActivity(SplashActivity.this);
                finish();
            }
        }, 1000);
    }
}
