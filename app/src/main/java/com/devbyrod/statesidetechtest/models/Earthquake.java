package com.devbyrod.statesidetechtest.models;

import com.devbyrod.statesidetechtest.R;

/**
 * Created by Mandrake on 10/26/16.
 */

public class Earthquake {
    public String type, id;
    public EarthquakeProperties properties;
    public EarthquakeGeometry geometry;

    public static int getMagnitudeColorResource(float magnitude) {

        int mag = (int)magnitude;

        switch(mag) {
            case 0: return R.color.colorEarthquakeMagnitude0;
            case 1: return R.color.colorEarthquakeMagnitude1;
            case 2: return R.color.colorEarthquakeMagnitude2;
            case 3: return R.color.colorEarthquakeMagnitude3;
            case 4: return R.color.colorEarthquakeMagnitude4;
            case 5: return R.color.colorEarthquakeMagnitude5;
            case 6: return R.color.colorEarthquakeMagnitude6;
            case 7: return R.color.colorEarthquakeMagnitude7;
            case 8: return R.color.colorEarthquakeMagnitude8;
            default: return R.color.colorEarthquakeMagnitude9;
        }
    }

    public static int getMagnitudeNameResource(float magnitude) {

        int mag = (int)magnitude;

        switch(mag) {
            case 0: return R.string.earthquake_magnitude_0;
            case 1: return R.string.earthquake_magnitude_1;
            case 2: return R.string.earthquake_magnitude_2;
            case 3: return R.string.earthquake_magnitude_3;
            case 4: return R.string.earthquake_magnitude_4;
            case 5: return R.string.earthquake_magnitude_5;
            case 6: return R.string.earthquake_magnitude_6;
            case 7: return R.string.earthquake_magnitude_7;
            case 8: return R.string.earthquake_magnitude_8;
            default: return R.string.earthquake_magnitude_9;
        }
    }
}
