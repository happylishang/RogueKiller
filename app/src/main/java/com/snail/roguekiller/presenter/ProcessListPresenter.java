package com.snail.roguekiller.presenter;

import android.view.View;

import com.snail.roguekiller.R;
import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.datamodel.RuningAppInfo;
import com.snail.roguekiller.eventbus.BaseEvent;
import com.snail.roguekiller.eventbus.EventConstants;
import com.snail.roguekiller.eventbus.ProcessTrackEvent;
import com.snail.roguekiller.fragment.ProcessListFragment;
import com.snail.roguekiller.task.ProcessesTrackerTask;
import com.snail.roguekiller.utils.DialogUtils;
import com.snail.roguekiller.utils.PreferrenceHelper;
import com.snail.roguekiller.utils.SystemUtils;


public class ProcessListPresenter extends HomeFragmentItemPresenter<ProcessListFragment> implements
        ProcessListAdapter.OnItemClickListener,
        View.OnClickListener {

    private int mCurrentOperationPosition;

    public ProcessListPresenter(ProcessListFragment target) {
        super(target);
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        mProcessType = PreferrenceHelper.gettCurrentFilter("ProcessList");
    }

    @Override
    public void onSKEventMainThread(BaseEvent event) {
        super.onSKEventMainThread(event);
        if (event.mEventType == EventConstants.GET_TASK) {
            ProcessTrackEvent processTrackEvent = (ProcessTrackEvent) event;
            ProcessListInfo infos = (ProcessListInfo) processTrackEvent.mData;
            combin(infos.mProcessInfos);
            mTarget.onRefreshComplete();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        if (position < 0)
            return;
        mCurrentOperationPosition = position;
        switch (view.getId()) {
            case R.id.lv_container:
                killProcessImadiate(view, position);
//                mTarget.showConfirmDialog(position);
                break;
        }
    }

    private void killProcessImadiate(View view, final int position) {
        mTarget.showKillMessage(mProcessInfos.get(position).applicationName + " has been killed !");
        SystemUtils.killBackgroudApplication(mProcessInfos.get(position).packageName);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
////            SystemUtils.clearBackgroudApplication(mProcessInfos.get(position).packageName);
//            SystemUtils.removeTask(mProcessInfos.get(position).taskID, 0);
//        } else {
////            SystemUtils.removeTask(mProcessInfos.get(position).pid, 0);
//        }
        mProcessInfos.remove(position);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemRemoved(position);
            }
        }, 1);
    }

    @Override
    public void onClick(View view) {
        mTarget.hideConfirmDialog();
        Integer tag = (Integer) view.getTag();
        if (tag != null) {
            switch (tag) {
                case DialogUtils.CONFIRM_ACTION.LETT_ACTION:
                    RuningAppInfo info = mProcessInfos.get(mCurrentOperationPosition);
                    SystemUtils.killBackgroudApplication(info.packageName);
                    mProcessInfos.remove(mCurrentOperationPosition);
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
        new ProcessesTrackerTask().execute(mProcessType);
        mTarget.onRefreshStart();
    }

    @Override
    public void OnPageFilter(int type) {
        mProcessType = type;
        new ProcessesTrackerTask().execute(type);
        mTarget.onRefreshStart();
        PreferrenceHelper.putCurrentFilter("ProcessList", mProcessType);
    }
}
