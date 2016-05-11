package com.snail.roguekiller.fragment;

import android.support.annotation.NonNull;

import com.snail.roguekiller.framework.BaseFragment;
import com.snail.roguekiller.presenter.HomeFragmentItemPresenter;
import com.snail.roguekiller.utils.Constants;

/**
 * Created by personal on 16/5/10.
 */
public abstract class HomeFragmentItem<T extends HomeFragmentItemPresenter>
        extends BaseFragment<T>
        implements IPageRefresh,
        IPageFilter {
    public int mCurrentType = Constants.ProcessType.ALL;

    public enum Filter {
        USER_ONLY,
        ALL
    }

    public enum Action {
        CLIKC,
        DIALOG
    }

    public void refreshToolbar() {
        mPresenter.OnPageSelect();
    }

    @Override
    public void OnPageFilter(int type) {
        mPresenter.OnPageFilter(type);
        mCurrentType = type;
    }

    public abstract void showKillMessage(@NonNull String content);

    @Override
    public void OnPageSelect() {
        refreshToolbar();
    }
}
