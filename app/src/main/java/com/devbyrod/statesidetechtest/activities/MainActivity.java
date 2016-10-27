package com.devbyrod.statesidetechtest.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.devbyrod.statesidetechtest.R;
import com.devbyrod.statesidetechtest.adapters.SectionsPagerAdapter;
import com.devbyrod.statesidetechtest.common.Constants;
import com.devbyrod.statesidetechtest.fragments.DatePickerFragment;
import com.devbyrod.statesidetechtest.fragments.EventsListFragment;
import com.devbyrod.statesidetechtest.fragments.MapFragment;
import com.devbyrod.statesidetechtest.models.Earthquake;
import com.devbyrod.statesidetechtest.models.EarthquakeData;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements EventsListFragment.EventsListFragmentListener,
                                                                MapFragment.MapFragmentListener,
                                                                DatePickerFragment.DatePickerFragmentListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int mDateToSet;
    private Date mStartDate, mEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.container);

        if(mViewPager != null) {

            setHandheldUI();
        } else {

            setTabletUI();
        }

        setSharedUI();
        setInitialDates();

        try {
            fetchEarthquakes(mStartDate, mEndDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(Earthquake earthquake) {

        if(mSectionsPagerAdapter != null) {
            mSectionsPagerAdapter.onItemClicked(earthquake);
            mViewPager.setCurrentItem(1);
            return;
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentMap);

        if(fragment != null) {

            ((MapFragment)fragment).onItemClicked(earthquake);
        }
    }

    @Override
    public void onDateSet(Date date) {

        String dateText;

        if(mDateToSet == R.id.txtStartDate) {
            mStartDate = date;
            dateText = getString(R.string.date_from);
        } else {
            mEndDate = date;
            dateText = getString(R.string.date_to);
        }

        ((TextView)findViewById(mDateToSet)).setText(dateText + " " + new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    private void setHandheldUI() {

        if(mSectionsPagerAdapter == null) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
            mSectionsPagerAdapter.addFragment(EventsListFragment.newInstance(true));
            mSectionsPagerAdapter.addFragment(MapFragment.newInstance());

            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        }
    }

    private void setTabletUI() {}

    private void setSharedUI(){

        TextView date = (TextView) findViewById(R.id.txtStartDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(), DatePickerFragment.TAG);
                mDateToSet = R.id.txtStartDate;
            }
        });

        date = (TextView) findViewById(R.id.txtEndDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(), DatePickerFragment.TAG);
                mDateToSet = R.id.txtEndDate;
            }
        });

        Button button = (Button) findViewById(R.id.btnGo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fetchEarthquakes(mStartDate, mEndDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setInitialDates() {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        mStartDate = new Date();
        mEndDate = calendar.getTime();
        ((TextView)findViewById(R.id.txtStartDate)).setText(getString(R.string.date_from) + " " + new SimpleDateFormat("yyyy-MM-dd").format(mStartDate));
        ((TextView)findViewById(R.id.txtEndDate)).setText(getString(R.string.date_to) + " " + new SimpleDateFormat("yyyy-MM-dd").format(mEndDate));
    }

    private void fetchEarthquakes(Date startDate, Date endDate) throws Exception {

        String format = "yyyy-MM-dd";
        String start = new SimpleDateFormat(format).format(startDate);
        String end = new SimpleDateFormat(format).format(endDate);
        String query = String.format(Constants.ENDPOINT_QUERY, start, end);
        Ion.with(this)
                .load(query)
                .as(new TypeToken<EarthquakeData>(){})
                .setCallback(new FutureCallback<EarthquakeData>() {
                    @Override
                    public void onCompleted(Exception e, EarthquakeData result) {

                        setEarthquakesData(result);
                    }
                });
    }

    private void setEarthquakesData(EarthquakeData data) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentEventsList);

        if(fragment != null) {

            ((EventsListFragment)fragment).setEarthquakesData(data);

            fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentMap);

            if(fragment != null) {

                ((MapFragment)fragment).setEarthquakesData(data);
            }

            return;
        }

        fragment = mSectionsPagerAdapter.getItem(0);
        if(fragment != null) {

            ((EventsListFragment)fragment).setEarthquakesData(data);
        }

        fragment = mSectionsPagerAdapter.getItem(1);

        if(fragment != null) {

            ((MapFragment)fragment).setEarthquakesData(data);
        }
    }
}
