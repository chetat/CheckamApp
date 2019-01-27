package com.yekuwilfred.checkam.register;

import android.app.Activity;
import android.text.TextUtils;

import com.yekuwilfred.checkam.BaseView;

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.OnRegisterListener {

    RegisterInteractor mRegisterInteractor;
    RegisterContract.View mView;

    RegisterPresenter(RegisterContract.View view) {
        this.mView = view;
        mRegisterInteractor = new RegisterInteractor(this);
    }

    @Override
    public void createAccount(Activity activity, String email, String password, String cPassword) {
        if (validateEntry(email, password, cPassword )){
            mRegisterInteractor.registerUser(activity, email, password);
        }
    }

    @Override
    public boolean validateEntry(String email, String password, String confirmPassword) {
        boolean valid = true;
        if (isEmptyText(email)) {
            valid = false;
        }
        if (isEmptyText(password) || !password.equals(confirmPassword)) {
            valid = false;
        }
        return valid;
    }
    //Check if string is empty
    private boolean isEmptyText(String text) {
        boolean empty = false;
        if (text.isEmpty()) {
            empty = true;
        }
        return empty;
    }

    @Override
    public void onSuccess(String message) {
        mView.onRegisterSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mView.onRegisterFailure(message);
    }
}
