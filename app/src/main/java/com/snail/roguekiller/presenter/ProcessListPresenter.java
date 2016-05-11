package com.snail.roguekiller.presenter;

import android.view.View;

import com.snail.roguekiller.R;
import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.datamodel.RuningTaskInfo;
import com.snail.roguekiller.eventbus.BaseEvent;
import com.snail.roguekiller.eventbus.EventConstants;
import com.snail.roguekiller.eventbus.ProcessTrackEvent;
import com.snail.roguekiller.fragment.HomeFragmentItem;
import com.snail.roguekiller.fragment.ProcessListFragment;
import com.snail.roguekiller.task.ProcessesTrackerTask;
import com.snail.roguekiller.utils.DialogUtils;
import com.snail.roguekiller.utils.SystemUtils;

import java.util.ArrayList;

/**
 * Created by personal on 16/5/7.
 */
public class ProcessListPresenter extends HomeFragmentItemPresenter<ProcessListFragment> implements
        ProcessListAdapter.OnItemClickListener,
        View.OnClickListener {

    private ProcessListAdapter mAdapter;
    private ArrayList<RuningTaskInfo> mProcessInfos = new ArrayList<>();
    private int mCurrentOperation;
    private HomeFragmentItem.Filter mFilter = HomeFragmentItem.Filter.USER_ONLY;
    private HomeFragmentItem.Action mAction = HomeFragmentItem.Action.CLIKC;

    public ProcessListPresenter(ProcessListFragment target) {
        super(target);
    }

    public void initAdapter() {
        mAdapter = new ProcessListAdapter(mProcessInfos);
        mTarget.initAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        refresh();
    }

    @Override
    public void onSKEventMainThread(BaseEvent event) {
        super.onSKEventMainThread(event);
        if (event.mEventType == EventConstants.GET_TASK) {
            ProcessTrackEvent processTrackEvent = (ProcessTrackEvent) event;
            ProcessListInfo infos = (ProcessListInfo) processTrackEvent.mData;
            mProcessInfos.clear();
            mProcessInfos.addAll(infos.mProcessInfos);
            mAdapter.notifyDataSetChanged();
            mTarget.onRefreshComplete();
        }
    }


    @Override
    public void onItemClick(View view, int position) {

        mCurrentOperation = position;
        switch (view.getId()) {
            case R.id.lv_container:
                killProcessImadiate(view, position);
//                mTarget.showConfirmDialog(position);
                break;
        }
    }

    private void killProcessImadiate(View view, int position) {
        mTarget.showKillMessage(mProcessInfos.get(position).applicationName + " has been killed !");
        mProcessInfos.remove(position);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        }, 200);
    }

    @Override
    public void onClick(View view) {
        mTarget.hideConfirmDialog();
        Integer tag = (Integer) view.getTag();
        if (tag != null) {
            switch (tag) {
                case DialogUtils.CONFIRM_ACTION.LETT_ACTION:
                    RuningTaskInfo info = mProcessInfos.get(mCurrentOperation);
                    SystemUtils.killBackgroudApplication(info.packageName);
                    mProcessInfos.remove(mCurrentOperation);
                    mAdapter.notifyDataSetChanged();
                    break;
                case DialogUtils.CONFIRM_ACTION.RIGHT_ACTION:
                    break;
                default:
                    break;
            }
        }
        switch (view.getId()) {
            case R.id.right:
                refresh();
                break;
        }
    }

    public void refresh() {
        new ProcessesTrackerTask().execute();
        mTarget.onRefreshStart();
    }
}
