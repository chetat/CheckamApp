package com.yekuwilfred.checkam.tabsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yekuwilfred.checkam.R;

/**
 * Created by YEKUWILFRED on 4/13/2017.
 */

public class TileFragment extends Fragment {
    public TileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tile_fragment, container, false);
    }
}
