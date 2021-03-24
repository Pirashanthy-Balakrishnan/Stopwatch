package com.pirasha.stopwatchnew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {

    //Declare member variables.
    private Chronometer stopwatch;
    private ToggleButton toggleButton;
    private ProgressBar progressBar;

    //Is the stop watch is running or not?
    private boolean running;

    //To calculate time difference between
    //start and pause the stopWatch.
    private long pauseOffset;

    //To count the progress value.
    private int progressValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign the views to member variable.
        stopwatch = findViewById(R.id.stopWatch);
        toggleButton = findViewById(R.id.toggleButton);
        progressBar = findViewById(R.id.progressBar);

        //Change the stopWatch format to actual clock format.
        stopwatch.setFormat("%s");

        //The runProgress() method will run code every second to check
        //whether the stopwatch is running, and, if it is, increment
        //the progress value and update the progress view.
        runProgress();

        //Customize the toggle button
        toggleButton.setText(getString(R.string.start));
        toggleButton.setTextOn(getString(R.string.pause));
        toggleButton.setTextOff(getString(R.string.start));

        //To check which method is selected by user. that start or pause.
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //Start the stopwatch time, when only click the start button by user. it'll
                //change the state to start.
                //If they click start button the isChecked value is true.
                if (isChecked){

                    //Start the stop watch time, when only click the start button
                    //not the activity staring time.
                    stopwatch.setBase(SystemClock.elapsedRealtime() - pauseOffset);

                    //Update the time in text view.
                    stopwatch.start();
                    running = true;
                }
                else {
                    //Pause the stopwatch.

                    //stop update the time value at only text view,
                    //but still run background.
                    stopwatch.stop();

                    //getBase method return the base value, when we start stopwatch,
                    //that we set the setBase value.
                    //pauseOffset get the value from current elapsedRealtime and
                    //subtract the stopwatch starting time.
                    pauseOffset = SystemClock.elapsedRealtime() - stopwatch.getBase();
                    running = false;
                }
            }
        });
    }

    private void runProgress() {
        final Handler handler = new Handler();

        //post method run the code immediately.
        handler.post(new Runnable() {
            @Override
            public void run() {

                //Assign the progressValue to progressBar
                //If the stopWatch is running, check the progress value
                //below the 60 seconds, then increment the progress value
                //otherwise set progressValue to zero, so the progress animation
                //start from beginning.
                progressBar.setProgress(progressValue);
                if (running){
                    if(progressValue < 60){
                        progressValue++;
                    }
                    else {
                        progressValue = 0;
                    }
                }

                //Post the code again with a delay of 1 second.
                handler.postDelayed(this,1000);
            }
        });
    }

    //When user click stop button the stopwatch will stop the running.
    public void stopStopwatch(View view){
        stopwatch.stop();

        //To move the initial time (zero).
        stopwatch.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;

        running = false;

        //change the progress animation starting position.
        progressValue = 0;

        //Change the state of stopwatch to start function.
        toggleButton.setChecked(false);
        toggleButton.setText(getString(R.string.start));

    }
}