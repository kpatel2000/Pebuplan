package com.example.pebuplan.fragments.tipids;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pebuplan.R;


public class TipidTips extends Fragment {




    public static TipidTips newInstance(String param1, String param2) {
        TipidTips fragment = new TipidTips();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tipid_tips, container, false);

        return view;
    }
}