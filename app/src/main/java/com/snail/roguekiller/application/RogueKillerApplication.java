package com.snail.roguekiller.application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class RogueKillerApplication extends Application {

    private static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Fabric.with(this, new Crashlytics());
        init();
    }

    private void init() {

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
