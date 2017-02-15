package com.snail.roguekiller.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.RunningAppInfo;
import com.snail.roguekiller.datamodel.ToolbarStates;
import com.snail.roguekiller.eventbus.ToolbarRefreshEvent;
import com.snail.roguekiller.fragment.HomeFragmentItem;
import com.snail.roguekiller.fragment.IPageFilter;
import com.snail.roguekiller.fragment.IPageSelect;
import com.snail.roguekiller.framework.BaseFragmentPresenter;
import com.snail.roguekiller.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.event.EventBus;


public abstract class HomeFragmentItemPresenter<T extends HomeFragmentItem>
        extends BaseFragmentPresenter<T>
        implements SwipeRefreshLayout.OnRefreshListener,
        IPageFilter, IPageSelect, ProcessListAdapter.OnItemClickListener {

    public HomeFragmentItemPresenter(T target) {
        super(target);
    }

    protected ProcessListAdapter mAdapter;
    public int mProcessType = Constants.ProcessType.ALL;
    private HomeFragmentItem.Action mAction = HomeFragmentItem.Action.CLIKC;
    public ArrayList<RunningAppInfo> mProcessInfos = new ArrayList<>();

    public abstract void refresh();

    public void initAdapter() {
        mAdapter = new ProcessListAdapter(mProcessInfos);
        mTarget.initAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        refresh();
    }

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

    public boolean isInProcessList(RunningAppInfo info, List<RunningAppInfo> processListInfo) {
        for (RunningAppInfo item : processListInfo) {
            if (info.packageName.equals(item.packageName) && info.pid == item.pid)
                return true;
        }
        return false;
    }

    public void combin(List<RunningAppInfo> newList) {

        if (newList == null) {
            mProcessInfos.clear();
            mAdapter.notifyDataSetChanged();
        } else {
            Iterator<RunningAppInfo> iterator = mProcessInfos.iterator();
            while (iterator.hasNext()) {
                RunningAppInfo info = iterator.next();
                if (!isInProcessList(info, newList)) {
                    int pos = mProcessInfos.indexOf(info);
                    iterator.remove();
                    mAdapter.notifyItemRemoved(pos);
                }
            }
            iterator = newList.iterator();
            while (iterator.hasNext()) {
                RunningAppInfo info = iterator.next();
                if (!isInProcessList(info, mProcessInfos)) {
                    int pos = newList.indexOf(info);
                    if (pos > mProcessInfos.size() - 1) {
                        mProcessInfos.add(info);
                    } else {
                        mProcessInfos.add(pos, info);
                    }
                    mAdapter.notifyItemInserted(pos);
                }
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
