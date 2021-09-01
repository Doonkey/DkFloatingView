package com.dk.floatingview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class FloatLifecycle implements Application.ActivityLifecycleCallbacks {

    private Callback mCallback;

    public FloatLifecycle bind(Application context) {
        context.registerActivityLifecycleCallbacks(this);
        return this;
    }

    public void listener(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (mCallback != null){
            mCallback.activityAttach(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (mCallback != null){
            mCallback.activityDetach(activity);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public interface Callback{
        void activityAttach(Activity activity);
        void activityDetach(Activity activity);
    }

}
