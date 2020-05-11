package com.example.climb;

// Helper class for storing markers
public class RouteMarker
{
    public float x;
    public float y;
    public float radius;

    public RouteMarker()
    {
        this.x = 0;
        this.y = 0;
        this.radius = 0;
    }

    public RouteMarker(float x, float y, float radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
}
