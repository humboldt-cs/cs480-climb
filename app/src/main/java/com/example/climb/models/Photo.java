package com.example.climb.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Photos")
public class Photo extends ParseObject {

    public static final String KEY_ASSOC_CLASS_ID= "AssocClassID";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_DESCRIPTION = "description";



    public String getAssocClassId() { return getString(KEY_ASSOC_CLASS_ID); }
    public void setAssocClassId(String AssocClassId) { put(KEY_ASSOC_CLASS_ID, AssocClassId); }

    public ParseFile getImage() {
        return getParseFile(KEY_PHOTO);
    }
    public void setImage(ParseFile parseFile) {
        put(KEY_PHOTO, parseFile);
    }

    public String getDescription() { return getString(KEY_DESCRIPTION); }
    public void setDescription(String description) { put(KEY_DESCRIPTION, description); }
}
