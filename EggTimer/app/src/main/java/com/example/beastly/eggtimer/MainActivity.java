package com.example.beastly.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public void start (View view) {
        if (!timerStarted) {

            timerStarted = true;
            seekBar.setEnabled(false);
            timer = new CountDownTimer(timeLeft, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    minutesLeft = (int) (millisUntilFinished / 1000) / 60;
                    secondsLeft = (int) (millisUntilFinished / 1000) % 60;
                    if (secondsLeft < 10) {
                        timeLeftTextView.setText(minutesLeft + ":0" + secondsLeft);
                    } else {
                        timeLeftTextView.setText(minutesLeft + ":" + secondsLeft);
                    }
                }

                @Override
                public void onFinish() {
                    ring.start();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            voiceSound.start();
                        }
                    }, 1000);
                    timerStarted = false;
                    btn.setText("Start");
                    timeLeftTextView.setText("END");
                    seekBar.setEnabled(true);
                }

            }.start();
            btn.setText("Stop");

        } else {
            timer.cancel();
            btn.setText("Start");
            seekBar.setEnabled(true);
            timerStarted = false;
        }
    }

    MediaPlayer ring;
    MediaPlayer voiceSound;
    SeekBar seekBar;
    TextView timeLeftTextView;
    long timeLeft;
    int minutesLeft;
    int secondsLeft;
    Button btn;
    boolean timerStarted = false;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        timeLeftTextView = (TextView) findViewById(R.id.timeLeft);
        btn = (Button) findViewById(R.id.button2);
        ring = MediaPlayer.create(this, R.raw.ringsound);
        voiceSound = MediaPlayer.create(this, R.raw.voicesound);

        seekBar.setMax(600000);
        seekBar.setProgress(360000);
        timeLeft = 360000;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeLeft = progress;
                minutesLeft = (int) (timeLeft/ 1000) / 60;
                secondsLeft = (int) (timeLeft/ 1000) % 60;
                if (secondsLeft < 10){
                    timeLeftTextView.setText(minutesLeft + ":0" + secondsLeft);
                } else {
                    timeLeftTextView.setText(minutesLeft + ":" + secondsLeft);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
