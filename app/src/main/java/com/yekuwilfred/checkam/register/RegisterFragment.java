package com.yekuwilfred.checkam.register;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yekuwilfred.checkam.main.Place_list_activity;
import com.yekuwilfred.checkam.R;

import androidx.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener, RegisterContract.View {

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;

     FirebaseAuth mAuth;
     RegisterPresenter mPresenter;
    public ProgressDialog mProgressDialog;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);


        mEmailField = view.findViewById(R.id.field_email);
        mPasswordField = view.findViewById(R.id.field_password);
        mConfirmPasswordField = view.findViewById(R.id.confirm_password);


        mPresenter = new RegisterPresenter(this);
        view.findViewById(R.id.create_account_btn).setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_account_btn:
                mPresenter.createAccount(getActivity(), mEmailField.getText().toString(),
                        mPasswordField.getText().toString(),
                        mConfirmPasswordField.getText().toString());
        }
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
        startActivity(new Intent(getActivity(), Place_list_activity.class));
        getActivity().finish();
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
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
    public void onRegisterSuccess(String message) {
        hideProgressDialog();
        Toast.makeText(getActivity(), "Successfully Logged in", Toast.LENGTH_SHORT).show();
        navigateToHome();
    }

    @Override
    public void onRegisterFailure(String message) {
        hideProgressDialog();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

    }
}

