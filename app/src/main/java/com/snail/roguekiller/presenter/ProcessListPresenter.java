package com.snail.roguekiller.presenter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.snail.roguekiller.adapter.ProcessListAdapter;
import com.snail.roguekiller.datamodel.ProcessInfo;
import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.eventbus.BaseEvent;
import com.snail.roguekiller.eventbus.EventConstants;
import com.snail.roguekiller.eventbus.ProcessTrackEvent;
import com.snail.roguekiller.fragment.ProcessListFragment;
import com.snail.roguekiller.framework.BaseFragmentPresenter;
import com.snail.roguekiller.utils.AppProfile;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by personal on 16/5/7.
 */
public class ProcessListPresenter extends BaseFragmentPresenter<ProcessListFragment> {

    private RecyclerView.Adapter mAdapter;
    private ArrayList<ProcessInfo> mProcessInfos = new ArrayList<>();

    public ProcessListPresenter(ProcessListFragment target) {
        super(target);
    }

    public void initAdapter() {
        mAdapter = new ProcessListAdapter(mProcessInfos);
        mTarget.initAdapter(mAdapter);
        startSearchTask();
    }


    private void startSearchTask() {
        new ProcessesTrackerTask().execute();
    }

    static class ProcessesTrackerTask extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {
            ArrayList<ProcessInfo> processInfos = new ArrayList<>();
            ActivityManager manager = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            PackageManager packageUtil = AppProfile.getContext().getPackageManager();
            List<ActivityManager.RunningAppProcessInfo> task = manager.getRunningAppProcesses();

            List<ApplicationInfo> applicationInfos = getUserApps();

            for (ActivityManager.RunningAppProcessInfo item : task) {
                ApplicationInfo applicationInfo = getApplicationByProcessName(item);
                if (applicationInfo != null) {
                    processInfos.add(ProcessInfo.generateInstance(item, applicationInfo));
                }
            }
            ProcessTrackEvent event = new ProcessTrackEvent();
            ProcessListInfo processListInfo = new ProcessListInfo();
            processListInfo.mProcessInfos = processInfos;
            event.mData = processListInfo;
            EventBus.getDefault().post(event);
            return null;
        }
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


    public static ApplicationInfo getApplicationByProcessName(ActivityManager.RunningAppProcessInfo item) {

        List<ApplicationInfo> list = getUserApps();
        for (ApplicationInfo info : list) {
            if (info.processName.equals(item.processName) && !isSystemPackage(info)) {
                return info;
            }
        }
        return null;
    }

    public static List<ApplicationInfo> getUserApps() {
        final PackageManager pm = AppProfile.getContext().getPackageManager();
        return pm.getInstalledApplications(PackageManager.GET_META_DATA);
    }


    private static boolean isSystemPackage(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }


    private static boolean isSystemPackage(ResolveInfo resolveInfo) {
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }


    private static boolean isSystemPackage(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
