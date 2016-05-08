package com.snail.roguekiller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.framework.BaseActivityPresenter;

public class AboutActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent =new Intent(context,AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected BaseActivityPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
