package com.dk.floatingview;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public class FloatWindow {

    private static Builder builder;

    private FloatWindow() {
    }

    public static IFloatingView get(){
        if (builder == null){
            throw new IllegalArgumentException("can not invoke before with()!");
        }
        return builder.floatingView;
    }

    public static Builder with(@NonNull Application context) {
        builder = new Builder(context);
        return builder;
    }

    public static class Builder {

        Application mContext;
        FloatLifecycle mFloatLifecycle;
        int mLayoutId;
        ViewGroup.LayoutParams mLayoutParam;
        Class<? extends Activity>[] mFilterActivities;
        IFloatingView floatingView;

        private Builder(Application context) {
            this.mContext = context;
            this.mFloatLifecycle = new FloatLifecycle().bind(mContext);
        }
        public Builder setLayoutId(int layoutId){
            this.mLayoutId = layoutId;
            return this;
        }
        public Builder setLayoutParam(ViewGroup.LayoutParams layoutParam){
            this.mLayoutParam = layoutParam;
            return this;
        }
        public Builder setFilter(@NonNull Class<? extends Activity>... activities) {
            mFilterActivities = activities;
            return this;
        }
        public void build() {
            floatingView = new FloatingView(this);
        }
    }
}
