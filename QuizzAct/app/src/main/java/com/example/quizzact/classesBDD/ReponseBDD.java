package com.example.quizzact.classesBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizzact.BaseDeDonnees;
import com.example.quizzact.classes.Reponse;

public class ReponseBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "quizzAct.db";

    private static final String TABLE_REPONSE = "REPONSE";
    private static final String COL_ID_REP = "idRep";
    private static final int NUM_COL_ID_REP = 0;
    private static final String COL_ID_QUEST = "idQuest";
    private static final int NUM_COL_ID_QUEST = 1;
    private static final String COL_LIB_REP = "libRep";
    private static final int NUM_COL_LIB_REP = 2;

    private SQLiteDatabase bdd;
    private BaseDeDonnees baseDeDonnees;

    public ReponseBDD(Context context){
        //On crée la BDD et sa table
        baseDeDonnees = new BaseDeDonnees(context, NOM_BDD, null, VERSION_BDD);
    }
    public void open(){
        //on ouvre la BDD en écriture
        bdd = baseDeDonnees.getWritableDatabase();
    }
    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }
    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertReponse(Reponse reponse){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé
        values.put(COL_ID_QUEST, reponse.getIdQuest());
        values.put(COL_LIB_REP, reponse.getLibRep());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_REPONSE, null, values);
    }
    public int updateReponse(int id, Reponse reponse){
        //La mise à jour d'une reponse
        //on précise quelle reponse on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_ID_QUEST, reponse.getIdQuest());
        values.put(COL_LIB_REP, reponse.getLibRep());
        return bdd.update(TABLE_REPONSE, values, COL_ID_REP + " = " +id, null);
    }

    public int removeReponseAvecIdReponse(int id){
        //Suppression d'une question de la BDD grâce à l'ID
        return bdd.delete(TABLE_REPONSE, COL_ID_REP + " = " +id, null);
    }
    public Reponse getReponseAvecLibRep(String libRep){
        //On récupère dans un Cursor les valeurs correspondant à une reponse contenu dans la BDD
        //(ici on sélectionne la reponse grâce à son titre)
        Cursor c = bdd.query(TABLE_REPONSE, new String[] {COL_ID_REP, COL_ID_QUEST, COL_LIB_REP},
                COL_LIB_REP + " LIKE \"" + libRep +"\"", null, null, null, null);
        return cursorToReponse(c);
    }

    public Reponse getReponseAvecID(int id){
        //On récupère dans un Cursor les valeurs correspondant à une reponse contenu dans la BDD
        //(ici on sélectionne la reponse grâce à son titre)
        Cursor c = bdd.query(TABLE_REPONSE, new String[] {COL_ID_REP, COL_ID_QUEST, COL_LIB_REP},
                COL_ID_REP + " LIKE \"" + id +"\"", null, null, null, null);
        return cursorToReponse(c);
    }

    private Reponse cursorToReponse(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Reponse reponse = new Reponse();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        reponse.setIdRep(c.getInt(NUM_COL_ID_REP));
        reponse.setIdQuest(c.getInt(NUM_COL_ID_QUEST));
        reponse.setLibRep(c.getString(NUM_COL_LIB_REP));
        //On ferme le cursor
        c.close();
        //On retourne la réponse
        return reponse;
    }

    public Reponse[] getReponsesLieAQuestion(int data){
        Reponse[] rep= new Reponse[4];
        String res = "select idRep from reponse where idQuest="+ data;
        Cursor c = bdd.query(TABLE_REPONSE, new String[] {COL_ID_REP, COL_ID_QUEST, COL_LIB_REP},
                COL_ID_QUEST + " LIKE \"" + data +"\"",null, null, null, null);
        if(c.getCount()==0){
            return null;
        }
        int i=0;
        c.moveToFirst();
        while(!c.isLast()){
            Reponse reponse = new Reponse();

            reponse.setIdRep(c.getInt(NUM_COL_ID_REP));
            reponse.setIdQuest(c.getInt(NUM_COL_ID_QUEST));
            reponse.setLibRep(c.getString(NUM_COL_LIB_REP));

            rep[i]=reponse;
            c.moveToNext();
            i++;
        }
        Reponse reponse = new Reponse();

        reponse.setIdRep(c.getInt(NUM_COL_ID_REP));
        reponse.setIdQuest(c.getInt(NUM_COL_ID_QUEST));
        reponse.setLibRep(c.getString(NUM_COL_LIB_REP));

        rep[i]=reponse;
        c.moveToNext();
        c.close();
        return rep;
    }

    public int countLignes(){
        int nbLignes=0;
        nbLignes=(int) DatabaseUtils.queryNumEntries(bdd,TABLE_REPONSE);
        return nbLignes;
    }




}
