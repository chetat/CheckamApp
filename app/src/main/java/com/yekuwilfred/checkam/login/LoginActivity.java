package com.yekuwilfred.checkam.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yekuwilfred.checkam.main.Place_list_activity;
import com.yekuwilfred.checkam.R;
import com.yekuwilfred.checkam.register.RegisterActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginMvp.View {

    private static final String TAG = "EmailPassword";

    private LoginPresenter mLoginPresenter;
    private EditText mEmailField;
    private EditText mPasswordField;
    private FirebaseUser user;
    public ProgressDialog mProgressDialog;


    //Firebase Auth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginPresenter = new LoginPresenter(this);

        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);
        //Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_up).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        if (isLoggedIn(user)) {
            navigateToHome();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_sign_in_button) {
            showProgressDialog();
            mLoginPresenter.signIn(this, mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
        if (i == R.id.sign_up){
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        }
    }

    @Override
    public void onLoginSuccess(String message) {
        hideProgressDialog();
        Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_SHORT).show();
        navigateToHome();
    }

    /*
     * Check if user already loggedIn
     * */
    public boolean isLoggedIn(FirebaseUser user) {
        boolean logged = false;
        if (user != null) {
            logged = true;
        }
        return logged;
    }

    //Go to Place_list_activity
    public void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, Place_list_activity.class);
        intent.putExtra("user", user);
        startActivity(new Intent(LoginActivity.this, Place_list_activity.class));
        finish();
    }


    @Override
    public void onLoginFailure(String message) {
        hideProgressDialog();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading_msg));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void setPresenter(LoginMvp.Presenter presenter) {

    }
}
