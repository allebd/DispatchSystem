package com.allebd.dispatchsystem.data;

import com.allebd.dispatchsystem.data.model.Hospital;
import com.allebd.dispatchsystem.data.model.HospitalLocation;
import com.allebd.dispatchsystem.data.model.RequestObject;
import com.allebd.dispatchsystem.data.model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class AppDataManager implements DataManager.Operations {


    private DatabaseReference reference;
    private DataManager.UserListener userListener;
    private DataManager.HospitalListener hospitalListener;

    public AppDataManager() {
        reference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void storeUserInfo(User user, String uid) {
        reference.child("user").child(uid).setValue(user);
    }

    @Override
    public void queryForUserInfo(String userId) {
        reference.child("user").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User();
                user.setName((String) dataSnapshot.child("name").getValue());
                user.setDob((String) dataSnapshot.child("dob").getValue());
                user.setTelephone((String) dataSnapshot.child("telephone").getValue());
                user.setGender((String) dataSnapshot.child("gender").getValue());
                user.setBloodGroup((String) dataSnapshot.child("bloodGroup").getValue());
                userListener.onUserInfoLoaded(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void queryForRequests(String userId) {
        reference.child("users").child("requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<RequestObject> requests = new ArrayList<RequestObject>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RequestObject request = snapshot.getValue(RequestObject.class);
                    requests.add(request);
                }
                Collections.reverse(requests);
                userListener.onRequestsLoaded(requests);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void queryForHospitalLocations(LatLng latLng) {
        reference.child("hospitals").child("location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HospitalLocation> locations = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HospitalLocation hospitalLocation = new HospitalLocation();
                    hospitalLocation.setUid(snapshot.getKey());
                    hospitalLocation.setLatitude((Double) snapshot.child("latitude").getValue());
                    hospitalLocation.setLongitude((Double) snapshot.child("longitude").getValue());
                    locations.add(hospitalLocation);
                }
                hospitalListener.onHospitalsLocationLoaded(locations);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void queryForHospitalInfo(final String hospitalId) {
        reference.child("hospitals").child("details").child(hospitalId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Hospital hospital = new Hospital();
                hospital.setName((String) dataSnapshot.child("name").getValue());
                hospital.setUid(hospitalId);
                hospital.setAddress((String) dataSnapshot.child("address").getValue());
                hospital.setNumber((String) dataSnapshot.child("number").getValue());
                hospital.setAmbulanceNumber((Long) dataSnapshot.child("ambulanceNumber").getValue());
                hospital.setToken((String) dataSnapshot.child("token").getValue());
                hospitalListener.onHospitalInfoLoaded(hospital);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setHospitalListeners(DataManager.HospitalListener listener) {
        hospitalListener = listener;
    }

    @Override
    public void setUserListener(DataManager.UserListener listener) {
        userListener = listener;
    }

    @Override
    public void sendRequestToHospital(RequestObject request) {
        String key = reference.child("hospitals").child("requests").child(request.getHospitalId()).push().getKey();
        request.setUid(key);
        reference.child("hospitals").child("requests").child(request.getUserId()).child(key).setValue(request);
        reference.child("users").child("requests").child(request.getUserId()).child(key).setValue(request);
    }
}