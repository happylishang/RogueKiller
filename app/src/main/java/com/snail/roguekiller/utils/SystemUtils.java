package com.snail.roguekiller.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import com.jaredrummler.android.processes.AndroidProcesses;

import java.lang.reflect.Method;
import java.util.List;

public class SystemUtils {

    public static List<ApplicationInfo> getInstalledApplications() {
        final PackageManager pm = AppProfile.getContext().getPackageManager();
        return pm.getInstalledApplications(PackageManager.GET_META_DATA);
    }


    private static boolean isSystemPackage(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }


    private static boolean isSystemPackage(ResolveInfo resolveInfo) {
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public static ApplicationInfo getUnSystemApplicationByName(ActivityManager.RunningAppProcessInfo item) {

        List<ApplicationInfo> list = SystemUtils.getInstalledApplications();
        for (ApplicationInfo info : list) {
            if (info.processName.equals(item.processName) && !isSystemPackage(info)) {
                return info;
            }
        }
        return null;
    }


    public static ApplicationInfo getServiceProcessByName(ActivityManager.RunningAppProcessInfo item) {

        List<ApplicationInfo> list = SystemUtils.getInstalledApplications();
        if (item.processName.contains(":")) {
            String name = item.processName.split(":")[0];
            for (ApplicationInfo info : list) {
                if (info.processName.equals(name)) {
                    return info;
                }
            }
        } else {
            for (ApplicationInfo info : list) {
                if (info.processName.equals(item.processName)) {
                    return info;
                }
            }
        }
        return null;
    }

    public static ApplicationInfo getUnSystemServiceProcessByName(ActivityManager.RunningAppProcessInfo item) {

        List<ApplicationInfo> list = SystemUtils.getInstalledApplications();
        if (item.processName.contains(":")) {
            String name = item.processName.split(":")[0];
            for (ApplicationInfo info : list) {
                if (!isSystemPackage(info) && info.processName.equals(name)) {
                    return info;
                }
            }
        } else {
            for (ApplicationInfo info : list) {
                if (!isSystemPackage(info) && info.processName.equals(item.processName)) {
                    return info;
                }
            }
        }
        return null;
    }

    public static ApplicationInfo getForgroundApplicationByProcessName(ActivityManager.RunningAppProcessInfo item) {

        List<ApplicationInfo> list = SystemUtils.getInstalledApplications();
        if (!item.processName.contains(":")) {
            for (ApplicationInfo info : list) {
                if (info.processName.equals(item.processName)) {
                    return info;
                }
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

    public static void clearBackgroudApplication(String packageName) {
        ActivityManager am = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        am.restartPackage(packageName);
    }


    /**
     * Completely remove the given task.
     *
     * @param taskId Identifier of the task to be removed.
     * @param flags  Additional operational flags.  May be 0 or
     * @return Returns true if the given task was found and removed.
     */
    public static boolean removeTask(int taskId, int flags) {

        ActivityManager mActivityManager = null;
        Method mRemoveTask = null;
        boolean ret = false;
        try {
            Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
            mActivityManager = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                mRemoveTask = activityManagerClass.getMethod("removeTask", new Class[]{int.class});
                mRemoveTask.setAccessible(true);
                ret = (Boolean) mRemoveTask.invoke(mActivityManager, Integer.valueOf(taskId));
                return ret;

            } else {
                mRemoveTask = activityManagerClass.getMethod("removeTask", new Class[]{int.class, int.class});
                mRemoveTask.setAccessible(true);
                ret = (Boolean)mRemoveTask.invoke(mActivityManager, Integer.valueOf(taskId), Integer.valueOf(flags));
                return ret;
            }

        } catch (ClassNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void clearRecentTasks() {
        ActivityManager mActivityManager = null;
        Method mRemoveTask = null;
        try {
            Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
            mActivityManager = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);

            mRemoveTask = activityManagerClass.getMethod("removeTask", new Class[]{int.class, int.class});
            mRemoveTask.setAccessible(true);

        } catch (ClassNotFoundException e) {
        } catch (Exception e) {
        }
        List<ActivityManager.RunningAppProcessInfo> recents = AndroidProcesses.getRunningAppProcessInfo(AppProfile.getContext());
        // Start from 1, since we don't want to kill ourselves!
        for (int i = 1; i < recents.size(); i++) {
            removeTask(recents.get(i).pid, 0);
        }
    }


    public static String getVersion() {
        PackageManager manager = AppProfile.getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(AppProfile.getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;

    }

    private void getRunningAppProcesses() {
        ActivityManager am = (ActivityManager) AppProfile.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = am.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo rsi : list) {
        }
    }
}
