package com.snail.roguekiller.presenter;

import android.support.v4.widget.SwipeRefreshLayout;

import com.snail.roguekiller.fragment.HomeFragmentItem;
import com.snail.roguekiller.framework.BaseFragmentPresenter;

/**
 * Created by personal on 16/5/11.
 */
public abstract class HomeFragmentItemPresenter<T extends HomeFragmentItem> extends BaseFragmentPresenter<T> implements SwipeRefreshLayout.OnRefreshListener {

    public HomeFragmentItemPresenter(T target) {
        super(target);
    }


    public abstract void refresh();

    @Override
    public void onRefresh() {
        refresh();
    }
}
