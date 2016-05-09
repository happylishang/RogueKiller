package com.snail.roguekiller.framework;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;


abstract public class BaseActivity<T extends BaseActivityPresenter> extends AppCompatActivity {

    protected T mPresenter;
    private ProgressDialog mWaitingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();

        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    abstract protected T createPresenter();


    protected void showWatting(String title, String message) {
        if (mWaitingDialog != null)
            stopWaiting();
        if (!isFinishing()) {
            mWaitingDialog = ProgressDialog.show(this, title, message, true, true);
            mWaitingDialog.setCanceledOnTouchOutside(false);
            if (!mWaitingDialog.isShowing()) {
                mWaitingDialog.show();
            }
        }
    }

    public void showWaiting() {
        if (mWaitingDialog == null) {
            mWaitingDialog = ProgressDialog.show(this, "", "Please Waiting", true, true);
        }
        if (!mWaitingDialog.isShowing() && !isFinishing()) {
            mWaitingDialog.show();
        }

    }

    public void stopWaiting() {
        if (mWaitingDialog != null && !isFinishing()) {
            mWaitingDialog.dismiss();
        }
    }
}
