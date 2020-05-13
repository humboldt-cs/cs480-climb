package com.example.climb.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import com.example.climb.RouteMarker;

import java.util.ArrayList;

// Custom class for annotating an image; utilizes Android's Canvas
public class AnnotationView extends androidx.appcompat.widget.AppCompatImageView
{
    ArrayList<RouteMarker> routeMarkers = new ArrayList();
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // Constructor methods
    public AnnotationView(Context context)
    {
        super(context);

        init(null);
    }

    public AnnotationView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        init(attrs);
    }

    public AnnotationView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    // Initialization method to be used in all constructors
    void init(AttributeSet set)
    {
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        for (int i = 0; i < routeMarkers.size(); i++)
        {
            RouteMarker routeMarker = routeMarkers.get(i);

            canvas.drawOval(
                    routeMarker.x - routeMarker.radius,
                    routeMarker.y - routeMarker.radius,
                    routeMarker.x + routeMarker.radius,
                    routeMarker.y + routeMarker.radius,
                    paint
            );
        }
    }

    // Methods to add markers to the image
    public void addMarker(float x, float y, float radius)
    {
        routeMarkers.add(new RouteMarker(x, y, radius));
    }

    public void undo()
    {
        routeMarkers.remove(routeMarkers.size() - 1);
    }

    public void finalize()
    {
        Bitmap bmp = ((BitmapDrawable)getDrawable()).getBitmap();
        Bitmap tempBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(tempBmp);

        canvas.drawBitmap(bmp, 0, 0, null);
        for (int i = 0; i < routeMarkers.size(); i++)
        {
            RouteMarker routeMarker = routeMarkers.get(i);

            canvas.drawOval(
                    routeMarker.x - routeMarker.radius,
                    routeMarker.y - routeMarker.radius,
                    routeMarker.x + routeMarker.radius,
                    routeMarker.y + routeMarker.radius,
                    paint
            );
        }

        setImageDrawable(new BitmapDrawable(getResources(), tempBmp));
    }
}
