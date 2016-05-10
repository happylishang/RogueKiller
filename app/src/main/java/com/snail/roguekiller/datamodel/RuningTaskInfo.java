package com.snail.roguekiller.datamodel;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import com.snail.roguekiller.utils.AppProfile;

/**
 * Created by personal on 16/5/8.
 */
public class RuningTaskInfo {

    public int pid;
    public String processName;
    public String packageName;
    public String applicationName;
    public Drawable appIcon = null;
    public int uid;

    public RuningTaskInfo(){}
    public RuningTaskInfo(ActivityManager.RunningAppProcessInfo info,
                          ApplicationInfo applicationInfo) {
        pid = info.pid;
        uid = info.uid;
        processName = info.processName;
        packageName = applicationInfo.packageName;
        applicationName = String.valueOf(applicationInfo.loadLabel(AppProfile.getContext().getPackageManager()));
        appIcon = applicationInfo.loadIcon(AppProfile.getContext().getPackageManager());
    }

    public static RuningTaskInfo generateInstance(ActivityManager.RunningAppProcessInfo appProcessInfo
            , ApplicationInfo applicationInfo) {
        return new RuningTaskInfo(appProcessInfo, applicationInfo);
    }
}
