package com.allebd.dispatchsystem.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.allebd.dispatchsystem.R;
import com.allebd.dispatchsystem.data.DataManager;
import com.allebd.dispatchsystem.data.model.Hospital;
import com.allebd.dispatchsystem.data.model.HospitalLocation;
import com.allebd.dispatchsystem.data.model.RequestObject;
import com.allebd.dispatchsystem.location.LocationHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class HospitalFinderActivity extends FragmentActivity implements OnMapReadyCallback, LocationHelper.LocationHelperListener, DataManager.HospitalListener, GoogleMap.OnMarkerClickListener {

    @Inject
    public LocationHelper locationHelper;
    @Inject
    public DataManager.Operations dataManager;
    private GoogleMap map;
    private String userId;
    private LatLng myLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_finder);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        initLocationServices();

        map.setOnMarkerClickListener(this);

    }

    private void initLocationServices() {
        locationHelper.setListener(this);
        locationHelper.createGoogleClient();
        locationHelper.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationHelper.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLastLocationGotten(LatLng latLng) {
        myLocation = latLng;
        dataManager.queryForHospitalLocations(latLng);

    }

    @Override
    public void onLocationChanged(LatLng latLng) {
        myLocation = latLng;
        dataManager.queryForHospitalLocations(latLng);
    }

    @Override
    public void onHospitalsLocationLoaded(ArrayList<HospitalLocation> hospitalLocations) {
        for (HospitalLocation location : hospitalLocations) {
            addMarker(location);
        }
    }


    private void addMarker(HospitalLocation location) {
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()));
        Marker marker = map.addMarker(options);
        marker.setTag(location.getUid());
    }

    @Override
    public void onHospitalInfoLoaded(Hospital hospital) {
showDialog(hospital);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String uid = (String) marker.getTag();
        dataManager.queryForHospitalInfo(uid);
        return true;
    }

    private void showDialog(final Hospital hospital) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_hospital_details);
        dialog.setTitle("Hospital Details");

        TextView hospitalName = (TextView) dialog.findViewById(R.id.hospitalName);
        TextView hospitalAddress = (TextView) dialog.findViewById(R.id.hospitalAddress);
        TextView hospitalNumber = (TextView) dialog.findViewById(R.id.hospitalNumber);
        ImageButton close = (ImageButton) dialog.findViewById(R.id.close);
        Button getHelp = (Button) dialog.findViewById(R.id.getHelp);

        hospitalName.setText(hospital.getName());
        hospitalAddress.setText(hospital.getAddress());
        hospitalNumber.setText(hospital.getNumber());
        if (hospital.getAmbulanceNumber() == 0) getHelp.setEnabled(false);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        getHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestObject requestObject = createRequestObject(hospital);
                sendRequest(requestObject);
            }
        });
    }

    private RequestObject createRequestObject(Hospital hospital) {
        RequestObject request = new RequestObject();
        request.initEmptyRequest();
        request.setHospitalId(hospital.getUid());
        request.setUserId(userId);
        request.setLatitude(myLocation.latitude);
        request.setLongitude(myLocation.longitude);
        Date date = Calendar.getInstance().getTime();
        request.setDate(date);
        return request;
    }

    private void sendRequest(RequestObject requestObject) {
        dataManager.sendRequestToHospital(requestObject);
    }
}
