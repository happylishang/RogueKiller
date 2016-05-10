package com.snail.roguekiller.eventbus;

/**
 * Created by personal on 16/5/8.
 */
public class ServicesTrackEvent extends BaseEvent {
    public ServicesTrackEvent() {
        super(EventConstants.GET_SERVICE);
    }
}
