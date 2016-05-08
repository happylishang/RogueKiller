package com.snail.roguekiller.eventbus;

/**
 * Created by personal on 16/5/7.
 */
public class BaseEvent {

    public BaseEvent(int type) {
        mEventType = type;
    }

    public int mEventType;
    public Object mData;
    public String mDesc;
}
