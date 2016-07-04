package com.sm.app.alert.sghedoni.andrea.dev;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewGeofenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewGeofenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewGeofenceFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String TAG = "[DebApp]NewGeofenceFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private Button addGeogence;
    private EditText nameFence;
    private EditText addressFence;
    private EditText cityFence;
    private EditText provinceFence;
    private Spinner spinnerRange;

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_insert_geofence, container, false);

        addGeogence = (Button) view.findViewById(R.id.buttonSaveFence);
        spinnerRange = (Spinner) view.findViewById(R.id.spinnerRange);
        nameFence = (EditText) view.findViewById(R.id.nameFence);
        addressFence = (EditText) view.findViewById(R.id.addressFence);
        cityFence = (EditText) view.findViewById(R.id.cityFence);
        provinceFence = (EditText) view.findViewById(R.id.provinceFence);

        addGeogence.setOnClickListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.range_geofence, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerRange.setAdapter(adapter);

        return view;
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
                String name = nameFence.getText().toString();
                String address = addressFence.getText().toString();
                String city = cityFence.getText().toString();
                String province = provinceFence.getText().toString();
                String range = spinnerRange.getSelectedItem().toString();

                this.saveFenceInSqliteDB(name, address, city, province, range);
                Controller.getLogFenceOnSQLiteDB();
                Log.d(TAG, "Saved Geofence!");
                break;
        }
    }

    public void saveFenceInSqliteDB(String name, String address, String city, String province, String range) {
        Geocoder g = new Geocoder(this.getContext(), Locale.getDefault());
        Double lat = null;
        Double lng = null;
        boolean active = true; // default whan you add a geofence is active
        try {
            String locationName = address + ", " + city + ", " + province ;
            List<Address> addressesName = g.getFromLocationName(locationName, 10);
            if (addressesName.size() > 0) {
                lat = addressesName.get(0).getLatitude();
                lng = addressesName.get(0).getLongitude();

                for (int i=0; i<addressesName.size(); i++){
                        Log.d(TAG, "INDIRIZZO TROVATO DAL NOME (" + i + ") :" + addressesName.get(i).toString());
                    }
            } else {
                Toast.makeText(this.getContext(), "No address found!", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this.getContext(), "No address found!", Toast.LENGTH_LONG).show();
            return;
        }
        int id = Controller.insertFenceOnSQLiteDB(name, address, city, province, lat + "", lng + "", range, 1);
        Fence f = new Fence(id, name, address, city, province, lat, lng, Double.parseDouble(range), true);
        Controller.fences.add(f);
        Toast.makeText(this.getContext(), "Added the fence: " + f.getName() + "!", Toast.LENGTH_LONG).show();

        // redirect to new itemfragment
        ItemFragment itemFragment = new ItemFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, itemFragment);
        ft.commit();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
