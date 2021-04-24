package com.gl.kotlin.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.gl.kotlin.R;

class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WebView.disableWebView();
        }
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
