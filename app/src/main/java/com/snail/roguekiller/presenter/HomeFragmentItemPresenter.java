package com.snail.roguekiller.presenter;

import android.support.v4.widget.SwipeRefreshLayout;

import com.snail.roguekiller.datamodel.ToolbarStates;
import com.snail.roguekiller.eventbus.ToolbarRefreshEvent;
import com.snail.roguekiller.fragment.HomeFragmentItem;
import com.snail.roguekiller.fragment.IPageFilter;
import com.snail.roguekiller.fragment.IPageSelect;
import com.snail.roguekiller.framework.BaseFragmentPresenter;
import com.snail.roguekiller.utils.Constants;

import de.greenrobot.event.EventBus;

/**
 * Created by personal on 16/5/11.
 */
public abstract class HomeFragmentItemPresenter<T extends HomeFragmentItem>
        extends BaseFragmentPresenter<T>
        implements SwipeRefreshLayout.OnRefreshListener,
        IPageFilter, IPageSelect {

    public HomeFragmentItemPresenter(T target) {
        super(target);
    }

    public int mProcessType = Constants.ProcessType.ALL;

    public abstract void refresh();

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void OnPageSelect() {
        ToolbarRefreshEvent event = new ToolbarRefreshEvent();
        ToolbarStates toolbarStates = new ToolbarStates();
        toolbarStates.processTyep = mProcessType;
        event.mToolbarStates = toolbarStates;
        EventBus.getDefault().post(event);
    }
}
