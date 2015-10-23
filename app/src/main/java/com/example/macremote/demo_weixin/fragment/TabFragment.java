package com.example.macremote.demo_weixin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macremote.demo_weixin.R;

/**
 * Created by macremote on 2015/10/21.
 */
public class TabFragment extends Fragment {

    private View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_1,container,false);
        return view;
    }
}
