package com.example.quizzact.classesBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizzact.BaseDeDonnees;
import com.example.quizzact.classes.Question;

public class QuestionBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "quizzAct.db";

    private static final String TABLE_QUESTION = "QUESTION";
    private static final String COL_ID_QUEST = "idQuest";
    private static final int NUM_COL_ID_QUEST = 0;
    private static final String COL_LIB_QUEST = "libQuest";
    private static final int NUM_COL_LIB_QUEST = 1;
    private static final String COL_ID_THEME = "idTheme";
    private static final int NUM_COL_ID_THEME = 2;
    private SQLiteDatabase bdd;
    private BaseDeDonnees baseDeDonnees;

    public QuestionBDD(Context context){

        baseDeDonnees = new BaseDeDonnees(context, NOM_BDD, null, VERSION_BDD);
    }
    public void open(){

        bdd = baseDeDonnees.getWritableDatabase();
    }
    public void close(){

        bdd.close();
    }
    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertQuestion(Question question){

        ContentValues values = new ContentValues();

        values.put(COL_LIB_QUEST, question.getLibQuest());
        values.put(COL_ID_THEME, question.getIdTheme());

        return bdd.insert(TABLE_QUESTION, null, values);
    }
    public int updateQuestion(int id, Question question){

        ContentValues values = new ContentValues();
        values.put(COL_LIB_QUEST, question.getLibQuest());
        values.put(COL_ID_THEME, question.getIdTheme());
        return bdd.update(TABLE_QUESTION, values, COL_ID_QUEST + " = " +id, null);
    }

    public int removeQuestionAvecID(int id){

        return bdd.delete(TABLE_QUESTION, COL_ID_QUEST + " = " +id, null);
    }
    public int removeQuestionAvecLib(String lib){

        return bdd.delete(TABLE_QUESTION, COL_LIB_QUEST + " LIKE \"" +lib+"\"", null);
    }
    public Question getQuestionAvecLib(String libQuest){

        Cursor c = bdd.query(TABLE_QUESTION, new String[] {COL_ID_QUEST, COL_LIB_QUEST, COL_ID_THEME},
                COL_LIB_QUEST + " LIKE \"" + libQuest +"\"", null, null, null, null);
        return cursorToQuestion(c);
    }
    public Question getQuestionAvecID(int id){

        Cursor c = bdd.query(TABLE_QUESTION, new String[] {COL_ID_QUEST, COL_LIB_QUEST, COL_ID_THEME},
                COL_ID_QUEST + " = \"" + id +"\"", null, null, null, null);
        return cursorToQuestion(c);
    }


    public int countLignes(){
        int nbLignes=0;
        nbLignes=(int) DatabaseUtils.queryNumEntries(bdd,TABLE_QUESTION);
        return nbLignes;
    }

    private Question cursorToQuestion(Cursor c){

        if (c.getCount() == 0)
            return null;

        c.moveToFirst();

        Question question = new Question();

        question.setId(c.getInt(NUM_COL_ID_QUEST));
        question.setLibQuest(c.getString(NUM_COL_LIB_QUEST));
        question.setIdTheme(c.getInt(NUM_COL_ID_THEME));

        c.close();

        return question;
    }
}
