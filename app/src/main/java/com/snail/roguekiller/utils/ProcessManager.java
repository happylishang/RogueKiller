package com.snail.roguekiller.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {

    private static final String TAG = "ProcessManager";

    private static final String APP_ID_PATTERN;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            APP_ID_PATTERN = "u\\d+_a\\d+";
        } else {
            APP_ID_PATTERN = "app_\\d+";
        }
    }

    public static List<ProcessInfo> getRunningProcesses() {
        List<ProcessInfo> processes = new ArrayList<ProcessInfo>();
        List<String> stdout = null;
        stdout = runCmd("toolbox ps -p -P -x -c");
        for (String line : stdout) {
            try {
                processes.add(new ProcessInfo(line));
            } catch (Exception e) {
                android.util.Log.d(TAG, "Failed parsing line " + line);
            }
        }
        return processes;
    }

    public static List<String> runCmd(String cmd) {
        java.lang.Process p = null;
        List<String> res = new ArrayList<String>();
        try {
            p = Runtime.getRuntime().exec(cmd);
            InputStream is1 = p.getInputStream();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
            String line1 = null;
            try {
                while ((line1 = br1.readLine()) != null) {
                    res.add(line1.trim());
                }
                br1.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                p.getErrorStream().close();
                p.getInputStream().close();
                p.getOutputStream().close();
            } catch (IOException ioe) {
                e.printStackTrace();
            }
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return res;
    }

    public static class ProcessInfo implements Parcelable {

        /**
         * User name
         */
        public final String user;

        /**
         * User ID
         */
        public final int uid;

        /**
         * Processes ID
         */
        public final int pid;

        /**
         * Parent processes ID
         */
        public final int ppid;

        /**
         * The process name
         */
        public final String name;

        // Much dirty. Much ugly.
        private ProcessInfo(String line) throws Exception {
//\\s表示 空格,回车,换行等空白符
            String[] fields = line.split("\\s+");
            user = fields[0];
//根据user获取uid
            uid = android.os.Process.getUidForName(user);
            pid = Integer.parseInt(fields[1]);
            ppid = Integer.parseInt(fields[2]);
//获取进程名称
            if (fields.length == 16) {
                name = fields[13];
            } else {
                name = fields[14];
            }
        }

        private ProcessInfo(Parcel in) {
            user = in.readString();
            uid = in.readInt();
            pid = in.readInt();
            ppid = in.readInt();
            name = in.readString();
        }

        public String getPackageName() {
            if (!user.matches(APP_ID_PATTERN)) {
// this process is not an application
                return null;
            } else if (name.contains(":")) {
// background service running in another process than the main app process
                return name.split(":")[0];
            }
            return name;
        }

        public PackageInfo getPackageInfo(Context context, int flags)
                throws PackageManager.NameNotFoundException {
            String packageName = getPackageName();
            if (packageName == null) {
                throw new PackageManager.NameNotFoundException(name + " is not an application process");
            }
            PackageInfo info =new PackageInfo();
            return context.getPackageManager().getPackageInfo(packageName, flags);
        }

        public ApplicationInfo getApplicationInfo(Context context, int flags)
                throws PackageManager.NameNotFoundException {
            String packageName = getPackageName();
            if (packageName == null) {
                throw new PackageManager.NameNotFoundException(name + " is not an application process");
            }
            return context.getPackageManager().getApplicationInfo(packageName, flags);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(user);
            dest.writeInt(uid);
            dest.writeInt(pid);
            dest.writeInt(ppid);
            dest.writeString(name);
        }

        public static final Creator<ProcessInfo> CREATOR = new Creator<ProcessInfo>() {

            public ProcessInfo createFromParcel(Parcel source) {
                return new ProcessInfo(source);
            }

            public ProcessInfo[] newArray(int size) {
                return new ProcessInfo[size];
            }
        };
    }
}



