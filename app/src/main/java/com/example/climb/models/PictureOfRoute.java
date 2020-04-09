package com.example.climb.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("PictureOfRoute")
public class PictureOfRoute extends ParseObject
{
    public static final String KEY_ROUTE = "route";
    public static final String KEY_BETA = "beta";

    public ParseObject getRoute() { return getParseObject(KEY_ROUTE); }
    public void setRoute(ParseObject route) { put(KEY_ROUTE, route); }

    public ParseFile getBeta() { return getParseFile(KEY_BETA); }
    public void setBeta(ParseFile beta) { put(KEY_BETA, beta); }
}
