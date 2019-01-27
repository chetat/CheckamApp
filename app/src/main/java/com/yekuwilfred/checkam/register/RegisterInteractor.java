package com.yekuwilfred.checkam.register;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;


public class RegisterInteractor implements RegisterContract.Interactor {

    private RegisterContract.OnRegisterListener mOnRegisterListener;

    RegisterInteractor(RegisterContract.OnRegisterListener listener) {
        this.mOnRegisterListener = listener;
    }

    /*
    * Register user to firebase using pasword and email
    * */
    @Override
    public void registerUser(Activity activity, String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mOnRegisterListener.onSuccess(task.getResult().toString());
                }
                if (!task.isSuccessful()){
                    mOnRegisterListener.onFailure(task.getException().toString());
                }
            }
        });
    }
}
