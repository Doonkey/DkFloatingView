package com.dk.floatingview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressLint("ViewConstructor")
public class DkFloatingView extends FloatingMagnetView implements MagnetViewListener {

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

    private final HashMap<Integer, Region> regionHashMap = new HashMap<>();
    private void clickView(View view){
        if (view == null){
            return;
        }
        if (view instanceof ViewGroup){
            view.setClickable(false);
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ((ViewGroup) view).getChildAt(i);
                clickView(childAt);
            }
        }else {
            view.setClickable(false);
            Rect rect = new Rect();
            view.getHitRect(rect);
            Region region = new Region(rect);
            regionHashMap.put(view.getId(), region);
        }
    }

    public View getView(){
        return mInflate;
    }

    private ViewClickListener mListener;
    public void setOnClickListener(ViewClickListener listener) {
        this.mListener = listener;
        mInflate.post(new Runnable() {
            @Override
            public void run() {
                regionHashMap.clear();
                clickView(mInflate);
                setMagnetViewListener(DkFloatingView.this);
            }
        });
    }

    @Override
    public void onRemove(FloatingMagnetView magnetView) {
        regionHashMap.clear();
    }

    @Override
    public void onClick(MotionEvent event) {
        Set<Map.Entry<Integer, Region>> entries = regionHashMap.entrySet();
        for (Map.Entry<Integer, Region> entry : entries) {
            if (entry.getValue().contains((int)event.getX(), (int)event.getY())){
                if (mListener != null){
                    mListener.onClick(entry.getKey());
                }
                break;
            }
        }
    }

    public interface ViewClickListener {
        void onClick(int viewId);
    }
}
