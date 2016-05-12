package com.snail.roguekiller.framework;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.snail.roguekiller.fragment.IPageSelect;

/**
 * Created by personal on 16/5/7.
 */
abstract public class BaseFragment<T extends BaseFragmentPresenter> extends Fragment implements IPageSelect {

    protected T mPresenter;

    private boolean mIsViewInflated;
    private ProgressDialog mWaitingDialog;

    public BaseFragment(){
        mPresenter = createPresenter();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("lishang","f create");

//        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsViewInflated = true;
        if (mPresenter != null) {
            mPresenter.onViewCreated();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.onActivityCreated();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    abstract protected T createPresenter();

    public boolean isViewInflated() {
        return mIsViewInflated;
    }

    public void showWaiting() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showWaiting();
        }
    }

    public void stopWaiting() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).stopWaiting();
        }
    }
}
