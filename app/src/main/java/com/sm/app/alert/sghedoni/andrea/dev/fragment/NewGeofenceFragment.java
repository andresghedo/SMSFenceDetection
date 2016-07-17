package com.sm.app.alert.sghedoni.andrea.dev.fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sm.app.alert.sghedoni.andrea.dev.utils.Constant;
import com.sm.app.alert.sghedoni.andrea.dev.utils.Controller;
import com.sm.app.alert.sghedoni.andrea.dev.entity.Fence;
import com.sm.app.alert.sghedoni.andrea.dev.R;
import com.sm.app.alert.sghedoni.andrea.dev.activity.MainActivity;

import java.util.List;
import java.util.Locale;

public class NewGeofenceFragment extends Fragment implements View.OnClickListener {

    private String TAG = "[DebApp]NewGeofenceFragment";

    private View view;
    private Button addGeogence;
    private EditText nameFence;
    private EditText addressFence;
    private EditText cityFence;
    private EditText provinceFence;
    private EditText textSMSFence;
    private EditText numberFence;
    private Spinner spinnerRange;
    private Spinner spinnerEvent;
    private boolean update;
    private int idFenceToUpdate;
    private int positionFenceToUpdateinController;

    public NewGeofenceFragment() {
    }

    public static NewGeofenceFragment newInstance(String param1, String param2) {
        NewGeofenceFragment fragment = new NewGeofenceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.update = false;
        this.idFenceToUpdate = -1;
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(Constant.TITLE_VIEW_ADD_NEW_FENCE );
        if (getArguments() != null) {
            this.update = true;
            this.idFenceToUpdate = getArguments().getInt(Constant.BUNDLE_FENCE_TO_UPDATE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_insert_geofence, container, false);

        addGeogence = (Button) view.findViewById(R.id.buttonSaveFence);
        spinnerRange = (Spinner) view.findViewById(R.id.spinnerRange);
        spinnerEvent = (Spinner) view.findViewById(R.id.spinnerEvent);
        nameFence = (EditText) view.findViewById(R.id.nameFence);
        addressFence = (EditText) view.findViewById(R.id.addressFence);
        cityFence = (EditText) view.findViewById(R.id.cityFence);
        provinceFence = (EditText) view.findViewById(R.id.provinceFence);
        numberFence = (EditText) view.findViewById(R.id.numberFence);
        textSMSFence = (EditText) view.findViewById(R.id.textSMSFence);

        addGeogence.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapterRange = ArrayAdapter.createFromResource(getActivity(),
                R.array.range_geofence, android.R.layout.simple_spinner_item);
        adapterRange.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        ArrayAdapter<CharSequence> adapterEvent = ArrayAdapter.createFromResource(getActivity(),
                R.array.event_geofence, android.R.layout.simple_spinner_item);
        adapterEvent.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        spinnerRange.setAdapter(adapterRange);
        spinnerEvent.setAdapter(adapterEvent);

        if ((this.update) && (this.idFenceToUpdate >= 0)) {
            this.setUpdateData();
        }

        return view;
    }

    public void setUpdateData() {
        positionFenceToUpdateinController = Controller.getPositionFenceInArrayById(this.idFenceToUpdate);
        if(positionFenceToUpdateinController >= 0) {
            Fence fence = Controller.fences.get(positionFenceToUpdateinController);
            ((MainActivity)getActivity()).getSupportActionBar().setTitle(Constant.TITLE_VIEW_UPDATE_FENCE + ": " + fence.getName());
            this.nameFence.setText(fence.getName());
            this.addressFence.setText(fence.getAddress());
            this.cityFence.setText(fence.getCity());
            this.provinceFence.setText(fence.getProvince());
            this.numberFence.setText(fence.getNumber());
            this.textSMSFence.setText(fence.getTextSMS());
            int index = (int)Constant.SPINNER_RANGE_POSITIONS.get(fence.getRange());
            this.spinnerRange.setSelection(index);
            this.spinnerEvent.setSelection(fence.getEvent());
        }
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSaveFence:
                String name     = nameFence.getText().toString();
                String address  = addressFence.getText().toString();
                String city     = cityFence.getText().toString();
                String province = provinceFence.getText().toString();
                String range    = spinnerRange.getSelectedItem().toString();
                String number   = numberFence.getText().toString();
                String textSMS  = textSMSFence.getText().toString();
                int event       = spinnerEvent.getSelectedItemPosition();

                this.saveChangesInSQLiteDB(name, address, city, province, range, number, textSMS, event);
                Controller.getLogFenceOnSQLiteDB();
                break;
        }
    }

    public void saveChangesInSQLiteDB(String name, String address, String city, String province, String range, String number, String textSMS, int event) {

        Double[] latLng = this.getLatLngByAddress(address, city, province);
        if (latLng == null)
            return;
        Double lat = latLng[0];
        Double lng = latLng[1];
        if(update)
            this.updateFenceInSqliteDB(name, address, city, province, lat, lng, range, number, textSMS, event);
        else
            this.saveFenceInSqliteDB(name, address, city, province, lat, lng, range, number, textSMS, event);

        // redirect to new itemfragment
        this.fragmentTransaction();
    }

    public void saveFenceInSqliteDB(String name, String address, String city, String province, Double lat, Double lng, String range, String number, String textSMS, int event) {
        boolean active = true; // default whan you add a geofence is active
        boolean match = false; // default whan you add a geofence is not in fence

        int id = Controller.insertFenceOnSQLiteDB(name, address, city, province, lat + "", lng + "", range, 1, number, textSMS, event);
        Fence f = new Fence(id, name, address, city, province, lat, lng, Float.parseFloat(range), active, match, number, textSMS, event);
        Controller.fences.add(f);
        Toast.makeText(this.getContext(), "Added the fence: " + f.getName() + "!", Toast.LENGTH_LONG).show();
    }

    public void updateFenceInSqliteDB(String name, String address, String city, String province, Double lat, Double lng, String range, String number, String textSMS, int event) {
        Fence fence = Controller.fences.get(positionFenceToUpdateinController);

        fence.setName(name);
        fence.setAddress(address);
        fence.setCity(city);
        fence.setProvince(province);
        fence.setRange(Float.parseFloat(range));
        fence.setLat(lat);
        fence.setLng(lng);
        fence.setNumber(number);
        fence.setTextSMS(textSMS);
        fence.setEvent(event);

        Controller.updateAllAttributeOnSQLiteDB(fence.getId(), name, address, city, province, lat + "", lng + "", range, number, textSMS, event);

        Toast.makeText(this.getContext(), "Updated the fence: " + Controller.fences.get(positionFenceToUpdateinController).getName() + "!", Toast.LENGTH_LONG).show();
    }

    public Double[] getLatLngByAddress(String address, String city, String province) {
        Geocoder g = new Geocoder(this.getContext(), Locale.getDefault());
        Double[] latLng = new Double[2];
        try {
            String locationName = address + ", " + city + ", " + province ;
            List<Address> addressesName = g.getFromLocationName(locationName, 10);
            if (addressesName.size() > 0) {
                latLng[0] = addressesName.get(0).getLatitude();
                latLng[1] = addressesName.get(0).getLongitude();
                return latLng;
            } else {
                Toast.makeText(this.getContext(), Constant.TOAST_TEXT_NO_ADDRESS_FOUND, Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getContext(), Constant.TOAST_TEXT_NO_ADDRESS_FOUND, Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void fragmentTransaction() {
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(Constant.TITLE_VIEW_LIST_GEOFENCES );
        FenceListFragment fenceListFragment = new FenceListFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fenceListFragment);
        ft.commit();
    }
}
