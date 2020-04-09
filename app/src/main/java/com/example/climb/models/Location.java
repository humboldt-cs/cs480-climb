package com.example.climb.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Location")
public class Location extends ParseObject
{
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";

    public String getName() { return getString(KEY_NAME); }
    public void setName(String name) { put(KEY_NAME, name); }

    public String getDescription() { return getString(KEY_DESCRIPTION); }
    public void setDescription(String description) { put(KEY_DESCRIPTION, description); }

    public double getLatitude() { return getDouble(KEY_LATITUDE); }
    public void setLatitude(double latitude) { put(KEY_LATITUDE, latitude); }

    public double getLongitude() { return getDouble(KEY_LONGITUDE); }
    public void setLongitude(double longitude) { put(KEY_LONGITUDE, longitude); }
}
