package com.snail.roguekiller.activity;

import android.os.Bundle;

import com.snail.roguekiller.R;
import com.snail.roguekiller.framework.BaseActivity;
import com.snail.roguekiller.presenter.HomePresenter;

public class HomeActivity extends BaseActivity<HomePresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }
}
