package com.dk.en.floatingview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * @ClassName TestActivity
 * @Description
 * @Author Yunpeng Li
 * @Creation 2018/3/15 下午5:01
 * @Mender Yunpeng Li
 * @Modification 2018/3/15 下午5:01
 */
public class Test1_1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ((TextView) findViewById(R.id.page_num)).setText("页面Test1_1Activity");
    }

    public void createActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Test2Activity.class);
        startActivity(intent);
    }
}
