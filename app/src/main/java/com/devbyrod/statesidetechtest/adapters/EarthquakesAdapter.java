package com.devbyrod.statesidetechtest.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devbyrod.statesidetechtest.R;
import com.devbyrod.statesidetechtest.models.Earthquake;

import java.util.List;

/**
 * Created by Mandrake on 10/26/16.
 */

public class EarthquakesAdapter extends RecyclerView.Adapter<EarthquakesAdapter.ViewHolder> {

    private static final float SCREEN_WIDTH_SLICE_TO_USE = 1.5f;
    private static final int POST_ANIMATION_DURATION = 1200;
    private static final float POST_INITIAL_ALPHA = 0f;
    private int mLastPosition = -1;
    private boolean mInViewPager;

    public interface EarthquakesAdapterListener{
        void onItemClicked(Earthquake earthquake);
    }

    private List<Earthquake> mEarthquakes;
    private Context mContext;
    private EarthquakesAdapterListener mListener;
    float mDistanceToMoveX = 0;

    public EarthquakesAdapter(List<Earthquake> earthquakes, EarthquakesAdapterListener listener, int fragmentWidth, boolean inViewPager) {
        mEarthquakes = earthquakes;
        mListener = listener;
        mDistanceToMoveX = fragmentWidth / SCREEN_WIDTH_SLICE_TO_USE;
        mInViewPager = inViewPager;
    }

    public void clear() {

        if(mEarthquakes == null || mEarthquakes.isEmpty()) return;

        mEarthquakes.clear();
        notifyDataSetChanged();
        mLastPosition = -1;
    }

    public void addAll(List<Earthquake> earthquakes) {
        mEarthquakes.addAll(earthquakes);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_earthquake_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mEarthquakes == null || mEarthquakes.isEmpty())
            return;

        final Earthquake earthquake = mEarthquakes.get(position);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null) {

                    mListener.onItemClicked(earthquake);
                }
            }
        });

        holder.txtLocation.setText(earthquake.properties.place);
        holder.txtLatitude.setText(mContext.getString(R.string.latitude) + ": " + new Double(earthquake.geometry.coordinates[1]).toString());
        holder.txtLongitude.setText(mContext.getString(R.string.longitude) + ": " + new Double(earthquake.geometry.coordinates[0]).toString());

        processMagnitude(holder, earthquake.properties.mag);
        manageAnimation(holder.view, position);
    }

    @Override
    public int getItemCount() {
        return mEarthquakes == null ? 0 : mEarthquakes.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
//        super.onViewRecycled(holder);
    }

    private void processMagnitude(ViewHolder holder, float magnitude) {

        int color = ContextCompat.getColor(mContext, Earthquake.getMagnitudeColorResource(magnitude));

        holder.txtMagnitude.setText(mContext.getString(Earthquake.getMagnitudeNameResource(magnitude)) + " " +
                new Float(magnitude).toString());

        holder.viewMagnitude.setBackgroundColor(color);
    }

    private void manageAnimation(final View view, int position) {

//        if(position < mLastPosition || (mInViewPager && position < 6))
//            return;
        if(mInViewPager && (position < mLastPosition || position < 6))
            return;

        view.setAlpha(POST_INITIAL_ALPHA);
        view.setX(mDistanceToMoveX);
        view.animate()
                .setDuration(POST_ANIMATION_DURATION)
                .alpha(1.0f)
                .translationX(0)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setAlpha(1);
                        view.setX(0);
                    }
                });

        mLastPosition = position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final View view;
        final TextView txtLocation;
        final TextView txtLatitude;
        final TextView txtLongitude;
        final TextView txtMagnitude;
        final View viewMagnitude;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            txtLocation = (TextView) itemView.findViewById(R.id.txtLocation);
            txtLatitude = (TextView) itemView.findViewById(R.id.txtLatitude);
            txtLongitude = (TextView) itemView.findViewById(R.id.txtLongitude);
            txtMagnitude = (TextView) itemView.findViewById(R.id.txtMagnitude);
            viewMagnitude = itemView.findViewById(R.id.viewMagnitude);
        }
    }
}
