package com.dk.floatingview;

import android.app.Activity;
import android.app.Application;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

public class FloatingView implements IFloatingView, FloatLifecycle.Callback {

    private Application mApplication;
    private final DkFloatingView dkFloatingView;
    private final Class<? extends Activity>[] mFilterActivities;

    private WeakReference<FrameLayout> mContainer;
    private boolean isShow = false;

    FloatingView(FloatWindow.Builder builder) {
        mApplication = builder.mContext;
        mFilterActivities = builder.mFilterActivities;
        dkFloatingView = new DkFloatingView(mApplication, builder.mLayoutId, builder.mLayoutParam);
        builder.mFloatLifecycle.listener(this);
    }

    @Override
    public void show(){
        isShow = true;
        addViewToWindow();
    }

    @Override
    public void hide(){
        isShow = false;
        detach(getContainer());
    }

    @Override
    public View getView() {
        return dkFloatingView.getView();
    }

    @Override
    public void setOnClickListener(DkFloatingView.ViewClickListener listener) {
        dkFloatingView.setOnClickListener(listener);
    }

    @Override
    public void activityAttach(Activity activity) {
        if (!isFilterActivity(activity)){
            attach(getActivityRoot(activity));
        }
    }

    @Override
    public void activityDetach(Activity activity) {
        if (!isFilterActivity(activity)) {
            detach(getActivityRoot(activity));
        }
    }

    private boolean isFilterActivity(Activity activity){
        if(mFilterActivities == null){
            return false;
        }
        for (Class<? extends Activity> mFilterActivity : mFilterActivities) {
            if (mFilterActivity.getName().equals(activity.getClass().getName())){
                return true;
            }
        }
        return false;
    }

    private void addViewToWindow() {
        if (getContainer() == null) {
            return;
        }
        if (dkFloatingView.getParent() != null) {
            ((ViewGroup) dkFloatingView.getParent()).removeView(dkFloatingView);
        }
        if (isShow && !ViewCompat.isAttachedToWindow(dkFloatingView)) {
            getContainer().addView(dkFloatingView);
        }
    }

    private FrameLayout getContainer() {
        if (mContainer == null) {
            return null;
        }
        return mContainer.get();
    }

    private FrameLayout getActivityRoot(Activity activity) {
        if (activity == null) {
            return null;
        }
        try {
            return (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void attach(FrameLayout container) {
        if (getContainer() != null) {
            mContainer.clear();
        }
        mContainer = new WeakReference<>(container);
        addViewToWindow();
    }
    private void detach(FrameLayout container) {
        if (container != null && dkFloatingView.getParent() == container){
            container.removeView(dkFloatingView);
        }
        if (getContainer() != null && getContainer() == container) {
            mContainer.clear();
            mContainer = null;
        }
    }

}