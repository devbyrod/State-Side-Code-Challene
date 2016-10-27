package com.devbyrod.statesidetechtest.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.devbyrod.statesidetechtest.R;
import com.devbyrod.statesidetechtest.fragments.EventsListFragment;
import com.devbyrod.statesidetechtest.fragments.MapFragment;
import com.devbyrod.statesidetechtest.models.Earthquake;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mandrake on 10/26/16.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGER_SECTIONS = 2;
    private Context mContext;
    private EventsListFragment mEventsListFragment;
    private MapFragment mMapFragment;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public void addFragment(Fragment fragment) {
        if(fragment instanceof EventsListFragment) {
            mEventsListFragment = (EventsListFragment) fragment;
            return;
        }

        if(fragment instanceof MapFragment) {
            mMapFragment = (MapFragment) fragment;
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return mEventsListFragment == null ? EventsListFragment.newInstance() : mEventsListFragment;
        }

        return mMapFragment == null ? MapFragment.newInstance() : mMapFragment;
    }

    @Override
    public int getCount() {
        return PAGER_SECTIONS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.section_events);
            case 1:
                return mContext.getString(R.string.section_map);
        }
        return null;
    }

    public void onItemClicked(Earthquake earthquake){
        if(mMapFragment != null) {
            mMapFragment.onItemClicked(earthquake);
        }
    }
}
