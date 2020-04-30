package com.example.quizzact.classesBDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizzact.BaseDeDonnees;
import com.example.quizzact.classes.Theme;

public class ThemeBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "quizzAct.db";
    private static final String TABLE_THEME = "THEME";
    private static final String ID_THEME= "idTheme";
    private static final int NUM_IDTHEME = 0;
    private static final String LIB_THEME = "libTheme";
    private static final int NUM_LIBTHEME = 1;


    private SQLiteDatabase bdd;
    private BaseDeDonnees baseDeDonnees;
    public ThemeBDD(Context context){
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
    public long insertTheme(Theme theme){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé
        values.put(LIB_THEME, theme.getLibTheme());

        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_THEME, null, values);
    }
    public int updateTheme(int id, Theme theme){
        //La mise à jour d'un livre
        //on précise quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();

        values.put(LIB_THEME, theme.getLibTheme());
        return bdd.update(TABLE_THEME, values, ID_THEME + " = " +id, null);
    }
    public int removeThemeWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_THEME, ID_THEME + " = " +id, null);
    }

    public Theme getThemeWithLib(String lib){
        //On récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD
        Cursor c = bdd.query(TABLE_THEME, new String[] {ID_THEME, LIB_THEME},
                LIB_THEME + " LIKE \"" + lib +"\"", null, null, null, null);
        return cursorToLivre(c);
    }
    public Theme getThemeWithID(int id){
        //On récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD
        Cursor c = bdd.query(TABLE_THEME, new String[] {ID_THEME, LIB_THEME},
                ID_THEME + " = " + id, null, null, null, null);
        return cursorToLivre(c);
    }
    //Cette méthode permet de convertir un cursor en un livre
    private Theme cursorToLivre(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Theme theme = new Theme();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        theme.setIdTheme(c.getInt(NUM_IDTHEME));
        theme.setLibTheme(c.getString(NUM_LIBTHEME));

        //On ferme le cursor
        c.close();
        //On retourne le livre
        return theme;
    }
}

