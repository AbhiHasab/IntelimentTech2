package com.education.abhihasabe.intelimenttechnologies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.education.abhihasabe.intelimenttechnologies.InternetConnection;
import com.education.abhihasabe.intelimenttechnologies.R;
import com.education.abhihasabe.intelimenttechnologies.VolleySingleton;
import com.education.abhihasabe.intelimenttechnologies.broadcastReceiver.ConnectivityReceiver;
import com.education.abhihasabe.intelimenttechnologies.dataObjects.Vehicle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Abhi on 28-03-2018.
 */

public class Tab1 extends Fragment implements OnMapReadyCallback, ConnectivityReceiver.ConnectivityReceiverListener {

    public Tab1() {
    }

    private RequestQueue mQueue;
    private ArrayList<Vehicle> listSuperHeroes;
    private ArrayList<String> worldlist;
    private Spinner mySpinner;
    private Button track;
    private CardView locationCard;
    private Double lat;
    private Double lng;
    private String userName = null;
    private boolean click = true;
    private boolean fristClick = true;
    private TextView txtCar;
    private TextView txtTrain;
    private MapView mapView;
    private String getCar;
    private String getTrain;
    private GoogleMap map;
    private int spinnerPosition;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1, container, false);
        mySpinner = v.findViewById(R.id.my_spinner);
        locationCard = v.findViewById(R.id.locationCard);
        track = v.findViewById(R.id.track);
        txtCar = v.findViewById(R.id.country);
        txtTrain = v.findViewById(R.id.population);
        mapView = v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnection.checkConnection(getActivity())) {
                    try {
                        if (fristClick) {

                            // Loading map when we click on frist time

                            track.setText("Remove My Location");
                            locationCard.setVisibility(View.VISIBLE);
                            initilizeMap();
                            click = false;
                            fristClick = false;
                        } else if (!fristClick) {

                            // Remove map when we click on Second time

                            locationCard.setVisibility(View.GONE);
                            fristClick = true;
                            track.setText("Track My Location");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    InternetConnection.showNetDisabledAlertToUser(getActivity());
                }
            }
        });
        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        listSuperHeroes = new ArrayList<Vehicle>();
        worldlist = new ArrayList<String>();
        return v;
    }
        // Call API
    private void jsonParse() {

        String url = "http://express-it.optusnet.com.au/sample.json";

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonarray) {
                        for (int i = 0; i < jsonarray.length(); i++) {
                            try {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                Vehicle countory = new Vehicle();

                                //Check object have or not

                                if (jsonobject.has("name")) {
                                    countory.setName(jsonobject.getString("name"));
                                    worldlist.add(jsonobject.getString("name"));
                                }
                                JSONObject fromcentral = jsonobject.getJSONObject("fromcentral");
                                if (fromcentral.has("car")) {
                                    countory.setCar(fromcentral.getString("car"));
                                }
                                if (fromcentral.has("train")) {
                                    countory.setTrain(fromcentral.getString("train"));
                                } else {
                                    countory.setTrain("Data Not Available");
                                }
                                JSONObject location = jsonobject.getJSONObject("location");
                                if (location.has("latitude")) {
                                    countory.setLatitude(location.getString("latitude"));
                                }
                                if (location.has("longitude")) {
                                    countory.setLongitude(location.getString("longitude"));
                                }
                                listSuperHeroes.add(countory);

                                mySpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_spinner_dropdown_item,
                                        worldlist));

                                //Call Spinner setOnClick Listener

                                mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> arg0,
                                                               View arg1, int position, long arg3) {
                                        // TODO Auto-generated method stub

                                        try {
                                            spinnerPosition = position;
                                            getCar = listSuperHeroes.get(position).getCar();
                                            getTrain = listSuperHeroes.get(position).getTrain();
                                            txtCar.setText(listSuperHeroes.get(position).getCar());
                                            txtTrain.setText(listSuperHeroes.get(position).getTrain());
                                            userName = listSuperHeroes.get(position).getName();
                                            lat = Double.parseDouble(listSuperHeroes.get(position).getLatitude());
                                            lng = Double.parseDouble(listSuperHeroes.get(position).getLongitude());

                                            if (!click) {
                                                initilizeMap();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                        // TODO Auto-generated method stub
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        mQueue.add(getRequest);
    }

    //Map Initilization

    private void initilizeMap() {
        mapView.getMapAsync(this);
       /* MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
    }

    // Set Map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            map = googleMap;
            map.getUiSettings().setMyLocationButtonEnabled(false);
            LatLng latLng = new LatLng(lat, lng);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(lat, lng)).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            googleMap.addMarker(new MarkerOptions().position(latLng).title(userName));
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        if (InternetConnection.checkConnection(getActivity())) {
            //Calling API
            jsonParse();
            mapView.onResume();
        } else {
            InternetConnection.showNetDisabledAlertToUser(getActivity());
        }
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // Save Instance

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            Log.e("spinnerPosition1", String.valueOf(spinnerPosition));
            outState.putInt("yourSpinner", spinnerPosition);
            outState.putString("lat", String.valueOf(lat));
            outState.putString("lng", String.valueOf(lng));
            outState.putString("getCar", getCar);
            outState.putString("getTrain", getTrain);
        }
    }

    //Restore Instance

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            Log.e("spinnerPosition2", String.valueOf(savedInstanceState.getInt("yourSpinner")));
            mySpinner.setSelection(savedInstanceState.getInt("spinnerData"));
            String stringName = savedInstanceState.getString("yourSpinner");
            String lat = savedInstanceState.getString("lat");
            String lng = savedInstanceState.getString("lng");
            String getCar = savedInstanceState.getString("getCar");
            String getTrain = savedInstanceState.getString("getTrain");
            txtCar.setText(getCar);
            txtTrain.setText(getTrain);
        }
    }

    //Checking Network Status

    @Override
    public void onNetworkConnectionChanged() {
        InternetConnection.checkConnection(getActivity());
    }
}