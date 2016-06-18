package com.snail.roguekiller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.snail.roguekiller.R;
import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.framework.BaseActivityPresenter;
import com.snail.roguekiller.utils.SystemUtils;
import com.snail.roguekiller.utils.ToastUtils;

import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected BaseActivityPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActionBar();
        getLftTv().setVisibility(View.GONE);
        getRightTv().setVisibility(View.GONE);
        getMiddleTv().setText("关于");
        setContentView(R.layout.activity_about);
    }

    @OnClick(R.id.btn_version)
    void version() {
        ToastUtils.show("Current Version is " + SystemUtils.getVersion());
    }

    @OnClick(R.id.btn_update)
    void upgrade() {
        ToastUtils.show("There is no available upgrade ");
    }

}
