package com.cat.serge.androidprogrammingtask.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cat.serge.androidprogrammingtask.R;
import com.cat.serge.androidprogrammingtask.adapters.WorldAdapter;
import com.cat.serge.androidprogrammingtask.model.World;
import com.cat.serge.androidprogrammingtask.views.SimpleDividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WorldListActivity extends AppCompatActivity {

    private List<World> mWorlds;
    @Bind(R.id.world_list) protected RecyclerView mRecyclerView;
    private WorldAdapter mWorldAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_list);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            if (getIntent() != null) {
                mWorlds = getIntent().getParcelableArrayListExtra(MainActivity.TAG_WORLDS_LIST);
            }
        } else {
            mWorlds = savedInstanceState.getParcelableArrayList(MainActivity.TAG_WORLDS_LIST);
        }

        setupRecyclerView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecorator(this));

        mWorldAdapter = new WorldAdapter(this,mWorlds);

        mRecyclerView.setAdapter(mWorldAdapter);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(
            MainActivity.TAG_WORLDS_LIST
            , (ArrayList<? extends Parcelable>) mWorlds
        );

    }
}
