package com.snail.roguekiller.datamodel;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;

/**
 * Created by personal on 16/5/8.
 */
public class ProcessInfo {

    private ProcessInfo(ActivityManager.RunningAppProcessInfo info,
                        ApplicationInfo applicationInfo) {

        processName = info.processName;
        importanceReasonCode = info.importanceReasonCode;
        importanceReasonComponent = info.importanceReasonComponent;
        importanceReasonPid = info.importanceReasonPid;
        lastTrimLevel = info.lastTrimLevel;
        lru = info.lru;
        importance = info.importance;
        pid = info.pid;
        pkgList = info.pkgList;
        uid = info.uid;
    }

    public static ProcessInfo generateInstance(ActivityManager.RunningAppProcessInfo appProcessInfo
            , ApplicationInfo applicationInfo) {
        return new ProcessInfo(appProcessInfo, applicationInfo);
    }

    public int importance;
    public int importanceReasonCode;
    public ComponentName importanceReasonComponent;
    public int importanceReasonPid;
    public int lastTrimLevel;
    public int lru;
    public int pid;
    public String[] pkgList = null;
    public String processName;
    public int uid;
}
