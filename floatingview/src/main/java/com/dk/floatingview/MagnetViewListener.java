package com.dk.floatingview;

import android.view.MotionEvent;

public interface MagnetViewListener {

    void onRemove(FloatingMagnetView magnetView);

    void onClick(MotionEvent event);
}
