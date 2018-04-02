package com.education.abhihasabe.intelimenttechnologies.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.education.abhihasabe.intelimenttechnologies.R;
/**
 * Created by Abhi on 28-03-2018.
 */

public class Tab2 extends Fragment{

    public Tab2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab2, container, false);
        return v;
    }
}
