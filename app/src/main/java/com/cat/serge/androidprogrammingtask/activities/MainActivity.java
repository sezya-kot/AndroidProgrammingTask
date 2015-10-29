package com.cat.serge.androidprogrammingtask.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cat.serge.androidprogrammingtask.BuildConfig;
import com.cat.serge.androidprogrammingtask.R;
import com.cat.serge.androidprogrammingtask.eventbus.BusNotifier;
import com.cat.serge.androidprogrammingtask.eventbus.events.CancelEvent;
import com.cat.serge.androidprogrammingtask.eventbus.events.FailEvent;
import com.cat.serge.androidprogrammingtask.eventbus.events.StartEvent;
import com.cat.serge.androidprogrammingtask.eventbus.events.SuccessEvent;
import com.cat.serge.androidprogrammingtask.services.WorldsService;
import com.cat.serge.androidprogrammingtask.strategies.SignInStrategy;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SignInStrategy {

    // region Constants
    private static final String           TAG             = MainActivity.class.getSimpleName();
    private static final java.lang.String TAG_IS_PROGRESS = "is_progress_tag";
    public static final String           TAG_WORLDS_LIST = "worlds_list_tag";
    // endregion

    // region Fields
    @Bind(R.id.login_et) protected     EditText    etLogin;
    @Bind(R.id.password_et) protected  EditText    etPassword;
    @Bind(R.id.sign_in_btn) protected  Button      btnSignIn;
    @Bind(R.id.progress_bar) protected ProgressBar pbProgress;

    private boolean isProgress;
    // endregion

    // region Activity callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BusNotifier.register(this);


        if (savedInstanceState == null) {
            isProgress(false);
            if (BuildConfig.DEBUG) {
                etLogin.setText("android.test@xyrality.com");
                etPassword.setText("password");
            }
        } else {
            isProgress = savedInstanceState.getBoolean(TAG_IS_PROGRESS, false);
        }
        isProgress(isProgress);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(TAG_IS_PROGRESS, isProgress);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        BusNotifier.unregister(this);
    }
    // endregion

    // region OnClickListeners
    @OnClick(R.id.sign_in_btn)
    @SuppressWarnings("unused")
    public void onSignInClick(final View view) {
        if (isValid()) {
            Intent intent = new Intent(this, WorldsService.class);
            intent.putExtra(WorldsService.WHAT_TO_DO_TAG, WorldsService.GET_WORLDS);
            intent.putExtra(WorldsService.LOGIN_TAG, etLogin.getText().toString().trim());
            intent.putExtra(WorldsService.PASSWORD_TAG, etLogin.getText().toString().trim());
            startService(intent);
        } else {
            Toast.makeText(this, R.string.incorrect_credentials, Toast.LENGTH_SHORT).show();
        }
    }
    // endregion

    // region SignInStrategy implementation
    @Subscribe
    @Override
    public void onStartSignIn(StartEvent event) {
        isProgress(true);
    }

    @Subscribe
    @Override
    public void onSuccess(SuccessEvent event) {
        isProgress(false);
        Intent intent = new Intent(this, WorldListActivity.class);
        intent.putParcelableArrayListExtra(
            TAG_WORLDS_LIST
            , (ArrayList<? extends Parcelable>) event.getWorlds()
        );
        startActivity(intent);
    }

    @Subscribe
    @Override
    public void onFail(FailEvent event) {
        isProgress(false);
        Toast.makeText(this, "Some server error!", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    @Override
    public void onCancel(CancelEvent event) {
        isProgress(false);
    }
    // endregion

    // region Private methods
    private boolean isValid() {
        return etLogin != null
            && !TextUtils.isEmpty(etLogin.getText())
            && etPassword != null
            && !TextUtils.isEmpty(etPassword.getText());
    }

    private void isProgress(final boolean isProgress) {
        this.isProgress = isProgress;
        etLogin.setEnabled(!isProgress);
        etPassword.setEnabled(!isProgress);
        btnSignIn.setEnabled(!isProgress);
        pbProgress.setVisibility(isProgress ? View.VISIBLE : View.INVISIBLE);
    }
    // endregion
}
