package com.snail.roguekiller.datamodel;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

/**
 * Created by personal on 16/5/8.
 */
public class RuningAppInfo {

    public int pid;
    public String processName;
    public String packageName;
    public String applicationName;
    public Drawable appIcon = null;
    public int uid;
    public int taskID;
    public ApplicationInfo mApplicationInfo;
}
