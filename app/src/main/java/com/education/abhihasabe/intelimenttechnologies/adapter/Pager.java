package com.education.abhihasabe.intelimenttechnologies.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.education.abhihasabe.intelimenttechnologies.fragment.Tab1;
import com.education.abhihasabe.intelimenttechnologies.fragment.Tab2;
import com.education.abhihasabe.intelimenttechnologies.fragment.Tab3;
import com.education.abhihasabe.intelimenttechnologies.fragment.Tab4;
import com.education.abhihasabe.intelimenttechnologies.fragment.Tab5;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhi on 28-03-2018.
 */

public class Pager extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public Pager(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}