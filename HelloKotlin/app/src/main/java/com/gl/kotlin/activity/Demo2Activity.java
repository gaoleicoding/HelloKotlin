package com.gl.kotlin.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.gl.kotlin.R;

class Demo2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    void first() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(DemoActivity.this, BackGroundService.class);
//                startService(intent);
//                Log.v("DemoActivity", "延迟执行");
            }
        }, 1000 * 65);

    }

}
