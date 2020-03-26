package com.example.first;

import android.animation.TimeInterpolator;

public class MyTimeInterpolator implements TimeInterpolator {
    public float getInterpolation(float input){
        float fraction;
        fraction=input*input;
        return fraction;
    }
}
