package com.example.climb.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.climb.R;
import com.example.climb.views.AnnotationView;

public class AnnotationActivity extends AppCompatActivity implements View.OnTouchListener
{
    public static String TAG = "AnnotationActivity";

    AnnotationView ivTakenImage;
    Button btnUpload;
    Button btnUndo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);

        // Find views
        ivTakenImage = findViewById(R.id.ivTakenImage);
        btnUpload = findViewById(R.id.btnUpload);
        btnUndo = findViewById(R.id.btnUndo);

        // Get file and intended orientation
        String filepath = getIntent().getStringExtra("filepath");
        int orientation = getIntent().getIntExtra("orientation", 0);

        // Get bitmap of the taken image
        Bitmap myBitmap = BitmapFactory.decodeFile(filepath);

        // Fix orientation of the bitmap
        try
        {
            ExifInterface exif = new ExifInterface(filepath);
            Matrix matrix = new Matrix();

            if (orientation == Surface.ROTATION_0)
            {
                matrix.postRotate(90);
            }
            else if (orientation == Surface.ROTATION_270)
            {
                matrix.postRotate(180);
            }

            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
        }
        catch (Exception e)
        {

        }

        // Set final bitmap
        ivTakenImage.setImageBitmap(myBitmap);

        // Listeners
        ivTakenImage.setOnTouchListener(this);
        btnUndo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    ivTakenImage.undo();
                    ivTakenImage.invalidate();
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        // On action down, add a marker
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
        {
            ivTakenImage.addMarker(event.getX(), event.getY(), 25);
            // Tell Android to update the view
            ivTakenImage.invalidate();
        }

        // Allow touch event to be passed on
        return false;
    }
}
