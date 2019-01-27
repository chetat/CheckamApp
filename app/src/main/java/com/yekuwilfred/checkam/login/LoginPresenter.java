package com.yekuwilfred.checkam.login;


import android.app.Activity;


public class LoginPresenter implements LoginMvp.Presenter, LoginMvp.OnLoginListener {

    private LoginInteractor mLoginInteractor;
    private LoginMvp.View mLoginView;

    public LoginPresenter(LoginMvp.View view) {
        this.mLoginView = view;
        mLoginInteractor = new LoginInteractor(this);
    }

    @Override
    public void signIn(Activity activity, String email, String password) {

        //Verify email input and password of user before Registering user

        if (validateEntry(email, password)) {
            mLoginInteractor.loginUser(activity, email, password);
        }
    }


    /*
     * Checks for correct values inputed by user
     * */
    @Override
    public boolean validateEntry(String email, String password) {
        boolean valid = true;
        if (isEmptyText(email)) {
            valid = false;
        }
        if (isEmptyText(password)) {
            valid = false;
        }
        return valid;
    }

    @Override
    public void start() {

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
        mLoginView.onLoginSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mLoginView.onLoginFailure(message);
    }
}
