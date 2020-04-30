package com.example.quizzact.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzact.R;
import com.example.quizzact.audio.MusicService;
import com.example.quizzact.classesBDD.ScoreBDD;

public class ParamsActivity extends AppCompatActivity /*implements Parcelable */{

    Button buttonMusic;
    Button buttonSounds;
    Button buttonCache;
    MediaPlayer mediaplayer;
    HomeWatcher homeWatcher;
    ScoreBDD scoreBDD = new ScoreBDD(this);
    private boolean mIsBound = false;
    private MusicService mServ;
    public static final String KEY_BUNDLE_BUTTON_MUSIC = "buttonMusic";
    public static final String KEY_BUNDLE_BUTTON_SOUNDS = "buttonSounds";
    public String test;

    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params );
        this.buttonMusic = (Button) findViewById(R.id.buttonMusic);
        this.buttonSounds = findViewById(R.id.buttonSounds);
        this.buttonCache = findViewById(R.id.buttonCache);
        this.mediaplayer=MediaPlayer.create(this,R.raw.sound);

        doBindService();
        final Intent music = new Intent();
        music.setClass(this,MusicService.class);





        Intent intent=getIntent();
       if(intent.getStringExtra("buttonMusic")!=null){

            buttonMusic.setText(intent.getStringExtra("buttonMusic"));

        }
        if(intent.getStringExtra("buttonSounds")!=null){

            buttonSounds.setText(intent.getStringExtra("buttonSounds"));

        }

        scoreBDD.open();
        if(scoreBDD.countLignes()!=0){
            buttonCache.setVisibility(View.VISIBLE);
        }else{
            buttonCache.setVisibility(View.INVISIBLE);
        }


        //Start HomeWatcher
        homeWatcher = new HomeWatcher(this);
        homeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });
        homeWatcher.startWatch();

        this.buttonMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonSounds.getText().toString().equals("ON")){
                    mediaplayer.start();
                }

                if(String.valueOf(buttonMusic.getText()).equals("ON")){
                    buttonMusic.setText("OFF");
                    mServ.pauseMusic();
                }else {
                    buttonMusic.setText("ON");
                    mServ.resumeMusic();
                }

            }
        });

       this.buttonSounds.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               if(buttonSounds.getText().toString().equals("ON")){
                   buttonSounds.setText("OFF");
               }else {
                   mediaplayer.start();
                   buttonSounds.setText("ON");
               }
           }
       });


        this.buttonCache.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(buttonSounds.getText().toString().equals("ON")){
                    mediaplayer.start();
                }
                scoreBDD.removeScore();
                buttonCache.setVisibility(View.INVISIBLE);
            }

        });



    }



    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ParamsActivity.this, MainActivity.class);
        intent.putExtra("buttonMusic", buttonMusic.getText().toString());
        intent.putExtra("buttonSounds",buttonSounds.getText().toString());
        finish();
        startActivity(intent);
        super.onBackPressed();

    }



    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(KEY_BUNDLE_BUTTON_MUSIC, String.valueOf(buttonMusic.getText()));
        savedInstanceState.putString(KEY_BUNDLE_BUTTON_SOUNDS,String.valueOf(buttonSounds.getText()));
        onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        buttonMusic.setText(savedInstanceState.getString(KEY_BUNDLE_BUTTON_MUSIC));
        buttonSounds.setText(savedInstanceState.getString(KEY_BUNDLE_BUTTON_SOUNDS));
    }
    @Override
    protected void onResume() {
        super.onResume();

        if ((mServ != null)&&(buttonMusic.getText().toString().equals("ON"))) {
            mServ.resumeMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Detect idle screen
        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //UNBIND music service
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }



}
