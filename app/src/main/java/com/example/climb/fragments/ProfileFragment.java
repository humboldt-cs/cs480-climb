package com.example.climb.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.climb.R;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import static com.parse.Parse.getApplicationContext;

public class ProfileFragment extends Fragment {
    private ImageView profileImg;
    private TextView displayName;
    private TextView userBio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();

        profileImg = view.findViewById(R.id.profileImg);
        ParseFile img = currentUser.getParseFile("profilePhoto");
        if (img != null) {
            String imageUrl = img.getUrl();
            Glide.with(getApplicationContext()).load(imageUrl).into(profileImg);

        }

        displayName = view.findViewById(R.id.displayName);
        String display_name = currentUser.getString("displayName");
        displayName.setText(display_name);

        userBio = view.findViewById(R.id.userBio);
        String bio = currentUser.getString("bio");
        userBio.setText(bio);

    }

}
