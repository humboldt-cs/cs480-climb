package com.example.climb.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Beta")
public class Beta extends ParseObject
{
    public static final String KEY_BETA = "beta";
    public static final String KEY_USER = "user";
    public static final String KEY_ROUTE = "route";

    public ParseFile getBeta() { return getParseFile(KEY_BETA); }
    public void setBeta(ParseFile beta) { put(KEY_BETA, beta); }

    public ParseObject getUser() { return getParseObject(KEY_USER); }
    public void setUser(ParseObject user) { put(KEY_USER, user); }

    public ParseObject getRoute() { return getParseObject(KEY_ROUTE); }
    public void setRoute(ParseObject route) { put(KEY_ROUTE, route); }
}
