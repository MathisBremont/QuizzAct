package com.example.quizzact.activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.MatrixCursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.quizzact.R;
import com.example.quizzact.audio.MusicService;
import com.example.quizzact.classes.Theme;
import com.example.quizzact.classesBDD.ScoreBDD;
import com.example.quizzact.classesBDD.ThemeBDD;

public class ScoreActivity extends AppCompatActivity {



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);



        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        Intent intent = getIntent();
        System.out.println(intent);





        TableLayout table = (TableLayout) findViewById(R.id.idTable);
        TableRow row; // ligne
        TextView tv1,tv2; //Case


        ScoreBDD scoreBDD= new ScoreBDD(this);
        scoreBDD.open();



        tv1=new TextView(this);
        tv2 = new TextView(this);

        row = new TableRow(this);
        tv1.setText("DATE");
        tv1.setTextSize(25);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv1.setBackground(getDrawable(R.drawable.table_cell));
        tv1.setTextColor(ContextCompat.getColor(this, R.color.white));

        tv2.setText("SCORE");
        tv2.setTextSize(25);
        tv2.setGravity(Gravity.CENTER);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        tv2.setBackground(getDrawable(R.drawable.table_cell));
        tv2.setPadding(0,0,50,0);
        tv2.setTextColor(ContextCompat.getColor(this, R.color.white));


        row.addView(tv1);
        row.addView(tv2);
        row.setBackground(getDrawable(R.drawable.table_row));
        table.addView(row);


        if(scoreBDD.countLignes()!=0){

            for(int i=1;i<=scoreBDD.countLignes();i++) {

                tv1=new TextView(this);
                tv2 = new TextView(this);
                row = new TableRow(this);
                if(scoreBDD.getScoreWithID(scoreBDD.countLignes()-i+1)!=null){
                    tv1.setText(String.valueOf(scoreBDD.getScoreWithID(scoreBDD.countLignes()-i+1).getDate()));
                    tv2.setText(String.valueOf(scoreBDD.getScoreWithID(scoreBDD.countLignes()-i+1).getScore()) + "/5");
                }

                tv1.setGravity(Gravity.RIGHT); // centrage dans la cellule
                tv1.setTextSize(20);
                tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv1.setTextColor(ContextCompat.getColor(this, R.color.white));
                tv1.setBackground(getDrawable(R.drawable.table_cell));



                tv2.setGravity(Gravity.RIGHT); // centrage dans la cellule
                tv2.setTextSize(20);
                tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv2.setTextColor(ContextCompat.getColor(this, R.color.white));
                tv2.setPadding(0,0,30,0);
                tv2.setBackground(getDrawable(R.drawable.table_cell));




                row.addView(tv1);
                row.addView(tv2);
                row.setBackground(getDrawable(R.drawable.table_row));

                /* row.setBackgroundColor(R.color.Black);*/

                row.setPadding(0,40,0,40);
                table.addView(row);
            }
        }



        scoreBDD.close();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
        if(getIntent().getStringExtra("buttonMusic")!=null)
            intent.putExtra("buttonMusic",getIntent().getStringExtra("buttonMusic"));
        if(getIntent().getStringExtra("buttonSounds")!=null)
            intent.putExtra("buttonSounds",getIntent().getStringExtra("buttonSounds"));
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public void onResume() {
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

}