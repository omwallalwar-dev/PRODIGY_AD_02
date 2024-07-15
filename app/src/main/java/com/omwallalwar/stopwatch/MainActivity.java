package com.omwallalwar.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textViewHours, textViewMinutes, textViewSeconds, textViewMillis;
    MaterialButton reset, start, stop;
    int seconds, minutes, hours, milliSeconds;
    long millisecondTime, startTime, timeBuff, updateTime = 0L;
    Handler handler;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            millisecondTime = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + millisecondTime;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            hours = minutes / 60;
            minutes = minutes % 60;
            seconds = seconds % 60;
            milliSeconds = (int) (updateTime % 1000);

            textViewHours.setText(String.format(Locale.getDefault(), "%02d", hours));
            textViewMinutes.setText(String.format(Locale.getDefault(), "%02d", minutes));
            textViewSeconds.setText(String.format(Locale.getDefault(), "%02d", seconds));
            textViewMillis.setText(String.format(Locale.getDefault(), "%03d", milliSeconds));
            handler.postDelayed(this, 50); // Update every 10 milliseconds
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHours = findViewById(R.id.textViewHours);
        textViewMinutes = findViewById(R.id.textViewMinutes);
        textViewSeconds = findViewById(R.id.textViewSeconds);
        textViewMillis = findViewById(R.id.textViewMillis);
        reset = findViewById(R.id.reset);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        handler = new Handler(Looper.getMainLooper());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeBuff += millisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                millisecondTime = 0L;
                startTime = 0L;
                timeBuff = 0L;
                updateTime = 0L;
                seconds = 0;
                minutes = 0;
                hours = 0;
                milliSeconds = 0;
                textViewHours.setText("00");
                textViewMinutes.setText("00");
                textViewSeconds.setText("00");
                textViewMillis.setText("000");
                reset.setEnabled(false);
            }
        });

        textViewHours.setText("00");
        textViewMinutes.setText("00");
        textViewSeconds.setText("00");
        textViewMillis.setText("000");
    }
}
