package com.snail.roguekiller.presenter;

import android.view.View;

import com.snail.roguekiller.R;
import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.datamodel.RuningTaskInfo;
import com.snail.roguekiller.eventbus.BaseEvent;
import com.snail.roguekiller.eventbus.EventConstants;
import com.snail.roguekiller.eventbus.ServicesTrackEvent;
import com.snail.roguekiller.fragment.ServiceListFragment;
import com.snail.roguekiller.task.ServicesTrackerTask;
import com.snail.roguekiller.utils.DialogUtils;
import com.snail.roguekiller.utils.SystemUtils;

/**
 * Created by personal on 16/5/7.
 */
public class ServiceListPresenter extends HomeFragmentItemPresenter<ServiceListFragment> implements
        ProcessListAdapter.OnItemClickListener,
        View.OnClickListener {


    private int mCurrentOperation;

    public ServiceListPresenter(ServiceListFragment target) {
        super(target);
    }

    @Override
    public void onSKEventMainThread(BaseEvent event) {
        super.onSKEventMainThread(event);
        if (event.mEventType == EventConstants.GET_SERVICE) {
            ServicesTrackEvent processTrackEvent = (ServicesTrackEvent) event;
            ProcessListInfo infos = (ProcessListInfo) processTrackEvent.mData;
//            for (; mProcessInfos.size() > 0; ) {
//                mProcessInfos.remove(0);
//                mAdapter.notifyItemRemoved(0);
//            }
//            for (int i = 0; i < infos.mProcessInfos.size(); i++) {
//                mProcessInfos.add(i, infos.mProcessInfos.get(i));
//                mAdapter.notifyItemInserted(i);
//            }
            combin(infos.mProcessInfos);
            mTarget.onRefreshComplete();
            mTarget.onRefreshComplete();
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        if (position < 0)
            return;
        mCurrentOperation = position;
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
        mProcessInfos.remove(position);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemRemoved(position);
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
                    SystemUtils.killBackgroudApplication(info.applicationName);
                    mProcessInfos.remove(mCurrentOperation);
                    mAdapter.notifyDataSetChanged();
                    break;
                case DialogUtils.CONFIRM_ACTION.RIGHT_ACTION:
                    break;
                default:
                    break;
            }
        }

    }

    public void refresh() {
        new ServicesTrackerTask().execute(mProcessType);
        mTarget.onRefreshStart();
    }

    @Override
    public void OnPageFilter(int type) {
        mProcessType = type;
        new ServicesTrackerTask().execute(type);
        mTarget.onRefreshStart();
    }
}
