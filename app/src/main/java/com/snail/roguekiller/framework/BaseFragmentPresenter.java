package com.snail.roguekiller.framework;

import com.snail.roguekiller.eventbus.BaseEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by personal on 16/5/7.
 */
public class BaseFragmentPresenter<T extends BaseFragment> extends BasePresenter<T> {

    public BaseFragmentPresenter(T target) {
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

    public void onActivityCreated() {

    }
    public void onDestroy() {

        EventBus.getDefault().unregister(this);
    }

    public void onPause() {

    }

    public void  onViewCreated(){

    }

    public final void  onEventMainThread(BaseEvent event) {

        if (mTarget != null && mTarget.isViewInflated()) {
            onSKEventMainThread(event);
        }
    }

    public void onSKEventMainThread(BaseEvent event) {

    }
}
