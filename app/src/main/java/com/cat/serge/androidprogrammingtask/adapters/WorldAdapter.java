package com.cat.serge.androidprogrammingtask.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cat.serge.androidprogrammingtask.R;
import com.cat.serge.androidprogrammingtask.model.World;

import java.util.List;

/**
 * WorldAdapter
 *
 * @author korchinsky_s
 * @since 29.10.2015.
 */
public class WorldAdapter extends RecyclerView.Adapter {

    // region Constants
    private static final String TAG = WorldAdapter.class.getSimpleName();
    // endregion

    // region Fields
    private LayoutInflater mInflater;
    private int            mItemLayoutResId;
    private List<World>    mWorlds;
    // endregion

    public WorldAdapter(final Context context, final List<World> worlds) {
        mInflater = LayoutInflater.from(context);
        mItemLayoutResId = R.layout.view_item;
        mWorlds = worlds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int i) {
        View itemView = mInflater.inflate(mItemLayoutResId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        World world = mWorlds.get(position);
        viewHolder.mName.setText(world.name);
        viewHolder.mId.setText(String.valueOf(world.id));
        viewHolder.mCounty.setText(world.country);
        viewHolder.mStatus.setText(world.worldStatus.description);
    }

    @Override
    public int getItemCount() {
        return mWorlds.size();
    }

    // region ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView mName;
        protected TextView mCounty;
        protected TextView mStatus;
        protected TextView mId;

        public ViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.name);
            mCounty = (TextView) v.findViewById(R.id.country);
            mStatus = (TextView) v.findViewById(R.id.status);
            mId = (TextView) v.findViewById(R.id.id);

        }

    }
}
