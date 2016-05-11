package com.snail.roguekiller.eventbus;

import com.snail.roguekiller.datamodel.ToolbarStates;

/**
 * Created by personal on 16/5/8.
 */
public class ToolbarRefreshEvent extends BaseEvent {
    public ToolbarRefreshEvent() {
        super(EventConstants.TOOL_BAR_REFRESH);
    }

  public  ToolbarStates mToolbarStates;
}
