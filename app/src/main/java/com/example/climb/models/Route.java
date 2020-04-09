package com.example.climb.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Route")
public class Route extends ParseObject
{
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";

    public String getName() { return getString(KEY_NAME); }
    public void setName(String name) { put(KEY_NAME, name); }

    public String getDescription() { return getString(KEY_DESCRIPTION); }
    public void setDescription(String description) { put(KEY_DESCRIPTION, description); }
}
