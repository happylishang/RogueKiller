package com.snail.roguekiller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.snail.roguekiller.framework.BaseFragment;
import com.snail.roguekiller.framework.BaseFragmentPresenter;

/**
 * Created by personal on 16/5/10.
 */
public abstract class HomeFragmentItem<T extends BaseFragmentPresenter> extends BaseFragment<T> implements IPageRefresh {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
