package com.sm.app.alert.sghedoni.andrea.dev.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.sm.app.alert.sghedoni.andrea.dev.utils.Controller;
import com.sm.app.alert.sghedoni.andrea.dev.R;
import com.sm.app.alert.sghedoni.andrea.dev.service.BetterApproachService;
import com.sm.app.alert.sghedoni.andrea.dev.service.PollingStrategyService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    protected static final String TAG = "[DebApp]ServiceFragment";
    private Switch switchPolling5s = null;
    private Switch switchBetterApproach = null;
    private View view;

    public ServiceFragment() {
    }

    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_service, container, false);

        this.switchPolling5s = (Switch) view.findViewById(R.id.switchPollingStrategy);
        this.switchPolling5s.setOnCheckedChangeListener(this);
        this.switchBetterApproach = (Switch) view.findViewById(R.id.switchBetterApproachStrategy);
        this.switchBetterApproach.setOnCheckedChangeListener(this);
        if (Controller.isMyServiceRunning(PollingStrategyService.class, getContext())) {
            this.switchPolling5s.setChecked(true);
            this.switchBetterApproach.setClickable(false);
        }
        if (Controller.isMyServiceRunning(BetterApproachService.class, getContext())) {
            this.switchBetterApproach.setChecked(true);
            this.switchPolling5s.setClickable(false);
        }
        return this.view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch(buttonView.getId()){
            case R.id.switchPollingStrategy:
                Log.d(TAG, "Event on switch Polling 5 sec Strategy!");
                this.managePollingStrategyService(isChecked);
                break;
            case R.id.switchBetterApproachStrategy:
                Log.d(TAG, "Event on switch Better Approach Strategy!");
                this.manageBetterApproachStrategyService(isChecked);
                break;
        }
    }

    private void managePollingStrategyService(boolean status) {
        if (status) {
            getContext().startService(new Intent(getContext(), PollingStrategyService.class));
            this.switchBetterApproach.setClickable(false);
            Log.d(TAG, "Starting PollingService ....... ");
        } else {
            getContext().stopService(new Intent(getContext(), PollingStrategyService.class));
            Controller.setAllFencesMatchedFalse();
            this.switchBetterApproach.setClickable(true);
            Log.d(TAG, "Stopping PollingService ....... ");
        }
    }

    private void manageBetterApproachStrategyService(boolean status) {
        if (status) {
            getContext().startService(new Intent(getContext(), BetterApproachService.class));
            this.switchPolling5s.setClickable(false);
            Log.d(TAG, "Starting Better Approach Strategy Service ....... ");
        } else {
            getContext().stopService(new Intent(getContext(), BetterApproachService.class));
            Controller.setAllFencesMatchedFalse();
            this.switchPolling5s.setClickable(true);
            Log.d(TAG, "Stopping Better Approach StrategyS ervice ....... ");
        }
    }
}
