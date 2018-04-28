package com.cdh.loadingview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.cdh.lodingviewlibary.DigitalLoadingView;

public class MainActivity extends AppCompatActivity {
    DigitalLoadingView dlv = null;

    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dlv = findViewById(R.id.loadding);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        progress += 10;
        dlv.setProgress(progress);
        return super.onTouchEvent(event);
    }
}
