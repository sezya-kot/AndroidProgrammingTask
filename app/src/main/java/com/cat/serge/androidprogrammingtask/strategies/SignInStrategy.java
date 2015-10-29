package com.cat.serge.androidprogrammingtask.strategies;

import com.cat.serge.androidprogrammingtask.eventbus.events.CancelEvent;
import com.cat.serge.androidprogrammingtask.eventbus.events.FailEvent;
import com.cat.serge.androidprogrammingtask.eventbus.events.StartEvent;
import com.cat.serge.androidprogrammingtask.eventbus.events.SuccessEvent;

/**
 * SignInStrategy
 *
 * @author korchinsky_s
 * @since 29.10.2015.
 */
public interface SignInStrategy {

    @SuppressWarnings("unused")
    void onStartSignIn(StartEvent event);

    @SuppressWarnings("unused")
    void onSuccess(SuccessEvent event);

    @SuppressWarnings("unused")
    void onFail(FailEvent event);

    @SuppressWarnings("unused")
    void onCancel(CancelEvent event);

}
