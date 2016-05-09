package com.snail.roguekiller.task;

/**
 * Created by personal on 16/5/8.
 */

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;

import com.snail.roguekiller.datamodel.ProcessInfo;
import com.snail.roguekiller.datamodel.ProcessListInfo;
import com.snail.roguekiller.eventbus.ProcessTrackEvent;
import com.snail.roguekiller.utils.AppProfile;
import com.snail.roguekiller.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ProcessesTrackerTask extends AsyncTask {


    @Override
    protected Object doInBackground(Object[] objects) {
        ArrayList<ProcessInfo> processInfos = new ArrayList<>();
        ActivityManager manager = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> task = manager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo item : task) {
            ApplicationInfo applicationInfo = SystemUtils.getApplicationByProcessName(item);
            if (applicationInfo != null && !SystemUtils.isSelfApplciation(applicationInfo)) {
                processInfos.add(ProcessInfo.generateInstance(item, applicationInfo));
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

    public static ArrayList<ProcessInfo> sortByFirstCase(ArrayList<ProcessInfo> processInfos) {

        ArrayList<ProcessInfo> _tempInfos = new ArrayList<>();
        int cycle = processInfos.size();
        for (int i = 0; i < cycle; i++) {
            ProcessInfo _info = processInfos.get(processInfos.size() - 1);
            for (int j = processInfos.size() - 1; j > 0; j--) {
                if (_info.appliationName.compareTo(processInfos.get(j - 1).appliationName) > 0) {
                    _info = processInfos.get(j - 1);
                }
            }
            processInfos.remove(_info);
            _tempInfos.add(i, _info);
        }
        return _tempInfos;
    }
}