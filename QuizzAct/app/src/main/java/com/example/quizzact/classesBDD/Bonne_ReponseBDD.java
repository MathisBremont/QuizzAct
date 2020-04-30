package com.example.quizzact.classesBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizzact.BaseDeDonnees;
import com.example.quizzact.classes.Bonne_Reponse;

public class Bonne_ReponseBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "quizzAct.db";

    private static final String TABLE_BONNE_REPONSE = "BONNE_REPONSE";
    private static final String COL_ID_REP = "idRep";
    private static final int NUM_COL_ID_REP = 0;
    private static final String COL_ID_QUEST = "idQuest";
    private static final int NUM_COL_ID_QUEST = 1;


    private SQLiteDatabase bdd;
    private BaseDeDonnees baseDeDonnees;
    public Bonne_ReponseBDD(Context context){
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
    public long insertBonneReponse(Bonne_Reponse bonne_reponse){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé
        values.put(COL_ID_QUEST, bonne_reponse.getIdRep());
        values.put(COL_ID_REP, bonne_reponse.getIdQuest());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_BONNE_REPONSE, null, values);
    }
    public int updateBonneReponse(int idRep, int idQuest, Bonne_Reponse bonne_reponse){
        //La mise à jour d'une bonne réponse
        //on précise quel bonne réponse on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_ID_QUEST, bonne_reponse.getIdRep());
        values.put(COL_ID_REP, bonne_reponse.getIdQuest());
        return bdd.update(TABLE_BONNE_REPONSE, values, COL_ID_REP + " = " +idRep + COL_ID_QUEST + " = " + idQuest, null);
    }

    public int removeBonneReponseAvecID(int idRep, int idQuest){
        //Suppression d'une question de la BDD grâce à l'ID
        return bdd.delete(TABLE_BONNE_REPONSE, COL_ID_REP + " = " +idRep + COL_ID_QUEST + " = " + idQuest, null);
    }

    public Bonne_Reponse getBonneReponseAvecIDQuestion(int idQuest){
        //On récupère dans un Cursor la bonne réponse contenu dans la BDD
        //(ici on sélectionne la bonne_réponse grâce à l'id de la question)
        Cursor c = bdd.query(TABLE_BONNE_REPONSE, new String[] {COL_ID_REP, COL_ID_QUEST},

                //Il y aura peut etre une erreur ici, car on compare un String avec un int
                COL_ID_QUEST + " LIKE \"" + idQuest +"\"", null, null, null, null);
        return cursorToBonneReponse(c);
    }
    //Cette méthode permet de convertir un cursor en un livre
    private Bonne_Reponse cursorToBonneReponse(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé une Bonne réponse
        Bonne_Reponse bonne_rep = new Bonne_Reponse();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        bonne_rep.setIdRep(c.getInt(NUM_COL_ID_REP));
        bonne_rep.setIdQuest(c.getInt(NUM_COL_ID_QUEST));
        //On ferme le cursor
        c.close();
        //On retourne la bonne réponse
        return bonne_rep;
    }
}
