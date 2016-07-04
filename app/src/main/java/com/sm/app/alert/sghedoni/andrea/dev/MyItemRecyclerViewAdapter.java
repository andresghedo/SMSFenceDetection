package com.sm.app.alert.sghedoni.andrea.dev;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import java.util.ArrayList;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Fence> mValues = null;

    public MyItemRecyclerViewAdapter(Context c, ArrayList<Fence> items) {

    }

    public MyItemRecyclerViewAdapter(ArrayList<Fence> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId() + "");
        holder.mContentView.setText(mValues.get(position).getName());
        holder.mSwitch.setChecked(mValues.get(position).isActive());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /*
    *******************************************************************************************
    *                         ViewHolder                                                      *
    *******************************************************************************************
    * */


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
    MenuItem.OnMenuItemClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Switch mSwitch = null;
        public Fence mItem;
        private String TAG = "[DebApp] ViewHolder";

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mSwitch = (Switch) view.findViewById(R.id.switchFence);
            mSwitch.setOnCheckedChangeListener(this);
            mView.setOnCreateContextMenuListener(this);
            mView.setOnLongClickListener(this);
            Log.d(TAG, "Constructor");
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Log.d(TAG, "onCreateContextMenu");
            menu.setHeaderTitle("Choose an action");
            MenuItem myActionItem = menu.add(0, 0, 0, "Delete");
            myActionItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Log.d(TAG, "onMenuItemClick, item: " + item.toString());
            switch (item.getItemId()) {
                case 0:
                    removeFence(this.mItem);
                    break;
                default:
                    Log.d(TAG, "You pick item id n°: " + item.getItemId());
            }

            return false;
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "onLongClick event, view: " + v.toString());
            Log.d(TAG, "Current Fence clicked: " + this.mItem.getName());
            return false;
        }

        public void removeFence(Fence toRemove) {
            int position = mValues.indexOf(toRemove);
            Controller.fences.remove(toRemove);
            Controller.getLogFenceEntities();
            Log.d(TAG, "Fence to remove in position : " + position);
            Controller.removeFenceFromDB(toRemove.getId());
            Controller.getLogFenceOnSQLiteDB();

            mValues.remove(toRemove);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mValues.size());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.d(TAG, "Switch changed! status of geofence " + this.mItem.getName() + "  : " + isChecked);
            int index = Controller.fences.indexOf(this.mItem);
            Controller.fences.get(index).setActive(isChecked);
            Controller.updateFenceStatusOnSQLiteDB(this.mItem.getId(), isChecked);
        }
    }
}
