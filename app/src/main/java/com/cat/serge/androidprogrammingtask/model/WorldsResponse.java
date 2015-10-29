package com.cat.serge.androidprogrammingtask.model;

import java.util.List;

/**
 * WorldsResponse
 *
 * @author korchinsky_s
 * @since 29.10.2015.
 */
public class WorldsResponse {
    public boolean     googleLoginSwitchOn;
    public String      serverVersion;
    public String      facebookLoginSwitchOn;
    public List<World> allAvailableWorlds;
    public boolean     featureHelpshift;
    public String      time;
    public String      info;

    public WorldsResponse() {
    }
}
