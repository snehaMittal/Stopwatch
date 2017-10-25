package com.javahelps.stopwatch;

import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    long stopTime = 0;
    TextView tv_time ;
    Button start ;
    Button lap ;
    Button stop ;
    int lapNum = 1 ;
    boolean isRunning = false ;
    LinearLayout container;

    private Context mContext ;
    private Chronometer chronometer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = (Chronometer)findViewById(R.id.chronometer);
        mContext = this ;
//        tv_time = (TextView) findViewById(R.id.tv_time);
        start = (Button)findViewById(R.id.b_start);

        lap = (Button)findViewById(R.id.b_lap);
        stop = (Button)findViewById(R.id.b_stop);
        container = (LinearLayout) findViewById(R.id.container);
        stop.setOnClickListener(this);
        start.setOnClickListener(this);
        lap.setOnClickListener(this);
    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_time.setText(time );
            }
        });
    }

    @Override
    public void onClick(View v) {
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View addView = inflater.inflate(R.layout.row,null);
        TextView textView = (TextView) addView.findViewById(R.id.textContent);

        if (v == start){

            if (isRunning == true){

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Already running..Do you want to restart ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isRunning = true ;
                                lapNum = 1 ;
                                container.removeAllViews();
                                chronometer.setBase(SystemClock.elapsedRealtime() + stopTime);
                                chronometer.start();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert2 = alert.create() ;
                alert2.setTitle("Alert!!");
                alert2.show();
            }
            else {
                isRunning = true ;
                lapNum = 1 ;
                container.removeAllViews();
                chronometer.setBase(SystemClock.elapsedRealtime() + stopTime);
                chronometer.start();
            }

        }
        if (v == lap){

            textView.setText("LAP " +lapNum+ " : " + chronometer.getText());
            container.addView(addView);
            lapNum++ ;
        }
        if (v == stop){
//            chronometer.setBase(SystemClock.elapsedRealtime());
            isRunning = false ;
            stopTime = 0;
            chronometer.stop();
        }
    }
}
