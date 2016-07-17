package com.sm.app.alert.sghedoni.andrea.dev.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sm.app.alert.sghedoni.andrea.dev.utils.Constant;
import com.sm.app.alert.sghedoni.andrea.dev.utils.Controller;
import com.sm.app.alert.sghedoni.andrea.dev.R;

/**
 *  Fragment that show user fence list.
 *  @author Andrea Sghedoni
 */
public class FenceListFragment extends Fragment {

    public FenceListFragment() {
    }

    public static FenceListFragment newInstance(int columnCount) {
        FenceListFragment fragment = new FenceListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the FenceRecyclerViewAdapter as list adapter
        if (view instanceof FrameLayout) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            recyclerView.setAdapter(new FenceRecyclerViewAdapter(Controller.fences, getActivity()));
            registerForContextMenu(recyclerView);
        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, Constant.SNACKBAR_TEXT_NEW_FENCE, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                NewGeofenceFragment newGeoFenceFragment = new NewGeofenceFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, newGeoFenceFragment);
                ft.commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
