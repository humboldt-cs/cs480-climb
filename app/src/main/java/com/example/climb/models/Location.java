package com.example.climb.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("Location")
public class Location extends ParseObject
{
    public static final String KEY_OBJECT_ID = "objectId";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LAT_LONG = "latLong";


    public String getId() { return getString(KEY_OBJECT_ID); }
    public void setId(String objectId) { put(KEY_OBJECT_ID, objectId); }

    public String getName() { return getString(KEY_NAME); }
    public void setName(String name) { put(KEY_NAME, name); }

    public String getDescription() { return getString(KEY_DESCRIPTION); }
    public void setDescription(String description) { put(KEY_DESCRIPTION, description); }

    public ParseGeoPoint getLatLong() { return getParseGeoPoint(KEY_LAT_LONG); }
    public void setLatLong(ParseGeoPoint latLong) { put(KEY_LAT_LONG, latLong); }
}
