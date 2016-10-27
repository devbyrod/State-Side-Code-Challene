package com.devbyrod.statesidetechtest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mandrake on 10/26/16.
 */

public class EarthquakeData implements Serializable{
    public String type;
    public Metadata metadata;
    public double[] bbox;
    @SerializedName("features")
    public List<Earthquake> earthquakes;
}
