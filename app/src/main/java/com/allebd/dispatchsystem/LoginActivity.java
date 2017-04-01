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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import android.databinding.DataBindingUtil;
//import com.allebd.dispatchsystem.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {
//    DatabaseHelper myDb;
    Context c;
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvRegister;
    private Button btnLogin;
    private ProgressBar mprogress;
//    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            switchActivity(MainActivity.class);
        }

        etUsername = (EditText) findViewById(R.id.input_user);
        etPassword = (EditText) findViewById(R.id.input_password);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        btnLogin = (Button) findViewById(R.id.btn_Login);
        mprogress = (ProgressBar) findViewById(R.id.progressBar);

        //setContentView(this, R.layout.activity_login);
        if (getIntent()!=null){
            Intent intent = getIntent();
            String email = intent.getStringExtra("input_user");
            String password = intent.getStringExtra("input_password");
            id = intent.getStringExtra("id");

            etUsername.setText(email);
            etPassword.setText(password);
        }

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:
                login();
                break;
            case R.id.tvRegister:
                switchActivity(RegisterActivity.class);
                break;
        }
    }

    private void login() {
        String emailText = etUsername.getText().toString().trim();
        String passwordText = etPassword.getText().toString().trim();
        if (!validate(emailText, passwordText)) return;
        mprogress.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);
        //auth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(this, this);
    }

    private boolean validate(String email, String password) {

        boolean state = true;

        if (TextUtils.isEmpty(email)) {
            Snackbar.make(findViewById(R.id.activity_login), "Enter email address!", Snackbar.LENGTH_SHORT).show();
            etUsername.setError("Enter email address");
            state = false;
        } else etUsername.setError(null);


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(findViewById(R.id.activity_login), "Enter a valid email address!", Snackbar.LENGTH_SHORT).show();
            etUsername.setError("Enter a valid email address!");
            state = false;
        } else etUsername.setError(null);


        if (TextUtils.isEmpty(password)) {
            Snackbar.make(findViewById(R.id.activity_login), "Enter password", Snackbar.LENGTH_SHORT).show();
            etPassword.setError("Enter password");
            state = false;
        } else etPassword.setError(null);


        if (password.length() < 5) {
            Snackbar.make(findViewById(R.id.activity_login), R.string.pass_short, Snackbar.LENGTH_SHORT).show();
            etPassword.setError("Password too short");
            state = false;
        } else etPassword.setError(null);

        return state;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {

        mprogress.setVisibility(View.GONE);
        if (!task.isSuccessful()) {
            Snackbar.make(findViewById(R.id.activity_login), R.string.auth_failed, Snackbar.LENGTH_SHORT).show();
            btnLogin.setEnabled(true);
        } else {
            switchActivity(MainActivity.class);
        }
    }

    private void switchActivity(Class classFile) {
        Intent intent = new Intent(this, classFile);
        if (id != null){
            intent.putExtra("id", id);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
