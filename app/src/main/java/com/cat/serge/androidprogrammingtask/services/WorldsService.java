package com.cat.serge.androidprogrammingtask.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.cat.serge.androidprogrammingtask.R;
import com.cat.serge.androidprogrammingtask.domain.NetworkBusinessLayer;

/**
 * WorldsService
 *
 * @author korchinsky_s
 * @since 29.10.2015.
 */
public class WorldsService extends IntentService {

    public static final String TAG            = WorldsService.class.getSimpleName();
    public static final String WHAT_TO_DO_TAG = "what_to_do";
    public static final int    GET_WORLDS     = 100500;
    public static final String LOGIN_TAG      = "login_tag";
    public static final String PASSWORD_TAG   = "password_tag";

    public WorldsService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        if (intent != null) {
            if (intent.getIntExtra(WHAT_TO_DO_TAG, 0) == GET_WORLDS) {

                NetworkBusinessLayer
                    .getInstance(this)
                    .getWorlds(
                        intent.getStringExtra(LOGIN_TAG)
                        , intent.getStringExtra(PASSWORD_TAG)
                    );
            }
        } else {
            Log.e(TAG, getString(R.string.error_incorect_key));
        }

    }
}
