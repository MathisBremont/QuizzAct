package com.example.quizzact.activities;

import com.example.quizzact.R;
import com.example.quizzact.audio.MusicService;

import androidx.appcompat.app.AppCompatActivity;

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


public class MainActivity extends AppCompatActivity {


    Button buttonPlay;
    Button buttonScore;
    Button buttonSettings;
    MediaPlayer sounds;
    HomeWatcher homeWatcher;

    String soundsOn ="ON";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.buttonPlay = findViewById(R.id.buttonPlay);
        this.buttonScore = findViewById(R.id.buttonScore);
        this.buttonSettings = findViewById(R.id.buttonSettings);

        this.sounds =MediaPlayer.create(this,R.raw.sound);

        Intent intent2 = getIntent();
        if(intent2.getStringExtra("buttonSounds")!=null){
            soundsOn =intent2.getStringExtra("buttonSounds");
        }



        //BIND Music Service
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        Intent intent = getIntent();

        //IMPORTANT -> Musique se lance dés le début
        if(intent.getAction()=="android.intent.action.MAIN"){
            startService(music);
        }else if(intent.getStringExtra("buttonMusic")=="ON"){
            startService(music);
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




        this.buttonPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonPlay.setEnabled(false);
                buttonScore.setEnabled(false);
                buttonSettings.setEnabled(false);
                if(soundsOn.equals("ON"))
                sounds.start();

                Intent intent = new Intent(MainActivity.this, QuizzActivity.class);
                if(getIntent().getStringExtra("buttonSounds")!=null){
                    intent.putExtra("buttonSounds",getIntent().getStringExtra("buttonSounds"));
                }
                if(getIntent().getStringExtra("buttonMusic")!=null){
                    intent.putExtra("buttonMusic",getIntent().getStringExtra("buttonMusic"));
                }
                startActivity(intent);
               finish();

            }
        });

        this.buttonSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonPlay.setEnabled(false);
                buttonScore.setEnabled(false);
                buttonSettings.setEnabled(false);

                Intent intent = new Intent(MainActivity.this, ParamsActivity.class);
                Intent intent2 = getIntent();
                if(intent2.getStringExtra("buttonSounds")!=null){

                    if(soundsOn.equals("ON")){
                        sounds.start();
                    }
                    intent.putExtra("buttonSounds",intent2.getStringExtra("buttonSounds"));
                }else{
                    sounds.start();
                }

                if(intent2.getStringExtra("buttonMusic")!=null){
                    intent.putExtra("buttonMusic",intent2.getStringExtra("buttonMusic"));
                }

                startActivity(intent);
                finish();
            }
        });

        this.buttonScore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                buttonPlay.setEnabled(false);
                buttonScore.setEnabled(false);
                buttonSettings.setEnabled(false);


                if(soundsOn.equals("ON"))
                sounds.start();

                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                if(getIntent().getStringExtra("buttonSounds")!=null){
                    intent.putExtra("buttonSounds",getIntent().getStringExtra("buttonSounds"));
                }
                if(getIntent().getStringExtra("buttonMusic")!=null){
                    intent.putExtra("buttonMusic",getIntent().getStringExtra("buttonMusic"));
                }
                startActivity(intent);
                finish();
            }
        });


    }





    private boolean mIsBound = false;
    private MusicService mServ;

    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
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
    public void onBackPressed(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        if (mServ != null) {
            if(intent.getStringExtra("buttonMusic")!=null){
                if(intent.getStringExtra("buttonMusic").equals("ON"))
                    mServ.resumeMusic();
            }else{
                mServ.resumeMusic();
            }
        }
        if(intent.getStringExtra("buttonSounds")!=null){
            soundsOn =intent.getStringExtra("buttonSounds");
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


