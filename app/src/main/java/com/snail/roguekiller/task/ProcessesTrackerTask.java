package com.snail.roguekiller.task;

/**
 * Created by personal on 16/5/8.
 */

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Build;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.datamodel.RuningTaskInfo;
import com.snail.roguekiller.eventbus.ProcessTrackEvent;
import com.snail.roguekiller.utils.AppProfile;
import com.snail.roguekiller.utils.Constants;
import com.snail.roguekiller.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ProcessesTrackerTask extends AsyncTask<Integer, Integer, Integer> {


    @Override
    protected Integer doInBackground(Integer[] objects) {
        int type = objects.length > 0 ? objects[0].intValue() : Constants.ProcessType.ALL;
        ArrayList<RuningTaskInfo> processInfos = new ArrayList<>();
        ActivityManager manager = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = AndroidProcesses.getRunningAppProcessInfo(AppProfile.getContext());
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                ApplicationInfo applicationInfo = getApplciationInfo(runningAppProcessInfo, type);
                if (applicationInfo != null && !SystemUtils.isSelfApplciation(applicationInfo)) {
                    processInfos.add(RuningTaskInfo.generateInstance(runningAppProcessInfo, applicationInfo));
                }
            }
        } else {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                ApplicationInfo applicationInfo = getApplciationInfo(runningAppProcessInfo, type);
                if (applicationInfo != null && !SystemUtils.isSelfApplciation(applicationInfo)) {
                    processInfos.add(RuningTaskInfo.generateInstance(runningAppProcessInfo, applicationInfo));
                }
            }
        }
        processInfos = sortByFirstCase(processInfos);
        ProcessTrackEvent event = new ProcessTrackEvent();
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

    private ApplicationInfo getApplciationInfo(ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int type) {
        ApplicationInfo applicationInfo;
        if (type == Constants.ProcessType.ALL) {
            applicationInfo = SystemUtils.getForgroundApplicationByProcessName(runningAppProcessInfo);
        } else {
            applicationInfo = SystemUtils.getUnSystemApplicationByName(runningAppProcessInfo);
        }
        return applicationInfo;
    }
}