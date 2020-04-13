package com.example.climb.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("User")
public class User extends ParseObject
{
    public static final String KEY_DISPLAY_NAME = "displayName";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_PHOTO = "profilePhoto";

    public String getDisplayName() { return getString(KEY_DISPLAY_NAME); }
    public void setDisplayName(String displayName) { put(KEY_DISPLAY_NAME, displayName); }

    public String getUsername() { return getString(KEY_USERNAME); }
    public void setUsername(String username) { put(KEY_USERNAME, username); }

    public ParseFile getProfilePhoto() { return getParseFile(KEY_PROFILE_PHOTO); }
    public void setProfilePhoto(ParseFile profilePhoto) { put(KEY_PROFILE_PHOTO, profilePhoto); }
}
