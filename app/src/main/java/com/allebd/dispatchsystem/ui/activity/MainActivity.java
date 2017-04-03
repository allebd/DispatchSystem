package com.allebd.dispatchsystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.allebd.dispatchsystem.R;
import com.allebd.dispatchsystem.ui.fragment.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FirebaseAuth.AuthStateListener {

    private FirebaseAuth auth;
    private String uid;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            switchFragment(getProfileFragment(uid));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .commit();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            switchFragment(getProfileFragment(uid));
        } else {
            switchActivityClean(LoginActivity.class);
        }
    }

    private Fragment getProfileFragment(String uid){
        return ProfileFragment.newInstance(uid);
    }
    public void switchActivityClean(Class classfile) {
        Intent intent = new Intent(this, classfile);
        startActivity(intent);
    }
}
