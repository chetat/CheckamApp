package com.yekuwilfred.checkam.login;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;

public class LoginInteractor implements LoginMvp.interactor {

    LoginMvp.OnLoginListener mLoginListener;

    LoginInteractor(LoginMvp.OnLoginListener mOnLoginListener){
        this.mLoginListener = mOnLoginListener;
    }

    @Override
    public void loginUser(final Activity activity, String email, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mLoginListener.onSuccess(task.getResult().toString());
                        }
                        if (!task.isSuccessful()){
                            mLoginListener.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }
}
