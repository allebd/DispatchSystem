package com.allebd.dispatchsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allebd.dispatchsystem.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.allebd.dispatchsystem.R.id.reg_bloodgroup;
import static com.allebd.dispatchsystem.R.id.reg_dob;
import static com.allebd.dispatchsystem.R.id.reg_gender;
import static com.allebd.dispatchsystem.R.id.reg_password;
import static com.allebd.dispatchsystem.R.id.reg_phone;
import static com.allebd.dispatchsystem.R.id.reg_user;

//import android.databinding.DataBindingUtil;
//import com.allebd.dispatchsystem.databinding.ActivityRegisterBinding;

/**
 * Created by CSISPC on 12/03/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    //private ActivityRegisterBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference  mDatabase;
    //private String etreg_use, etreg_pas, etreg_bloodGrou, etreg_do, etreg_phon, etreg_gende, id;
    private EditText etreg_user;
    private EditText etreg_pass;
    private EditText etreg_phone;
    private EditText etreg_gender;
    private EditText etreg_dob;
    private EditText etreg_bloodGroup;
    private TextView tvUserLogin;
    private Button btnRegister;
    private ProgressBar mprogress;
    private FirebaseAuth.AuthStateListener authListener;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etreg_user = (EditText) findViewById(reg_user);
        etreg_pass = (EditText) findViewById(reg_password);
        etreg_phone = (EditText) findViewById(reg_phone);
        etreg_gender = (EditText) findViewById(reg_gender);
        etreg_dob = (EditText) findViewById(reg_dob);
        etreg_bloodGroup = (EditText) findViewById(reg_bloodgroup);
        tvUserLogin = (TextView) findViewById(R.id.tvUserLogin);
        btnRegister = (Button) findViewById(R.id.btn_register);
        mprogress = (ProgressBar) findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(this);
        tvUserLogin.setOnClickListener(this);
    }

    private void switchActivity(Class classFile, boolean addExtras) {
        Intent intent = new Intent(this, classFile);
        if (addExtras) {
            intent.putExtra("username", reg_user);
            intent.putExtra("password", reg_password);
            intent.putExtra("phone", reg_phone);
            intent.putExtra("gender", reg_gender);
            intent.putExtra("dob", reg_dob);
            intent.putExtra("bloodGroup", reg_bloodgroup);
            //intent.putExtra("id", id);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvUserLogin:
                switchActivity(LoginActivity.class, false);
                break;
            case R.id.btn_register:
                signup();
                break;
        }
    }

    private void signup() {
        String etreguser = etreg_user.getText().toString().trim();
        String etregpass = etreg_pass.getText().toString().trim();
        String etregdob = etreg_dob.getText().toString().trim();
        String etregbg = etreg_bloodGroup.getText().toString().trim();
        String etregphone = etreg_phone.getText().toString().trim();
        String etreggender = etreg_gender.getText().toString().trim();

        if (!validateEmail(etreguser) || !validatePassword(etregpass) || validateDob(etregdob)
                || validateBg(etregbg) || validatePhone(etregphone) || validateGender(etreggender)) return;
        mprogress.setVisibility(View.VISIBLE);
        btnRegister.setEnabled(false);
//        this.etreg_use = etreguser;
//        this.etreg_pas = etregpass;
//        this.etreg_do = etregdob;
//        this.etreg_bloodGrou = etregbg;
//        this.etreg_phon = etregphone;
//        this.etreg_gende = etreggender;
        firebaseAuth.createUserWithEmailAndPassword(etreguser, etregpass).addOnCompleteListener(this, this);
    }

    private boolean validateDob(String dob) {
        boolean state = true;
        if (TextUtils.isEmpty(dob)) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Enter Date of Birth!", Snackbar.LENGTH_SHORT).show();
            etreg_dob.setError("Enter Date of Birth!");
            state = false;

        } else etreg_dob.setError(null);
        return state;
    }

    private boolean validateBg(String bg) {
        boolean state = true;
        if (TextUtils.isEmpty(bg)) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Enter Blood Group!", Snackbar.LENGTH_SHORT).show();
            etreg_bloodGroup.setError("Enter Blood Group!");
            state = false;

        } else etreg_bloodGroup.setError(null);
        return state;
    }

    private boolean validatePhone(String phone) {
        boolean state = true;
        if (TextUtils.isEmpty(phone)) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Enter Phone Number!", Snackbar.LENGTH_SHORT).show();
            etreg_phone.setError("Enter Phone Number!");
            state = false;

        } else etreg_phone.setError(null);
        return state;
    }

    private boolean validateGender(String gender) {
        boolean state = true;
        if (TextUtils.isEmpty(gender)) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Enter Gender!", Snackbar.LENGTH_SHORT).show();
            etreg_gender.setError("Enter Gender!");
            state = false;

        } else etreg_gender.setError(null);
        return state;
    }


    private boolean validateEmail(String email) {
        boolean state = true;
        if (TextUtils.isEmpty(email)) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Enter email address!", Snackbar.LENGTH_SHORT).show();
            etreg_user.setError("Enter email address!");
            state = false;

        } else etreg_user.setError(null);

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Enter a valid email address!", Snackbar.LENGTH_SHORT).show();
            etreg_user.setError("Enter a valid email address!");
            state = false;
        } else etreg_user.setError(null);
        return state;
    }

    private boolean validatePassword(String password) {
        boolean state = true;
        if (TextUtils.isEmpty(password)) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Enter password", Snackbar.LENGTH_SHORT).show();
            etreg_pass.setError("Enter password");
            state = false;
        } else etreg_pass.setError(null);

        if (password.length() < 5) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Password too short, enter minimum 5 characters!", Snackbar.LENGTH_SHORT).show();
            etreg_pass.setError("Password too short");
            state = false;
        } else etreg_pass.setError(null);
        return state;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        mprogress.setVisibility(View.GONE);
        if (!task.isSuccessful()) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Sign Up failed, try again", Snackbar.LENGTH_SHORT).show();
            btnRegister.setEnabled(true);
        } else {
            //THIS IS FOR STORING AUTHENTICATED USER'S DATA
            authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    String etreguser = etreg_user.getText().toString().trim();
                    String etregpass = etreg_pass.getText().toString().trim();
                    String etregdob = etreg_dob.getText().toString().trim();
                    String etregbg = etreg_bloodGroup.getText().toString().trim();
                    String etregphone = etreg_phone.getText().toString().trim();
                    String etreggender = etreg_gender.getText().toString().trim();

                    Users users = new Users(etreguser, etregpass, etregphone, etregdob, etreggender, etregbg);

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        mDatabase.child("users").child(user.getUid()).setValue(users);
                    }
                }
            };

            switchActivity(LoginActivity.class, true);
        }

    }

}
