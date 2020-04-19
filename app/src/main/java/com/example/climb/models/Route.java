package com.example.climb.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Route")
public class Route extends ParseObject
{
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DIFFICULTY = "difficulty";
    public static final String KEY_METHOD = "method";

    public String getName() { return getString(KEY_NAME); }
    public void setName(String name) { put(KEY_NAME, name); }

    public String getDescription() { return getString(KEY_DESCRIPTION); }
    public void setDescription(String description) { put(KEY_DESCRIPTION, description); }

    public ParseObject getLocation() { return getParseObject(KEY_LOCATION); }
    public void setLocation(ParseObject location) { put(KEY_LOCATION, location); }

    public String getDifficulty() { return getString(KEY_DIFFICULTY); }
    public void setDifficulty(String difficulty) { put(KEY_DIFFICULTY, difficulty); }

    public String getMethod() { return getString(KEY_METHOD); }
    public void setMethod(String method) { put(KEY_METHOD, method); }
}
