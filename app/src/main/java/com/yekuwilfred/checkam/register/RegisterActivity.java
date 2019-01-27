package com.yekuwilfred.checkam.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.yekuwilfred.checkam.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       RegisterFragment registerFragment = new RegisterFragment();

      android.app.FragmentManager fragmentManager = getFragmentManager();

        fragmentManager
                .beginTransaction()
                .add(R.id.register_fragment_container, registerFragment)
                .commit();
    }
}
