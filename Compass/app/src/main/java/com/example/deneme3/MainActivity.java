package com.example.deneme3;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private float[] mGravity=new float[3];
    private float[] mGeomagnetic=new float[3];
    private float azimuth =0f;
    ImageView imageView;
    private float CurrectAzimuth=0f;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap());

        mSensorManager =(SensorManager) getSystemService(SENSOR_SERVICE);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    public Bitmap bitmap(){
        int width = 300;
        int height = 300;

        // Bitmap olusturma
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Canvas objesi
        Canvas canvas = new Canvas(bitmap);

        // Kalemleri olusturma
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        Paint circle = new Paint();
        circle.setColor(Color.DKGRAY);
        circle.setStyle(Paint.Style.STROKE);
        circle.setStrokeWidth(1);

        Paint paint2 = new Paint();
        paint2.setColor(Color.BLUE);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(2);

        Paint paint3 = new Paint();
        paint3.setColor(Color.BLACK);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(2);

        Paint paint4 = new Paint();
        paint4.setColor(Color.GRAY);
        paint4.setStyle(Paint.Style.STROKE);
        paint4.setStrokeWidth(2);

        // Bitmap dairesi
        int centerX = width / 2;
        int centerY = height / 2;
        //Math.min(width, height) / 2 -5;
        int radius = 120;
        canvas.drawCircle(centerX, centerY, radius, circle);

        // Cizgiler

        canvas.drawLine(centerX, centerY, centerX, 31, paint3); // N

        //canvas.drawLine(width / 2, height / 2 - radius, width / 2, height / 2 - radius + 20, paint);
        canvas.drawLine(centerX, centerY, centerX+86, centerY-86, paint);
        canvas.drawLine(centerX, centerY, centerX-86, centerY-86, paint);
        canvas.drawLine(centerX, centerY, centerX+86, centerY+86, paint);
        canvas.drawLine(centerX, centerY, centerX-86, centerY+86, paint);
        canvas.drawLine(centerX, centerY, width - 31, centerY, paint3); // E
        canvas.drawLine(centerX, centerY, centerX, height - 31, paint3); // S
        canvas.drawLine(centerX, centerY, 31, centerY, paint3); // W

        // Yönler
        paint.setTextSize(20);
        paint2.setTextSize(20);
        paint4.setTextSize(20);
        canvas.drawText("N", centerX-7, 25, paint);
        canvas.drawText("NE", centerX+86, centerY-86, paint4);
        canvas.drawText("NW", centerX-110, centerY-90, paint4);
        canvas.drawText("SE", centerX+86, centerY+105, paint4);
        canvas.drawText("SW", centerX-112, centerY+105, paint4);
        canvas.drawText("E", width - 27, centerY+5, paint2);
        canvas.drawText("S", centerX-6, height - 13, paint);
        canvas.drawText("W", 11, centerY+5, paint2);

        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        Path ok_N = new Path();
        ok_N.moveTo(width / 2, height / 2 - radius);
        ok_N.lineTo(width / 2 + 10, height / 2 - radius + 20);
        ok_N.lineTo(width / 2 - 10, height / 2 - radius + 20);
        ok_N.close();
        paint2.setColor(Color.BLACK);
        canvas.drawPath(ok_N, paint2);

        return bitmap;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        final float alpha=0.97f;
        synchronized (this){
            if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                mGravity[0]=alpha*mGravity[0]+(1-alpha)*sensorEvent.values[0];
                mGravity[1]=alpha*mGravity[1]+(1-alpha)*sensorEvent.values[1];
                mGravity[2]=alpha*mGravity[2]+(1-alpha)*sensorEvent.values[2];
            }
            if(sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
                mGeomagnetic[0]=alpha*mGeomagnetic[0]+(1-alpha)*sensorEvent.values[0];
                mGeomagnetic[1]=alpha*mGeomagnetic[1]+(1-alpha)*sensorEvent.values[1];
                mGeomagnetic[2]=alpha*mGeomagnetic[2]+(1-alpha)*sensorEvent.values[2];
            }
            float R[]=new float[9];
            float I[]=new float[9];
            boolean success=SensorManager.getRotationMatrix(R,I,mGravity,mGeomagnetic);
            if(success)
            {
                float orientation[]=new float[3];
                SensorManager.getOrientation(R,orientation);
                azimuth=(float) Math.toDegrees(orientation[0]);
                azimuth=(azimuth+360)%360;
                Animation anim=new RotateAnimation(-CurrectAzimuth,-azimuth,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                CurrectAzimuth=azimuth;
                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);
                imageView.startAnimation(anim);

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}