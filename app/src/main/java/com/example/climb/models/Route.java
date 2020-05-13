package com.example.climb.models;

import android.media.Image;
import android.provider.Contacts;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.util.List;

@ParseClassName("Route")
public class Route extends ParseObject
{
    public static final String KEY_OBJECT_ID = "objectId";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DIFFICULTY = "difficulty";
    public static final String KEY_METHOD = "method";
    public static final String KEY_THUMBNAIL = "thumbnail";


    public String getId() { return getString(KEY_OBJECT_ID); }
    public void setId(String objectId) { put(KEY_OBJECT_ID, objectId); }

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

    public ParseFile getThumbnail() { return getParseFile(KEY_THUMBNAIL); }
    public void setThumbnail(ParseFile thumbnail) { put(KEY_THUMBNAIL, thumbnail); }


    protected void queryPhotos() {
        ParseQuery<Photo> query = ParseQuery.getQuery("Photos");
        query.include(Photo.KEY_ASSOC_CLASS_ID);
        query.whereEqualTo(Route.KEY_OBJECT_ID, this);
        //query.setLimit(20);
        query.addDescendingOrder(Photo.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Photo>() {
            @Override
            public void done(List<Photo> photos, ParseException e) {
                if (e != null) {
                    Log.e("Route", "Issue with getting photos", e);
                    return;
                }
                for (Photo photo : photos) {
                    Log.i("Route", "Post: " + photo.getImage());
                }
                //allPhotos.addAll(photos);
                //adapter.notifyDataSetChanged();
            }
        });
    }

}
