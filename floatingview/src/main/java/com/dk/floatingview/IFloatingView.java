package com.dk.floatingview;

public interface IFloatingView {

    void show();
    void hide();

    void setOnClickListener(DkFloatingView.ViewClickListener listener);

}
