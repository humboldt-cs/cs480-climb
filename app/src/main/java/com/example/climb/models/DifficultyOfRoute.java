package com.example.climb.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("DifficultyOfRoute")
public class DifficultyOfRoute extends ParseObject
{
    public static final String KEY_ROUTE = "route";
    public static final String KEY_DIFFICULTY = "difficultyRating";

    public ParseObject getRoute() { return getParseObject(KEY_ROUTE); };
    public void setRoute(ParseObject routeObject) { put(KEY_ROUTE, routeObject); }

    public double getDifficulty() { return getInt(KEY_DIFFICULTY); }
    public void setDifficulty(int difficulty) { put(KEY_DIFFICULTY, difficulty); }
}
