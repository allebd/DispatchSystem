package com.allebd.dispatchsystem.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allebd.dispatchsystem.R;
import com.allebd.dispatchsystem.data.DataManager;
import com.allebd.dispatchsystem.data.model.User;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements DataManager.UserListener {
    private static final String ARG_PARAM1 = "param1";

    public DataManager.Operations dataManager;
    // TODO: Rename and change types of parameters
    private String userId;
    private FirebaseAuth auth;
    private ImageView profileImage;
    private TextView profileName;
    private TextView profilePhone;
    private TextView profileGender;
    private TextView profileDOB;
    private TextView profileBloodGroup;


    public ProfileFragment() {
    }


    public static ProfileFragment newInstance(String userId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = (ImageView) view.findViewById(R.id.profileImage);
        profileName = (TextView) view.findViewById(R.id.profileName);
        profilePhone = (TextView) view.findViewById(R.id.profilePhone);
        profileGender = (TextView) view.findViewById(R.id.profileGender);
        profileDOB = (TextView) view.findViewById(R.id.profileDOB);
        profileBloodGroup = (TextView) view.findViewById(R.id.profileBloodGroup);
        initUI();
        dataManager.queryForUserInfo(userId);
        return view;
    }

    private void initUI() {
        User user = new User();
        user.initEmptyUser();
        updateUI(user);
    }

    @Override
    public void onUserInfoLoaded(User user) {
        updateUI(user);
    }

    private void updateUI(User user) {
        profileName.setText(user.getName());
        profilePhone.setText(user.getTelephone());
        profileGender.setText(user.getGender());
        profileDOB.setText(user.getDob());
        profileBloodGroup.setText(user.getBloodGroup());
    }
}
