package com.attilene.luxmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView luxValue;
    Button luxRecord;
    Button luxStart;
    Button luxStop;
    SensorManager sensorManager;
    Sensor luxSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        luxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        luxValue = findViewById(R.id.lux_value);
        luxRecord = findViewById(R.id.lux_record);
        luxStart = findViewById(R.id.lux_start);
        luxStop = findViewById(R.id.lux_stop);

        luxStart.setOnClickListener(view -> {
            if (luxSensor != null) {
                sensorManager.registerListener(listenLux, luxSensor,
                        SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                Toast.makeText(this,
                        "Нет датчика освещенности", Toast.LENGTH_SHORT).show();
            }
        });

        luxStop.setOnClickListener(view -> {
            sensorManager.unregisterListener(listenLux, luxSensor);
            luxValue.setText("0");
        });
    }

    SensorEventListener listenLux = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            luxValue.setText(String.valueOf((int) sensorEvent.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenLux, luxSensor);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                finish();
                break;
            case R.id.exit:
                finish();
                break;
            default:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}