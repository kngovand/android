package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public void onReset(View view) {

        super.onDestroy();

        Button startButton = findViewById(R.id.startButton);
        Button resetButton = findViewById(R.id.resetButton);
        SeekBar seekBar = findViewById(R.id.seekBar);

        // reset seekbar, hide reset button, show start button
        seekBar.setEnabled(true);
        resetButton.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // user defined time on seekbar
        final int[] timeInSeconds = {0};

        final Button startButton = findViewById(R.id.startButton);
        final Button resetButton = findViewById(R.id.resetButton);
        final SeekBar seekBar = findViewById(R.id.seekBar);
        final TextView text = findViewById(R.id.textView);

        // max 1200 seconds
        seekBar.setMax(1200);

        // hide reset button
        resetButton.setVisibility(View.GONE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("tag", "" + progress);

                // time conversions
                int minutes = (progress % 3600) / 60;
                int seconds = progress % 60;

                String time = String.format("%02d:%02d", minutes, seconds);

                text.setText(time);
                timeInSeconds[0] = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        startButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                // disable seekbar
                seekBar.setEnabled(false);
                // hide start button, show reset button
                startButton.setVisibility(View.GONE);

                // grab time in seconds from seekbar
                int time = timeInSeconds[0];

                new CountDownTimer(time*1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        int seconds = (int) millisUntilFinished / 1000;
                        int minutes = (seconds % 3600) / 60;

                        Log.i("Seconds", "" + seconds);

                        // 00:00 format
                        text.setText(String.format("%02d:%02d", minutes, seconds % 60));
                    }

                    @Override
                    public void onFinish() {
                        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tutu);
                        mediaPlayer.start();

                        text.setText("Wow!");

                        resetButton.setVisibility(View.VISIBLE);
                    }
                }.start();
            }

        });
    }
}
