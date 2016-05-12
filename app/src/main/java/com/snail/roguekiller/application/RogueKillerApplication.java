package com.snail.roguekiller.application;

import android.app.Application;
import android.content.Intent;

import com.crashlytics.android.Crashlytics;
import com.snail.roguekiller.log.CrashHandler;
import com.snail.roguekiller.service.BackGroundService;
import com.snail.roguekiller.utils.AppProfile;
import com.snail.roguekiller.utils.ServerUtils;

import io.fabric.sdk.android.Fabric;

public class RogueKillerApplication extends Application {

    private static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        startService(new Intent(this, BackGroundService.class));
        init();
    }

    private void init() {


        if (ServerUtils.IS_DEBUG) {
            CrashHandler.getInstance().init(mContext);
        } else if (AppProfile.isMainProcess()) {
            Fabric.with(this, new Crashlytics());
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        timeMemory();
    }

    private void timeMemory() {

    }

    public static Application getApplicationContextInstance() {
        return mContext;
    }
}
