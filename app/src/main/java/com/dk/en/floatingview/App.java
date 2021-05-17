package com.dk.en.floatingview;

import android.app.Application;
import android.widget.Toast;

import com.dk.floatingview.FloatWindow;
import com.dk.floatingview.DkFloatingView;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FloatWindow.with(this)
                .setLayoutId(R.layout.float_music)
                .build();
        FloatWindow.get().setOnClickListener(new DkFloatingView.ViewClickListener() {
            @Override
            public void onClick(int viewId) {
                switch (viewId){
                    case R.id.iv_player_status:
                        Toast.makeText(App.this, "状态",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.iv_player_close:
                        Toast.makeText(App.this, "关闭",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.iv_player_cover:
                        Toast.makeText(App.this, "封面",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
