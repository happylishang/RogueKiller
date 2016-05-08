package com.snail.roguekiller.eventbus;

/**
 * Created by personal on 16/5/8.
 */
public class ProcessTrackEvent extends BaseEvent {
    public ProcessTrackEvent() {
        super(EventConstants.GET_TASK);
    }
}
