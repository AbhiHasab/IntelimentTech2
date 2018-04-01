package com.education.abhihasabe.intelimenttechnologies;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.education.abhihasabe.intelimenttechnologies.adapter.Pager;
import com.education.abhihasabe.intelimenttechnologies.fragment.Tab1;
import com.education.abhihasabe.intelimenttechnologies.fragment.Tab2;
import com.education.abhihasabe.intelimenttechnologies.fragment.Tab3;
import com.education.abhihasabe.intelimenttechnologies.fragment.Tab4;
import com.education.abhihasabe.intelimenttechnologies.fragment.Tab5;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        Pager adapter = new Pager(getSupportFragmentManager());
        adapter.addFrag(new Tab1(), "Item 1");
        adapter.addFrag(new Tab2(), "Item 2");
        adapter.addFrag(new Tab3(), "Item 3");
        adapter.addFrag(new Tab4(), "Item 4");
        adapter.addFrag(new Tab5(), "Item 5");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Call","Restart");
    }
}