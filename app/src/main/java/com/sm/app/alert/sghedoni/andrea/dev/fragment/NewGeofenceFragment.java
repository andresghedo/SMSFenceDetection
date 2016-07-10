package com.sm.app.alert.sghedoni.andrea.dev.fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sm.app.alert.sghedoni.andrea.dev.Constant;
import com.sm.app.alert.sghedoni.andrea.dev.Controller;
import com.sm.app.alert.sghedoni.andrea.dev.Fence;
import com.sm.app.alert.sghedoni.andrea.dev.R;
import com.sm.app.alert.sghedoni.andrea.dev.activity.MainActivity;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewGeofenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewGeofenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewGeofenceFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String TAG = "[DebApp]NewGeofenceFragment";

    private View view;
    private Button addGeogence;
    private EditText nameFence;
    private EditText addressFence;
    private EditText cityFence;
    private EditText provinceFence;
    private Spinner spinnerRange;
    private Spinner spinnerEvent;
    private boolean update;
    private int idFenceToUpdate;
    private int positionFenceToUpdateinController;

    public NewGeofenceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_insert_geofence.
     */
    // TODO: Rename and change types and number of parameters
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
        Log.d(TAG, "onCreate");
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(Constant.TITLE_VIEW_ADD_NEW_FENCE );
        if (getArguments() != null) {
            this.update = true;
            this.idFenceToUpdate = getArguments().getInt(Constant.BUNDLE_FENCE_TO_UPDATE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_insert_geofence, container, false);

        addGeogence = (Button) view.findViewById(R.id.buttonSaveFence);
        spinnerRange = (Spinner) view.findViewById(R.id.spinnerRange);
        spinnerEvent = (Spinner) view.findViewById(R.id.spinnerEvent);
        nameFence = (EditText) view.findViewById(R.id.nameFence);
        addressFence = (EditText) view.findViewById(R.id.addressFence);
        cityFence = (EditText) view.findViewById(R.id.cityFence);
        provinceFence = (EditText) view.findViewById(R.id.provinceFence);

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
            //number
            //text sms
            int index = (int)Constant.SPINNER_RANGE_POSITIONS.get(fence.getRange());
            this.spinnerRange.setSelection(index);
            //event
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
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

                this.saveChangesInSQLiteDB(name, address, city, province, range);
                Controller.getLogFenceOnSQLiteDB();
                break;
        }
    }

    public void saveChangesInSQLiteDB(String name, String address, String city, String province, String range) {

        Double[] latLng = this.getLatLngByAddress(address, city, province);
        if (latLng == null)
            return;
        Double lat = latLng[0];
        Double lng = latLng[1];
        if(update)
            this.updateFenceInSqliteDB(name, address, city, province, lat, lng, range);
        else
            this.saveFenceInSqliteDB(name, address, city, province, lat, lng, range);

        // redirect to new itemfragment
        this.fragmentTransaction();
    }

    public void saveFenceInSqliteDB(String name, String address, String city, String province, Double lat, Double lng, String range) {
        boolean active = true; // default whan you add a geofence is active
        boolean match = false; // default whan you add a geofence is not in fence

        int id = Controller.insertFenceOnSQLiteDB(name, address, city, province, lat + "", lng + "", range, 1);
        Fence f = new Fence(id, name, address, city, province, lat, lng, Float.parseFloat(range), active, match);
        Controller.fences.add(f);
        Toast.makeText(this.getContext(), "Added the fence: " + f.getName() + "!", Toast.LENGTH_LONG).show();
    }

    public void updateFenceInSqliteDB(String name, String address, String city, String province, Double lat, Double lng, String range) {
        Fence fence = Controller.fences.get(positionFenceToUpdateinController);

        fence.setName(name);
        fence.setAddress(address);
        fence.setCity(city);
        fence.setProvince(province);
        fence.setRange(Float.parseFloat(range));
        fence.setLat(lat);
        fence.setLng(lng);

        Controller.updateAllAttributeOnSQLiteDB(fence.getId(), name, address, city, province, lat + "", lng + "", range);

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
                Toast.makeText(this.getContext(), "No address found!", Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getContext(), "No address found!", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public void fragmentTransaction() {
        // redirect to new itemfragment
        ItemFragment itemFragment = new ItemFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, itemFragment);
        ft.commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
