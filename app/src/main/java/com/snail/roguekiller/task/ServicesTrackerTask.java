package com.snail.roguekiller.task;

/**
 * Created by personal on 16/5/8.
 */

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;

import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.datamodel.RunningAppInfo;
import com.snail.roguekiller.eventbus.ServicesTrackEvent;
import com.snail.roguekiller.utils.AppProfile;
import com.snail.roguekiller.utils.Constants;
import com.snail.roguekiller.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ServicesTrackerTask extends AsyncTask<Integer, Integer, Integer> {


    @Override
    protected Integer doInBackground(Integer[] objects) {
        int type = objects.length > 0 ? objects[0].intValue() : Constants.ProcessType.ALL;
        ArrayList<RunningAppInfo> processInfos = new ArrayList<>();
        ActivityManager manager = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);


        List<ActivityManager.RunningServiceInfo> _listRunServeces = manager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo service : _listRunServeces) {
            RunningAppInfo processInfo = new RunningAppInfo();
            processInfo.packageName = service.service.getPackageName();
            processInfo.processName = service.process;
            processInfo.applicationName = String.valueOf(service.process);
            ActivityManager.RunningAppProcessInfo info = new ActivityManager.RunningAppProcessInfo(service.process, service.pid, null);
            ApplicationInfo applicationInfo = getApplciationInfo(info, type);
            if (applicationInfo != null) {
                processInfo.pid = service.pid;
                processInfo.mApplicationInfo =applicationInfo;
                processInfos.add(processInfo);
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

    public static ArrayList<RunningAppInfo> sortByFirstCase(ArrayList<RunningAppInfo> processInfos) {

        ArrayList<RunningAppInfo> _tempInfos = new ArrayList<>();
        int cycle = processInfos.size();
        for (int i = 0; i < cycle; i++) {
            RunningAppInfo _info = processInfos.get(processInfos.size() - 1);
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
            applicationInfo = SystemUtils.getServiceProcessByName(runningAppProcessInfo);
        } else {
            applicationInfo = SystemUtils.getUnSystemServiceProcessByName(runningAppProcessInfo);
        }
        return applicationInfo;
    }
}