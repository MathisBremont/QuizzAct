package com.example.quizzact.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizzact.R;
import com.example.quizzact.audio.MusicService;
import com.example.quizzact.classes.Bonne_Reponse;
import com.example.quizzact.classes.Question;
import com.example.quizzact.classes.Reponse;
import com.example.quizzact.classes.Score;
import com.example.quizzact.classes.Theme;
import com.example.quizzact.classesBDD.Bonne_ReponseBDD;
import com.example.quizzact.classesBDD.QuestionBDD;
import com.example.quizzact.classesBDD.ReponseBDD;
import com.example.quizzact.classesBDD.ScoreBDD;
import com.example.quizzact.classesBDD.ThemeBDD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class QuizzActivity extends Activity {

    TextView tvNumQuestion;
    TextView tvQuestion;
    TextView tvTheme;
    Button reponse1;
    Button reponse2;
    Button reponse3;
    Button reponse4;
    int numQuestion;
    int score=0;
    ArrayList<Integer> listeQuestions = new ArrayList<Integer>();
    MediaPlayer sound;
    String soundsOn;
    boolean backPressed=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        tvNumQuestion = findViewById(R.id.numQuestion);
        tvQuestion = findViewById(R.id.question);
        tvTheme = findViewById(R.id.theme);
        reponse1 = findViewById(R.id.reponse1);
        reponse2 = findViewById(R.id.reponse2);
        reponse3 = findViewById(R.id.reponse3);
        reponse4 = findViewById(R.id.reponse4);
        sound = MediaPlayer.create(this,R.raw.sound);

        QuestionBDD questionBDD = new QuestionBDD(this);
        ReponseBDD reponseBDD = new ReponseBDD(this);
        Bonne_ReponseBDD bonne_reponseBDD = new Bonne_ReponseBDD(QuizzActivity.this);
        ThemeBDD themeBDD = new ThemeBDD(this);

        themeBDD.open();
        bonne_reponseBDD.open();
        reponseBDD.open();
        questionBDD.open();

        Theme theme;
        Question question;
        Reponse[] rep = new Reponse[4];
        Bonne_Reponse bonne_reponse = new Bonne_Reponse();


        doBindService();
        final Intent music = new Intent();
        music.setClass(this,MusicService.class);

        if(getIntent().getStringExtra("buttonSounds")!=null){
            if(getIntent().getStringExtra("buttonSounds").equals("ON")){
                soundsOn="ON";
            }else{
                soundsOn="OFF";
            }
        }else{
            soundsOn="ON";
        }






        Intent intent = getIntent();
        numQuestion=intent.getIntExtra("numQuestion",1);

        if(intent.getIntegerArrayListExtra("listeQuestions")!=null){
            listeQuestions=getIntent().getIntegerArrayListExtra("listeQuestions");
        }
        score=intent.getIntExtra("score",0);

        if(getIntent().getBooleanExtra("backPressed",false)){
            System.out.println("SAOT");
            Intent intent2 = new Intent(QuizzActivity.this,MainActivity.class);

            finish();
            overridePendingTransition(0, 0);
            startActivity(intent2);
        }



        if(numQuestion!=6) {

            int nombreAleatoire = 1 + (int) (Math.random() * ((questionBDD.countLignes() - 1) + 1));
            while (listeQuestions.contains(nombreAleatoire)) {
                nombreAleatoire = 1 + (int) (Math.random() * ((questionBDD.countLignes() - 1) + 1));
            }
            listeQuestions.add(nombreAleatoire);

            tvNumQuestion.setText("Question "+ numQuestion +"/5");


            question = questionBDD.getQuestionAvecID(nombreAleatoire);
            bonne_reponse = bonne_reponseBDD.getBonneReponseAvecIDQuestion(nombreAleatoire);
            theme= themeBDD.getThemeWithID(question.getIdTheme());

            if (question != null) {
                tvQuestion.setText(question.getLibQuest());
                tvTheme.setText(theme.getLibTheme());
                tvQuestion.setText(question.getLibQuest());

                rep = reponseBDD.getReponsesLieAQuestion(questionBDD.getQuestionAvecID(nombreAleatoire).getId());

                Collections.shuffle(Arrays.asList(rep));
                reponse1.setText(rep[0].getLibRep());
                reponse2.setText(rep[1].getLibRep());
                reponse3.setText(rep[2].getLibRep());
                reponse4.setText(rep[3].getLibRep());

            }
        }else{

            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            ScoreBDD scoreBDD = new ScoreBDD(this);
            scoreBDD.open();

            Score scoreObject = new Score(scoreBDD.countLignes()+1,score,currentDate);
            scoreBDD.insertScore(scoreObject);



            Intent intentResult = new Intent(QuizzActivity.this,ResultActivity.class);
            intentResult.putExtra("buttonSounds",soundsOn);
            intentResult.putExtra("score",score);
            if(getIntent().getStringExtra("buttonMusic")!=null){
                intentResult.putExtra("buttonMusic",getIntent().getStringExtra("buttonMusic"));
            }
            finish();
            startActivity(intentResult);
        }


            final Reponse rep1 = reponseBDD.getReponseAvecLibRep(reponse1.getText().toString());
            final Reponse rep2 = reponseBDD.getReponseAvecLibRep(reponse2.getText().toString());
            final Reponse rep3 = reponseBDD.getReponseAvecLibRep(reponse3.getText().toString());
            final Reponse rep4 = reponseBDD.getReponseAvecLibRep(reponse4.getText().toString());



        reponse1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(soundsOn.equals("ON")){
                        sound.start();
                    }



                    numQuestion+=1;


                    Bonne_ReponseBDD bonne_reponseBDD = new Bonne_ReponseBDD(QuizzActivity.this);
                    bonne_reponseBDD.open();
                    QuestionBDD qbdd = new QuestionBDD(QuizzActivity.this);
                    qbdd.open();
                    Question q = qbdd.getQuestionAvecLib(tvQuestion.getText().toString());
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep1.getIdRep()) {
                        score+=1;
                        reponse1.setBackgroundColor(Color.GREEN);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep2.getIdRep()) {
                        reponse1.setBackgroundColor(Color.GRAY);
                        reponse2.setBackgroundColor(Color.GREEN);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep3.getIdRep()) {
                        reponse1.setBackgroundColor(Color.GRAY);
                        reponse3.setBackgroundColor(Color.GREEN);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep4.getIdRep()) {
                        reponse1.setBackgroundColor(Color.GRAY);
                        reponse4.setBackgroundColor(Color.GREEN);
                    }
                    reponse1.setEnabled(false);
                    reponse2.setEnabled(false);
                    reponse3.setEnabled(false);
                    reponse4.setEnabled(false);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        public void run()
                        {
                            Intent intent = new Intent(QuizzActivity.this, QuizzActivity.class);
                            intent.putExtra("listeQuestions", listeQuestions);
                            intent.putExtra("numQuestion", numQuestion);
                            intent.putExtra("score",score);
                            intent.putExtra("buttonSounds",soundsOn);
                            System.out.println("SALUT  :  "+backPressed);
                            intent.putExtra("backPressed",backPressed);
                            if(getIntent().getStringExtra("buttonMusic")!=null){
                                intent.putExtra("buttonMusic",getIntent().getStringExtra("buttonMusic"));
                            }


                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }, 1500L);

                }
            });
            reponse2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numQuestion+=1;

                    if(soundsOn.equals("ON")){
                        sound.start();
                    }


                    Bonne_ReponseBDD bonne_reponseBDD = new Bonne_ReponseBDD(QuizzActivity.this);
                    bonne_reponseBDD.open();
                    QuestionBDD qbdd = new QuestionBDD(QuizzActivity.this);
                    qbdd.open();
                    Question q = qbdd.getQuestionAvecLib(tvQuestion.getText().toString());

                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep1.getIdRep()) {
                        reponse1.setBackgroundColor(Color.GREEN);
                        reponse2.setBackgroundColor(Color.GRAY);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep2.getIdRep()) {
                        reponse2.setBackgroundColor(Color.GREEN);
                        score+=1;
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep3.getIdRep()) {
                        reponse2.setBackgroundColor(Color.GRAY);
                        reponse3.setBackgroundColor(Color.GREEN);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep4.getIdRep()) {
                        reponse2.setBackgroundColor(Color.GRAY);
                        reponse4.setBackgroundColor(Color.GREEN);
                    }

                    reponse1.setEnabled(false);
                    reponse2.setEnabled(false);
                    reponse3.setEnabled(false);
                    reponse4.setEnabled(false);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        public void run()
                        {
                            Intent intent = new Intent(QuizzActivity.this, QuizzActivity.class);
                            intent.putExtra("listeQuestions", listeQuestions);
                            intent.putExtra("numQuestion", numQuestion);
                            intent.putExtra("score",score);
                            intent.putExtra("backPressed",backPressed);
                            intent.putExtra("buttonSounds",soundsOn);
                            if(getIntent().getStringExtra("buttonMusic")!=null){
                                intent.putExtra("buttonMusic",getIntent().getStringExtra("buttonMusic"));
                            }


                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }, 1500L);
                }

            });
            reponse3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numQuestion+=1;

                    if(soundsOn.equals("ON")){
                        sound.start();
                    }



                    Bonne_ReponseBDD bonne_reponseBDD = new Bonne_ReponseBDD(QuizzActivity.this);
                    bonne_reponseBDD.open();
                    QuestionBDD qbdd = new QuestionBDD(QuizzActivity.this);
                    qbdd.open();
                    Question q = qbdd.getQuestionAvecLib(tvQuestion.getText().toString());
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep1.getIdRep()) {
                        reponse1.setBackgroundColor(Color.GREEN);
                        reponse3.setBackgroundColor(Color.GRAY);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep2.getIdRep()) {
                        reponse2.setBackgroundColor(Color.GREEN);
                        reponse3.setBackgroundColor(Color.GRAY);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep3.getIdRep()) {
                        reponse3.setBackgroundColor(Color.GREEN);
                        score+=1;
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep4.getIdRep()) {
                        reponse3.setBackgroundColor(Color.GRAY);
                        reponse4.setBackgroundColor(Color.GREEN);
                    }

                    reponse1.setEnabled(false);
                    reponse2.setEnabled(false);
                    reponse3.setEnabled(false);
                    reponse4.setEnabled(false);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        public void run()
                        {
                            Intent intent = new Intent(QuizzActivity.this, QuizzActivity.class);
                            intent.putExtra("listeQuestions", listeQuestions);
                            intent.putExtra("numQuestion", numQuestion);
                            intent.putExtra("score",score);
                            intent.putExtra("buttonSounds",soundsOn);
                            intent.putExtra("backPressed",backPressed);
                            if(getIntent().getStringExtra("buttonMusic")!=null){
                                intent.putExtra("buttonMusic",getIntent().getStringExtra("buttonMusic"));
                            }



                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }, 1500L);


                }
            });
            reponse4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numQuestion+=1;

                    if(soundsOn.equals("ON")){
                        sound.start();
                    }



                    Bonne_ReponseBDD bonne_reponseBDD = new Bonne_ReponseBDD(QuizzActivity.this);
                    bonne_reponseBDD.open();
                    QuestionBDD qbdd = new QuestionBDD(QuizzActivity.this);
                    qbdd.open();
                    Question q = qbdd.getQuestionAvecLib(tvQuestion.getText().toString());
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep1.getIdRep()) {
                        reponse1.setBackgroundColor(Color.GREEN);
                        reponse4.setBackgroundColor(Color.GRAY);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep2.getIdRep()) {
                        reponse2.setBackgroundColor(Color.GREEN);
                        reponse4.setBackgroundColor(Color.GRAY);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep3.getIdRep()) {
                        reponse3.setBackgroundColor(Color.GREEN);
                        reponse4.setBackgroundColor(Color.GRAY);
                    }
                    if (bonne_reponseBDD.getBonneReponseAvecIDQuestion(q.getId()).getIdRep() == rep4.getIdRep()) {
                        reponse4.setBackgroundColor(Color.GREEN);
                        score+=1;
                    }

                    reponse1.setEnabled(false);
                    reponse2.setEnabled(false);
                    reponse3.setEnabled(false);
                    reponse4.setEnabled(false);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        public void run()
                        {
                            Intent intent = new Intent(QuizzActivity.this, QuizzActivity.class);
                            intent.putExtra("listeQuestions", listeQuestions);
                            intent.putExtra("numQuestion", numQuestion);
                            intent.putExtra("score",score);
                            intent.putExtra("buttonSounds",soundsOn);
                            intent.putExtra("backPressed",backPressed);
                            if(getIntent().getStringExtra("buttonMusic")!=null){
                                intent.putExtra("buttonMusic",getIntent().getStringExtra("buttonMusic"));
                            }


                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }, 1500L);
                }
            });

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
        if(intent.getStringExtra("buttonSounds")!=null){
            soundsOn=intent.getStringExtra("buttonSounds");
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(QuizzActivity.this,MainActivity.class);
        if(getIntent().getStringExtra("buttonMusic")!=null){
            intent.putExtra("buttonMusic",getIntent().getStringExtra("buttonMusic"));
        }
        if(getIntent().getStringExtra("buttonSounds")!=null){
            intent.putExtra("buttonSounds",getIntent().getStringExtra("buttonSounds"));
        }
        finish();
        startActivity(intent);
        backPressed=true;
        super.onBackPressed();
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
