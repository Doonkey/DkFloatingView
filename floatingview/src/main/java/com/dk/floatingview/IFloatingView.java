package com.dk.floatingview;

import android.view.View;

public interface IFloatingView {

    void show();
    void hide();

    View getView();

    void setOnClickListener(DkFloatingView.ViewClickListener listener);

}
