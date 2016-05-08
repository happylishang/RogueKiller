package com.snail.roguekiller.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by personal on 16/5/8.
 */
public class SystemUtils {

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

    public static ApplicationInfo getApplicationByProcessName(ActivityManager.RunningAppProcessInfo item) {

        List<ApplicationInfo> list = SystemUtils.getUserApps();
        for (ApplicationInfo info : list) {
            if (info.processName.equals(item.processName) && !isSystemPackage(info)) {
                return info;
            }
        }
        return null;
    }

    public static boolean isSelfApplciation(ApplicationInfo info) {

        return info.packageName.equals(AppProfile.getContext().getPackageName());
    }

    public static boolean isSystemPackage(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }


    public static void killBackgroudApplication(String packageName) {
        ActivityManager am = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        am.killBackgroundProcesses(packageName);
    }

}
