package com.yekuwilfred.checkam.register;

import android.app.Activity;

import com.yekuwilfred.checkam.BaseView;
import com.yekuwilfred.checkam.login.LoginMvp;

public interface RegisterContract extends LoginMvp {

    interface View extends BaseView<Presenter> {
        void onRegisterSuccess(String message);

        void onRegisterFailure(String message);
    }

    interface Presenter {
        void createAccount(Activity activity, String email, String password, String cPassword);

        boolean validateEntry(String email, String password, String cPassword);
    }

    interface Interactor {
        void registerUser(Activity activity, String email, String password);
    }

    interface OnRegisterListener {
        void onSuccess(String message);

        void onFailure(String message);
    }


}
