package com.snail.roguekiller.presenter;

import android.view.View;

import com.snail.roguekiller.R;
import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.ProcessInfo;
import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.eventbus.BaseEvent;
import com.snail.roguekiller.eventbus.EventConstants;
import com.snail.roguekiller.eventbus.ProcessTrackEvent;
import com.snail.roguekiller.fragment.ProcessListFragment;
import com.snail.roguekiller.framework.BaseFragmentPresenter;
import com.snail.roguekiller.task.ProcessesTrackerTask;
import com.snail.roguekiller.utils.DialogUtils;
import com.snail.roguekiller.utils.SystemUtils;

import java.util.ArrayList;

/**
 * Created by personal on 16/5/7.
 */
public class ProcessListPresenter extends BaseFragmentPresenter<ProcessListFragment> implements
        ProcessListAdapter.OnItemClickListener,
        View.OnClickListener {


    private ProcessListAdapter mAdapter;
    private ArrayList<ProcessInfo> mProcessInfos = new ArrayList<>();
    private int mCurrentOperation;

    public ProcessListPresenter(ProcessListFragment target) {
        super(target);
    }

    public void initAdapter() {
        mAdapter = new ProcessListAdapter(mProcessInfos);
        mTarget.initAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        startSearchTask();
    }


    private void startSearchTask() {
        new ProcessesTrackerTask().execute();
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
        }
    }


    @Override
    public void onItemClick(View view, int position) {

        mCurrentOperation = position;
        switch (view.getId()) {
            case R.id.bt_info:
                mTarget.showConfirmDialog(position);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        mTarget.hideConfirmDialog();
        Integer tag = (Integer) view.getTag();
        switch (tag) {
            case DialogUtils.CONFIRM_ACTION.LETT_ACTION:
                ProcessInfo info = mProcessInfos.get(mCurrentOperation);
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
    public void refresh() {
        startSearchTask();
    }

}
