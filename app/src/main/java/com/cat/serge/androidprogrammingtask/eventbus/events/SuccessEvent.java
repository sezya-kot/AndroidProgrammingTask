package com.cat.serge.androidprogrammingtask.eventbus.events;

import com.cat.serge.androidprogrammingtask.model.World;

import java.util.List;

/**
 * SuccessEvent
 *
 * @author korchinsky_s
 * @since 29.10.2015.
 */
public class SuccessEvent {

    private List<World> mWorlds;

    public SuccessEvent(final List<World> worlds) {
        mWorlds = worlds;
    }

    @SuppressWarnings("unused")
    public List<World> getWorlds() {
        return mWorlds;
    }

    @SuppressWarnings("unused")
    public void setWorlds(final List<World> worlds) {
        mWorlds = worlds;
    }
}
