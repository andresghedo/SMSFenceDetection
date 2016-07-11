package com.sm.app.alert.sghedoni.andrea.dev.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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


import com.sm.app.alert.sghedoni.andrea.dev.utils.Constant;
import com.sm.app.alert.sghedoni.andrea.dev.utils.Controller;
import com.sm.app.alert.sghedoni.andrea.dev.entity.Fence;
import com.sm.app.alert.sghedoni.andrea.dev.R;

import java.util.ArrayList;


public class FenceRecyclerViewAdapter extends RecyclerView.Adapter<FenceRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Fence> mValues = null;

    private FragmentActivity fa;

    public FenceRecyclerViewAdapter(ArrayList<Fence> items, FragmentActivity fa) {
        this.mValues = items;
        this.fa = fa;
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
        public final TextView mContentView;
        public Switch mSwitch = null;
        public Fence mItem;
        private String TAG = "[DebApp] ViewHolder";


        public ViewHolder(View view) {
            super(view);
            mView = view;
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
            MenuItem deleteActionItem = menu.add(0, 0, 0, "Delete");
            MenuItem updateActionItem = menu.add(0, 1, 1, "Update/Show");
            deleteActionItem.setOnMenuItemClickListener(this);
            updateActionItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Log.d(TAG, "onMenuItemClick, item: " + item.toString());
            switch (item.getItemId()) {
                case 0:
                    removeFence(this.mItem);
                    break;
                case 1:
                    Log.d(TAG, "Update geofence");
                    getFragmentUpdate(this.mItem);
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

        public void getFragmentUpdate(Fence fence) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.BUNDLE_FENCE_TO_UPDATE_ID, fence.getId());
            // set Fragmentclass Arguments
            NewGeofenceFragment fragment = new NewGeofenceFragment();
            fragment.setArguments(bundle);
            if (fragment != null) {
                FragmentTransaction ft = fa.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.d(TAG, "Switch changed! status of geofence " + this.mItem.getName() + "  : " + isChecked);
            int index = Controller.fences.indexOf(this.mItem);
            Fence fence = Controller.fences.get(index);
            fence.setActive(isChecked);
            Controller.updateFenceStatusOnSQLiteDB(fence.getId(), isChecked);
            if(!isChecked){
                Controller.updateFenceMatchOnSQLiteDB(fence.getId(), false);
                fence.setMatch(false);
            }
        }
    }
}
