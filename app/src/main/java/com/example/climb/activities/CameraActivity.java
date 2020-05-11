package com.example.climb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.climb.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

/*
    This class is responsible for taking images (and potentially videos in the future) to be used
    as betas for routes.
 */
public class CameraActivity extends AppCompatActivity implements LifecycleOwner, View.OnClickListener
{
    // Create orientation mapping, to communicate with AnnotationActivity
    static final SparseIntArray ORIENTATIONS = new SparseIntArray(4);
    static
    {
        ORIENTATIONS.append(Surface.ROTATION_0, 1);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 3);
        ORIENTATIONS.append(Surface.ROTATION_270, 2);
    }

    final static String TAG = "CameraActivity";

    // Request codes
    final int REQUEST_CODE_PERMISSIONS = 10;
    final int ANNOTATION_REQUEST_CODE = 420;

    // Permissions necessary for this activity
    final String[] REQUIRED_PERMISSIONS =
            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // Image capture to be used with CameraX to take a picture
    ImageCapture imageCapture;

    // Views
    PreviewView previewView;
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Locate views
        previewView = findViewById(R.id.previewView);
        container = findViewById(R.id.container);

        // Ensure all permissions are granted
        if (allPermissionsGranted())
        {
            startCamera();
        }
        else
        {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            );
        }

        // Listeners
        container.setOnClickListener(this);
    }

    // Take a picture when the user clicks on the preview
    @Override
    public void onClick(View v)
    {
        // Make/find file
        File dir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        if (!dir.exists() && !dir.mkdirs())
        {
            Log.d(TAG, "Failed to create dir");
            return;
        }
        File file = new File(dir.getPath() + File.separator + "photo.jpg");

        // Set up the camera...
        ImageCapture.OutputFileOptions outputFileOptions
                = new ImageCapture.OutputFileOptions.Builder(file).build();
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback()
                {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults)
                    {
                        // If photo is successfully saved, launch the annotation activity to edit the photo
                        Intent i = new Intent(CameraActivity.this, AnnotationActivity.class);
                        i.putExtra("filepath", file.getAbsolutePath());
                        i.putExtra("orientation", previewView.getDisplay().getRotation());
                        startActivityForResult(i, ANNOTATION_REQUEST_CODE);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception)
                    {
                        Toast.makeText(
                                CameraActivity.this,
                                "Picture not able to be created " + file.getAbsolutePath(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    // Set up camera systems
    void startCamera()
    {
        // Request camera provider
        ListenableFuture cameraProviderFuture
                = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try
            {
                WindowManager windowManager = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

                ProcessCameraProvider cameraProvider = (ProcessCameraProvider)cameraProviderFuture.get();
                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                /*
                    Bind preview
                 */
                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                Camera camera = cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageCapture
                );
                preview.setSurfaceProvider(
                        previewView.createSurfaceProvider(camera.getCameraInfo())
                );
            }
            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // Process result from permission request dialog box
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSIONS)
        {
            if (checkGrantResults(grantResults))
            {
                // May have to wrap in a call to previewView.post()
                startCamera();
            }
            else
            {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Check if all permissions have been granted
    boolean allPermissionsGranted()
    {
        boolean granted = true;

        for (String p : REQUIRED_PERMISSIONS)
        {
            if (ContextCompat.checkSelfPermission(getBaseContext(), p)
               != PackageManager.PERMISSION_GRANTED)
            {
                granted = false;
            }
        }

        return granted;
    }

    boolean checkGrantResults(int[] grantResults)
    {
        for (int i = 0; i < grantResults.length; i++)
        {
            if (PackageManager.PERMISSION_GRANTED != grantResults[i])
            {
                return false;
            }
        }

        return true;
    }

    // Handle the results from AnnotationActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ANNOTATION_REQUEST_CODE && resultCode == RESULT_OK)
        {
            finish();
        }
        else
        {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
