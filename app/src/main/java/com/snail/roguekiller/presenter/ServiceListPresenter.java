package com.snail.roguekiller.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.snail.roguekiller.R;
import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.RuningTaskInfo;
import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.eventbus.BaseEvent;
import com.snail.roguekiller.eventbus.EventConstants;
import com.snail.roguekiller.eventbus.ServicesTrackEvent;
import com.snail.roguekiller.fragment.ServiceListFragment;
import com.snail.roguekiller.framework.BaseFragmentPresenter;
import com.snail.roguekiller.task.ServicesTrackerTask;
import com.snail.roguekiller.utils.DialogUtils;
import com.snail.roguekiller.utils.SystemUtils;

import java.util.ArrayList;

/**
 * Created by personal on 16/5/7.
 */
public class ServiceListPresenter extends BaseFragmentPresenter<ServiceListFragment> implements
        ProcessListAdapter.OnItemClickListener,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private ProcessListAdapter mAdapter;
    private ArrayList<RuningTaskInfo> mProcessInfos = new ArrayList<>();
    private int mCurrentOperation;

    public ServiceListPresenter(ServiceListFragment target) {
        super(target);
    }

    public void initAdapter() {
        mAdapter = new ProcessListAdapter(mProcessInfos);
        mTarget.initAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        startSearchTask();
    }


    private void startSearchTask() {
        new ServicesTrackerTask().execute();
    }

    @Override
    public void onSKEventMainThread(BaseEvent event) {
        super.onSKEventMainThread(event);
        if (event.mEventType == EventConstants.GET_SERVICE) {
            ServicesTrackEvent processTrackEvent = (ServicesTrackEvent) event;
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
                mTarget.showConfirmDialog(position);
                break;
        }
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
                startSearchTask();
                break;
        }
    }

    public void refresh() {
        startSearchTask();
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
