package com.allebd.dispatchsystem.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allebd.dispatchsystem.R;
import com.allebd.dispatchsystem.data.DataManager;
import com.allebd.dispatchsystem.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

//import android.databinding.DataBindingUtil;
//import com.allebd.dispatchsystem.databinding.ActivityRegisterBinding;

/**
 * Created by CSISPC on 12/03/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    @Inject
    public DataManager.Operations dataManager;
    Context c;
    //private ActivityRegisterBinding binding;
    private FirebaseAuth firebaseAuth;
    private EditText etreg_user;
    private EditText etreg_email;
    private EditText etreg_pass;
    private EditText etreg_phone;
    private EditText etreg_gender;
    private EditText etreg_dob;
    private EditText etreg_bloodGroup;
    private TextView tvUserLogin;
    private Button btnRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener authListener;
    private String etRegEmail, etRegPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etreg_user = (EditText) findViewById(R.id.reg_user);
        etreg_email = (EditText) findViewById(R.id.reg_email);
        etreg_pass = (EditText) findViewById(R.id.reg_password);
        etreg_phone = (EditText) findViewById(R.id.reg_phone);
        etreg_gender = (EditText) findViewById(R.id.reg_gender);
        etreg_dob = (EditText) findViewById(R.id.reg_dob);
        etreg_bloodGroup = (EditText) findViewById(R.id.reg_bloodgroup);
        tvUserLogin = (TextView) findViewById(R.id.tvUserLogin);
        btnRegister = (Button) findViewById(R.id.btn_register);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(this);
        tvUserLogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
    }

    private void switchActivity(Class classFile) {
        Intent intent = new Intent(this, classFile);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvUserLogin:
                switchActivity(LoginActivity.class);
                break;
            case R.id.btn_register:
                signup();
                break;
        }
    }

    private void signup() {
        String etRegUser = etreg_user.getText().toString().trim();
        String etRegEmail = etreg_email.getText().toString().trim();
        String etRegPass = etreg_pass.getText().toString().trim();
        String etRegDob = etreg_dob.getText().toString().trim();
        String etRegBg = etreg_bloodGroup.getText().toString().trim();
        String etRegPhone = etreg_phone.getText().toString().trim();
        String etRegGender = etreg_gender.getText().toString().trim();

        if (!validateEmail(etRegEmail) || !validateName(etRegUser) || !validatePassword(etRegPass) || !validateDob(etRegDob)
                || !validateBg(etRegBg) || !validatePhone(etRegPhone) || !validateGender(etRegGender))
            return;
        showProgressDialog("Registering you!");
        btnRegister.setEnabled(false);
        firebaseAuth.createUserWithEmailAndPassword(etRegEmail, etRegPass).addOnCompleteListener(this, this);
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

    private boolean validateName(String name) {
        boolean state = true;
        if (TextUtils.isEmpty(name)) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Enter Name!", Snackbar.LENGTH_SHORT).show();
            etreg_user.setError("Enter Name!");
            state = false;
        } else etreg_user.setError(null);
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
            etreg_email.setError("Enter email address!");
            state = false;

        } else etreg_email.setError(null);

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(findViewById(R.id.activity_sign_up), "Enter a valid email address!", Snackbar.LENGTH_SHORT).show();
            etreg_email.setError("Enter a valid email address!");
            state = false;
        } else etreg_email.setError(null);
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
        dismissProgressDialog();
        if (!task.isSuccessful()) {

             task.getException().printStackTrace();
            Snackbar.make(findViewById(R.id.activity_sign_up), "Sign Up failed, try again", Snackbar.LENGTH_SHORT).show();
            btnRegister.setEnabled(true);
        } else {

            String uid = task.getResult().getUser().getUid();
            dataManager.storeUserInfo(createUser(), uid);
            switchActivity(MainActivity.class);

        }
    }



    private User createUser() {
        User user = new User();
        user.setName(etreg_user.getText().toString());
        user.setDob(etreg_dob.getText().toString());
        user.setGender(etreg_gender.getText().toString());
        user.setTelephone(etreg_phone.getText().toString());
        user.setBloodGroup(etreg_bloodGroup.getText().toString());
        return user;
    }

    private void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {

    }
}
