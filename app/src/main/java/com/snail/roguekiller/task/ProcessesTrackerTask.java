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
import com.snail.roguekiller.datamodel.RuningAppInfo;
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
        ArrayList<RuningAppInfo> processInfos = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = AndroidProcesses.getRunningAppProcessInfo(AppProfile.getContext());
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                ApplicationInfo applicationInfo = getApplciationInfo(runningAppProcessInfo, type);
                if (applicationInfo != null && !SystemUtils.isSelfApplciation(applicationInfo)) {
                    RuningAppInfo runingTaskInfo = new RuningAppInfo();
                    runingTaskInfo.pid = runningAppProcessInfo.pid;
                    runingTaskInfo.uid = runningAppProcessInfo.uid;
                    runingTaskInfo.processName = runningAppProcessInfo.processName;
                    runingTaskInfo.packageName = applicationInfo.packageName;
                    runingTaskInfo.applicationName = String.valueOf(applicationInfo.loadLabel(AppProfile.getContext().getPackageManager()));
                    runingTaskInfo.appIcon = applicationInfo.loadIcon(AppProfile.getContext().getPackageManager());
                    processInfos.add(runingTaskInfo);
                }
            }
        } else {
            ActivityManager manager = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
            List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(100);
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                ApplicationInfo applicationInfo = getApplciationInfo(runningAppProcessInfo, type);
                if (applicationInfo != null && !SystemUtils.isSelfApplciation(applicationInfo)) {
                    RuningAppInfo runingTaskInfo = new RuningAppInfo();
                    runingTaskInfo.pid = runningAppProcessInfo.pid;
                    runingTaskInfo.uid = runningAppProcessInfo.uid;
                    runingTaskInfo.processName = runningAppProcessInfo.processName;
                    runingTaskInfo.packageName = applicationInfo.packageName;
                    runingTaskInfo.applicationName = String.valueOf(applicationInfo.loadLabel(AppProfile.getContext().getPackageManager()));
                    runingTaskInfo.appIcon = applicationInfo.loadIcon(AppProfile.getContext().getPackageManager());
                    processInfos.add(runingTaskInfo);
                }
            }
            addTaskId(runningTaskInfos,processInfos);
        }
        processInfos = sortByFirstCase(processInfos);
        ProcessTrackEvent event = new ProcessTrackEvent();
        ProcessListInfo processListInfo = new ProcessListInfo();
        processListInfo.mProcessInfos = processInfos;
        event.mData = processListInfo;
        EventBus.getDefault().post(event);
        return null;
    }

    public static ArrayList<RuningAppInfo> sortByFirstCase(ArrayList<RuningAppInfo> processInfos) {

        ArrayList<RuningAppInfo> _tempInfos = new ArrayList<>();
        int cycle = processInfos.size();
        for (int i = 0; i < cycle; i++) {
            RuningAppInfo _info = processInfos.get(processInfos.size() - 1);
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

    public void addTaskId(List<ActivityManager.RunningTaskInfo> infos,
                          List<RuningAppInfo> runningAppProcessInfos) {

        for (ActivityManager.RunningTaskInfo info : infos) {
            for (RuningAppInfo item : runningAppProcessInfos) {
                if (info.baseActivity.getPackageName().equals(item.packageName)) {
                    item.taskID = info.id;
                    break;
                }
            }
        }
    }
}