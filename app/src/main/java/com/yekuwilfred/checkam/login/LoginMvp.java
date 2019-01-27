package com.yekuwilfred.checkam.login;

import android.app.Activity;

import com.yekuwilfred.checkam.BasePresenter;
import com.yekuwilfred.checkam.BaseView;

public interface LoginMvp {

    interface View extends BaseView<Presenter> {
        void onLoginSuccess(String message);

        void onLoginFailure(String message);

        }

    interface Presenter extends BasePresenter {
        void signIn(Activity activity, String email, String password);

        boolean validateEntry(String email, String password);
    }

    interface interactor {
        //Firebase Login
        void loginUser(Activity activity, String email, String password);
    }

    interface OnLoginListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
