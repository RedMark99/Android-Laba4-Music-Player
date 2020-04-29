package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicActivity extends Activity {
    TextView nameMusicTV;
    TextView authorMusicTV;
    ImageView imageViewBtnPlayAndStop;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    SeekBar volumeSeekBar;


    boolean check = false;



    Handler handlerACT;
    Runnable runnable;
    //
    int totalTime;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    SeekBar positionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        nameMusicTV = findViewById(R.id.nameMusic);
        authorMusicTV = findViewById(R.id.authorMusic);
        imageViewBtnPlayAndStop = findViewById(R.id.playPauseBtn);
        elapsedTimeLabel = (TextView) findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = (TextView) findViewById(R.id.remainingTimeLabel);

        //progress
        handlerACT = new Handler();


        // volume
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        // Скопирована
        volumeSeekBar.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        volumeSeekBar.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));

        //Тут конец
        /*int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumeSeekBar.setProgress(maxVolume);*/


        Intent intent = getIntent();

        if (intent != null){
            String nameSong = intent.getStringExtra("nameMusic");
            nameMusicTV.setText(nameSong.replace("_", " "));
            authorMusicTV.setText("Автор: " + intent.getStringExtra("nameAuthor"));
            mediaPlayer = MediaPlayer.create(getApplicationContext(),
                    getResources().getIdentifier(nameSong, "raw", getPackageName()));

            // ----------------------------------
            totalTime = mediaPlayer.getDuration();
            // ----------------------------------

        }

        //----

        positionBar = (SeekBar) findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mediaPlayer.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        // Thread (Update positionBar & timeLabel)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                        if(check == false)
                        {
                            Message msg = new Message();
                            msg.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {}
                }
            }
        }).start();


        //----


        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                imageViewBtnPlayAndStop.setImageResource(R.drawable.play);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime-currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);
        }
    };



    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }



    public void playAndPause(View view) {
        setStatusPalyer();
    }


    private void setStatusPalyer(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            imageViewBtnPlayAndStop.setImageResource(R.drawable.play);
        }
        else{
            mediaPlayer.start();
            imageViewBtnPlayAndStop.setImageResource(R.drawable.stop);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        check = true;
        mediaPlayer.release();
        handlerACT.removeCallbacks(runnable);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mediaPlayer.pause();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }


        return true;
    }
}
