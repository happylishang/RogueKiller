package com.snail.roguekiller.framework;

import com.snail.roguekiller.eventbus.BaseEvent;

import de.greenrobot.event.EventBus;


/**
 * Created by personal on 16/5/7.
 */
public class BaseActivityPresenter<T extends BaseActivity> extends BasePresenter<T> {


    public BaseActivityPresenter(T target) {

        super(target);

    }

    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    public void onStart() {

    }

    public void onStop() {

    }

    public void onResume() {

    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(BaseEvent event) {

        onSKEventMainThread(event);
    }

    public void onSKEventMainThread(BaseEvent event) {

    }
}
