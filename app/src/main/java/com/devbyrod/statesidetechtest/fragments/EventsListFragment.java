package com.devbyrod.statesidetechtest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devbyrod.statesidetechtest.R;
import com.devbyrod.statesidetechtest.adapters.EarthquakesAdapter;
import com.devbyrod.statesidetechtest.models.Earthquake;
import com.devbyrod.statesidetechtest.models.EarthquakeData;

import java.util.ArrayList;
import java.util.List;

public class EventsListFragment extends Fragment implements EarthquakesAdapter.EarthquakesAdapterListener {

    private static final String ARG_IN_VIEWPAGER = "arg_in_viewpager";

    private EventsListFragmentListener mListener;
    private View mView;
    private RecyclerView mRecyclerView;
    private boolean mInViewPager = false;

    public EventsListFragment() {
        // Required empty public constructor
    }

    public static EventsListFragment newInstance() {
        return new EventsListFragment();
    }

    public static EventsListFragment newInstance(boolean inViewPager) {
        EventsListFragment fragment = new EventsListFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IN_VIEWPAGER, inViewPager);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_events_list, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.earthquakesRecyclerView);
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInViewPager = getArguments().getBoolean(ARG_IN_VIEWPAGER);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventsListFragmentListener) {
            mListener = (EventsListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EventsListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClicked(Earthquake earthquake) {

        if(mListener != null) {

            mListener.onItemClicked(earthquake);
        }
    }

    public void setEarthquakesData(EarthquakeData data) {

        setRecyclerView(data);
    }

    private void setRecyclerView(EarthquakeData data) {

        if(mView == null)
            return;

        mRecyclerView.setAdapter(new EarthquakesAdapter(
                data.earthquakes == null ? new ArrayList<Earthquake>() : data.earthquakes,
                this,
                mView.getWidth(),
                mInViewPager)
        );
    }

    public interface EventsListFragmentListener {
        void onItemClicked(Earthquake earthquake);
    }
}
