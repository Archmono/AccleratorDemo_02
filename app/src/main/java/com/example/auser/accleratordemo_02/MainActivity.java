package com.example.auser.accleratordemo_02;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sm;
    Sensor sr;
    TextView tv;
    ImageView imgV;
    RelativeLayout layout;
    double mx = 0, my = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tv = (TextView)findViewById(R.id.textView1);
        imgV = (ImageView)findViewById(R.id.imageView2);
        layout = (RelativeLayout)findViewById(R.id.layout);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(mx == 0){
            mx = (layout.getWidth() - imgV.getWidth())/20;
            my = (layout.getHeight() - imgV.getHeight())/20;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imgV.getLayoutParams();
        params.leftMargin = (int)((10 - event.values[0])*mx);
        params.topMargin = (int)((10 - event.values[1])*my);
        imgV.setLayoutParams(params);

        tv.setText(String.format("X=%1.2f, Y=%1.2f, Z=%1.2f",event.values[0],event.values[1],event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,sr,SensorManager.SENSOR_DELAY_NORMAL);
    }
}
