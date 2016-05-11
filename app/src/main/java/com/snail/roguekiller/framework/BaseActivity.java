package com.snail.roguekiller.framework;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.snail.roguekiller.R;

import butterknife.ButterKnife;


abstract public class BaseActivity<T extends BaseActivityPresenter> extends AppCompatActivity {

    protected T mPresenter;
    private ProgressDialog mWaitingDialog;
    private CustomedActionbar mCustomedActionbar;
    private View mActionBarView;

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
//        setUpActionBar();
        ButterKnife.bind(this);
    }

    public void setUpActionBar() {
        ActionBar _actionBar = getSupportActionBar();
        mCustomedActionbar = new CustomedActionbar();
        mCustomedActionbar.mActionBar = _actionBar;
        if (_actionBar != null) {
            mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_common, null);
            _actionBar.setDisplayShowCustomEnabled(true);
            _actionBar.setCustomView(mActionBarView, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        }
    }

    public TextView getLftTv() {

        return (TextView) mActionBarView.findViewById(R.id.left);
    }

    public TextView getMiddleTv() {
        return (TextView) mActionBarView.findViewById(R.id.middle);

    }

    public TextView getRightTv() {
        return (TextView) mActionBarView.findViewById(R.id.right);

    }

    @Override
    public void onResume() {
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
