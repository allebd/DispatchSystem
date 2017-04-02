package com.allebd.dispatchsystem.data;

import com.allebd.dispatchsystem.data.model.Hospital;
import com.allebd.dispatchsystem.data.model.HospitalLocation;
import com.allebd.dispatchsystem.data.model.User;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public interface DataManager {

    interface Operations {
        void storeUserInfo(User user, String uid);

        void queryForUserInfo(String userId);

        void queryForHospitalLocations(LatLng latLng);

        void queryForHospitalInfo(String hospitalId);

        void setHospitalListeners(HospitalListener listener);

        void setUserListener(UserListener listener);

        void sendRequestToHospital(RequestObject request, String hospitalId);
    }

    interface HospitalListener {
        void onHospitalsLocationLoaded(ArrayList<HospitalLocation> hospitalLocations);

        void onHospitalInfoLoaded(Hospital hospital);
    }

    interface UserListener {
        void onUserInfoLoaded(User user);
    }
}
