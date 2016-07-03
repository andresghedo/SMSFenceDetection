package com.sm.app.wifi.sghedoni.andrea.wifism;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    MenuItem.OnMenuItemClickListener, View.OnLongClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Fence mItem;
        private String TAG = "[DebApp] ViewHolder";

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
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
            MenuItem myActionItem = menu.add(0, 1, 1, "Delete");
            myActionItem.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Log.d(TAG, "onMenuItemClick, item: " + item.toString());
            return false;
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d(TAG, "onLongClick event, view: " + v.toString());
            Log.d(TAG, "Current Fence clicked: " + this.mItem.getName());
            return false;
        }
    }
}
