package com.dk.floatingview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

@SuppressLint("ViewConstructor")
public class DkFloatingView extends FloatingMagnetView implements View.OnClickListener {

    private final View mInflate;
    public DkFloatingView(@NonNull Context context, @LayoutRes int resource, ViewGroup.LayoutParams layoutParam) {
        super(context, null);
        mInflate = inflate(context, resource, this);
        setLayoutParams(layoutParam == null ? getParams(): layoutParam);
    }

    private FrameLayout.LayoutParams getParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.START;
        params.setMargins(100, params.topMargin, params.rightMargin, 100);
        return params;
    }

    public View getView(){
        return mInflate;
    }

    private ViewClickListener mListener;
    public void setOnClickListener(ViewClickListener listener) {
        this.mListener = listener;
        mInflate.post(() -> clickView2(mInflate));
    }

    private void clickView2(View view){
        if (view == null) return;
        view.setOnClickListener(DkFloatingView.this);
        if (view instanceof ViewGroup){
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ((ViewGroup) view).getChildAt(i);
                clickView2(childAt);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (mListener != null){
            mListener.onClick(view);
        }
    }

    public interface ViewClickListener {
        void onClick(View view);
    }
}
