package com.snail.roguekiller.task;

/**
 * Created by personal on 16/5/8.
 */

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Build;

import com.snail.roguekiller.datamodel.RuningTaskInfo;
import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.eventbus.ServicesTrackEvent;
import com.snail.roguekiller.utils.AppProfile;
import com.snail.roguekiller.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ServicesTrackerTask extends AsyncTask {


    @Override
    protected Object doInBackground(Object[] objects) {
        ArrayList<RuningTaskInfo> processInfos = new ArrayList<>();
        ActivityManager manager = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            List<ActivityManager.RunningServiceInfo> _listRunServeces = manager.getRunningServices(Integer.MAX_VALUE);
            for (ActivityManager.RunningServiceInfo service : _listRunServeces) {

                RuningTaskInfo processInfo = new RuningTaskInfo();
                processInfo.processName = service.process;
                processInfo.applicationName = String.valueOf(service.process);
                ActivityManager.RunningAppProcessInfo info = new ActivityManager.RunningAppProcessInfo(service.process, service.pid, null);
                ApplicationInfo applicationInfo = SystemUtils.getApplicationByProcessName(info);
                if (applicationInfo != null) {
                    processInfo.pid = service.pid;
                    processInfo.appIcon = applicationInfo.loadIcon(AppProfile.getContext().getPackageManager());
                }
                processInfos.add(processInfo);
            }
        } else {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                ApplicationInfo applicationInfo = SystemUtils.getApplicationByProcessName(runningAppProcessInfo);
                if (applicationInfo != null && !SystemUtils.isSelfApplciation(applicationInfo)) {
                    processInfos.add(RuningTaskInfo.generateInstance(runningAppProcessInfo, applicationInfo));
                }
            }
        }
        processInfos = sortByFirstCase(processInfos);
        ServicesTrackEvent event = new ServicesTrackEvent();
        ProcessListInfo processListInfo = new ProcessListInfo();
        processListInfo.mProcessInfos = processInfos;
        event.mData = processListInfo;
        EventBus.getDefault().post(event);
        return null;
    }

    public static ArrayList<RuningTaskInfo> sortByFirstCase(ArrayList<RuningTaskInfo> processInfos) {

        ArrayList<RuningTaskInfo> _tempInfos = new ArrayList<>();
        int cycle = processInfos.size();
        for (int i = 0; i < cycle; i++) {
            RuningTaskInfo _info = processInfos.get(processInfos.size() - 1);
            for (int j = processInfos.size() - 1; j > 0; j--) {
                if (_info.applicationName.compareTo(processInfos.get(j - 1).applicationName) > 0) {
                    _info = processInfos.get(j - 1);
                }
            }
            processInfos.remove(_info);
            _tempInfos.add(i, _info);
        }
        return _tempInfos;
    }
}