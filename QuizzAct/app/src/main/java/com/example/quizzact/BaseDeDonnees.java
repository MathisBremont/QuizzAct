package com.example.quizzact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDonnees extends SQLiteOpenHelper {

    /////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    //THEME
    private static final String TABLE_THEME = "THEME";
    private static final String ID_THEME= "idTheme";
    private static final String LIB_THEME = "libTheme";

    //QUESTION
    private static final String TABLE_QUESTION = "QUESTION";
    private static final String ID_QUESTION = "idQuest";
    private static final String LIB_QUESTION = "libQuest";

    //REPONSE
    private static final String TABLE_REPONSE = "REPONSE";
    private static final String ID_REPONSE = "idRep";
    private static final String LIB_REPONSE = "libRep";

    //BONNE REPONSE
    private static final String TABLE_BONNE_REPONSE = "BONNE_REPONSE";



    //SCORE
    private static final String TABLE_SCORE = "SCORE";
    private static final String ID_SCORE = "idScore";
    private static final String SCORE_SCORE = "score";
    private static final String DATE_SCORE="date";



    public static final String DONNEES_DANS_QUESTION = "INSERT INTO "+ TABLE_QUESTION+ "("+ LIB_QUESTION+","+ID_THEME+") VALUES " +
            "(\"Quelle est la capitale de la France ?\", 1),"+
            "(\"Quelle est la capitale de la Belgique ?\", 1),"+
            "(\"Quelle est la capitale de la Suisse ?\", 1),"+
            "(\"Quelle est la capitale de la Finlande ?\", 1),"+
            "(\"Quelle est la capitale du Canada ?\", 1),"+
            "(\"Quelle est la capitale de la Norvège ?\", 1),"+
            "(\"Quelle est la capitale des Etats-Unis ?\", 1),"+
            "(\"Quelle est la capitale de la Chine ?\", 1),"+
            "(\"Quelle est la capitale du Japon ?\", 1),"+
            "(\"Quelle est la capitale de la Russie ?\", 1),"+

            "(\"Quel âge a la planète Terre ?\", 2),"+
            "(\"Qu'est ce qu'une molécule ?\", 2),"+
            "(\"Le volt est l'unité de ?\", 2),"+
            "(\"Le BCG est un vaccin contre ?\", 2),"+
            "(\"A la température de 15°C, a quelle vitesse atteint-on le mur du son ?\", 2),"+
            "(\"A quoi sert le fluor ?\", 2),"+
            "(\"Quel groupe sanguin est donneur universel ?\", 2),"+

            "(\"Avec quoi joue-t-on au bowling ?\", 4),"+
            "(\"À quelle hauteur se trouve le filet de volley pour une compétition masculine ?\", 4),"+
            "(\"Quel sport est pratiqué lors de Roland Garros ?\", 4),"+
            "(\"Quel sport a été inventé par un pasteur canadien installé dans le Massachusetts ?\", 4),"+
            "(\"Quel pays a remporté la Coupe du monde de Football en 1998 ?\", 4),"+
            "(\"Quel est le nom du vainqueur du tournoi de tennis de Wimbledon en 2005 ?\", 4),"+
            "(\"Combien de fois, Alain Prost a-t-il été champion du monde de Formule 1 ?\", 4),"+

            "(\"Edith Piaf a chanté la chanson ''À quoi ça sert l'amour'' avec ?\", 3),"+
            "(\"Qui a gagné le Grand Prix Eurovision en 1965 avec ''Poupée de cire'' ?\", 3),"+
            "(\"Quel était le surnom de la chanteuse Barbara ?\", 3),"+
            "(\"Comment a-t-on appelé la jeune scène française des années 60 ?\", 3),"+
            "(\"Lequel de ces titres n'appartient pas au répertoire de Charles Aznavour ?\", 3),"+
            "(\"''Le Sud'' est une de ses plus belles mélodies. De quel interprète il s'agit ?\", 3),"+
            "(\"Parmi ces trois chanteurs, lequel n'a pas participé à la comédie musicale ''Notre-Dame de Paris'' ?\", 3);";

    public static final String DONNEES_DANS_THEME = "INSERT INTO "+TABLE_THEME+ " ("+LIB_THEME+") VALUES ( \"Capitale\"),"+
            "(\"Science\"),"+
            "(\"Musique\"),"+
            "(\"Sport\");";


    public static final String DONNEES_DANS_BONNE_REPONSE = "INSERT INTO "+TABLE_BONNE_REPONSE+ " ("+ ID_REPONSE+","+ID_QUESTION+") VALUES (1, 1),"+
            "(5, 2),"+
            "(9, 3),"+
            "(13, 4),"+
            "(17, 5),"+
            "(21, 6),"+
            "(25, 7),"+
            "(29, 8),"+
            "(33, 9),"+
            "(37, 10),"+
            "(41, 11),"+
            "(45, 12),"+
            "(49, 13),"+
            "(53, 14),"+
            "(57, 15),"+
            "(61, 16),"+
            "(65, 17),"+
            "(69, 18),"+
            "(73, 19),"+
            "(77, 20),"+
            "(81, 21),"+
            "(85, 22),"+
            "(89, 23),"+
            "(93, 24),"+
            "(97, 25),"+
            "(101, 26),"+
            "(105, 27),"+
            "(109, 28),"+
            "(113, 29),"+
            "(117, 30),"+
            "(121, 31);";
    /////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    private static final String CREATE_TABLE_THEME = "create table " + TABLE_THEME + " ("
            + ID_THEME + " integer primary key autoincrement, "
            + LIB_THEME + " text not null);";



    private static final String CREATE_TABLE_QUESTION = "create table " + TABLE_QUESTION + " ("
            + ID_QUESTION +" integer primary key autoincrement,"
            + LIB_QUESTION+ " text not null, "
            + ID_THEME +" integer not null references "+ TABLE_THEME +"("+ID_THEME+"));";



    private static final String CREATE_TABLE_REPONSE = "create table " + TABLE_REPONSE + " ("
            + ID_REPONSE +" integer primary key autoincrement,"
            + ID_QUESTION+" integer not null references "+TABLE_QUESTION+"("+ID_QUESTION+"),"
            + LIB_REPONSE+ " text not null);";



    private static final String CREATE_TABLE_BONNE_REPONSE = "create table " + TABLE_BONNE_REPONSE + " ("
            + ID_REPONSE +" integer not null references "+TABLE_REPONSE+"("+ID_REPONSE+"),"
            + ID_QUESTION+" integer not null references "+TABLE_QUESTION+"("+ID_QUESTION+"),"
            + "primary key ("+ID_QUESTION+","+ID_REPONSE+"));";




    private static final String CREATE_TABLE_SCORE = "create table " + TABLE_SCORE + " ("
            + ID_SCORE +" integer primary key,"
            + SCORE_SCORE+" integer not null,"
            + DATE_SCORE+" text not null);";

    /////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////


    public BaseDeDonnees(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_THEME);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_REPONSE);
        db.execSQL(CREATE_TABLE_BONNE_REPONSE);
        db.execSQL(CREATE_TABLE_SCORE);

        db.execSQL(DONNEES_DANS_THEME);
        db.execSQL(DONNEES_DANS_QUESTION);


        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (1,\"Paris\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (1,\"Lyon\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (1,\"Bordeaux\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (1,\"Strasbourg\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (2,\"Bruxelles\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (2,\"Liège\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (2,\"Anvers\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (2,\"Gand\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (3,\"Berne\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (3,\"Genève\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (3,\"Zurich\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (3,\"Lucerne\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (4,\"Helsinski\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (4,\"Turku\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (4,\"Oulu\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (4,\"Tampere\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (5,\"Ottawa\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (5,\"Vancouver\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (5,\"Calgary\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (5,\"Montréal\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (6,\"Oslo\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (6,\"Bergen\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (6,\"Haugesund\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (6,\"Drammen\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (7,\"Washington\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (7,\"San Francisco\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (7,\"Miami\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (7,\"New York\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (8,\"Pékin\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (8,\"Hong-Kong\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (8,\"Shanghai\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (8,\"Wuhan\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (9,\"Tokyo\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (9,\"Hiroshima\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (9,\"Yokohama\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (9,\"Chiba\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (10,\"Moscou\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (10,\"Saint-Pétersbourg\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (10,\"Perm\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (10,\"Oufa\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (11,\"5,57 milliars d'années\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (11,\"4,57 milliards d'années\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (11,\"3,87 milliards d'années\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (11,\"4,87 milliards d'années\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (12,\"Un assemblage d'atomes\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (12,\"Un assemblage de cellule\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (12,\"Le plus petit des éléments\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (12,\"Un noyau d'atome\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (13,\"Tension électrique\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (13,\"Courant électrique\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (13,\"Résistance électrique\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (13,\"Capacité de batterie\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (14,\"La tuberculose\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (14,\"Le tétanos\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (14,\"La grippe\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (14,\"Le COVID-19\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (15,\"1224 Km/h\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (15,\"924 Km/h\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (15,\"1024 Km/h\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (15,\"1124 Km/h\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (16,\"A lutter contre les caries dentaires\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (16,\"A nettoyer la bouche\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (16,\"A ficer le calcium des os et des dents\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (16,\"A conserver l'émaille des dents\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (17,\"O-\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (17,\"A-\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (17,\"A+\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (17,\"AB+\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (18,\"Une boule\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (18,\"Une balle\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (18,\"Un dé\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (18,\"Un ballon\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (19,\"2,43 mètres\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (19,\"2,24 mètres\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (19,\"1,93 mètres\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (19,\"2,78 mètres\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (20,\"Tennis\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (20,\"Voile\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (20,\"Judo\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (20,\"Natation\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (21,\"Le basket\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (21,\"Le hockey\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (21,\"Le curling\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (21,\"Le saut à ski\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (22,\"La France\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (22,\"Le Brésil\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (22,\"L'Italie\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (22,\"L'Allemagne\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (23,\"Roger Federer\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (23,\"Albert Costa\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (23,\"Yannick Noah\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (23,\"Andy Roddick\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (24,\"4\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (24,\"8\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (24,\"2\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (24,\"5\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (25,\"Théo Sarapo\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (25,\"Yves Montand\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (25,\"Georges Moustaki\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (25,\"Daniel Lavoie\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (26,\"France Gall\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (26,\"Françoise Hardy\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (26,\"Sylvie Vartan\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (26,\"Angèle\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (27,\"La Dame en noir\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (27,\"La Belle Dame\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (27,\"La Grande Dame\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (27,\"La Dame du Lac\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (28,\"Les yé-yé\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (28,\"Les yo-yo\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (28,\"Les ya-ya\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (28,\"Les yu-yu\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (29,\"Nantes\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (29,\"Et pourtant\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (29,\"Hier Encore\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (29,\"La Bohème\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (30,\"Nino Ferrer\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (30,\"Claude Nougaro\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (30,\"Léo Ferré\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (30,\"Carla Bruni\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (31,\"Patrick Bruel\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (31,\"Daniel Lavoie\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (31,\"Patrick Fiori\")");
        db.execSQL("INSERT INTO "+TABLE_REPONSE+ "("+ID_QUESTION+","+LIB_REPONSE+")VALUES (31,\"Garou\")");
        db.execSQL(DONNEES_DANS_BONNE_REPONSE);







    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_THEME + ";");
        db.execSQL("DROP TABLE " + TABLE_QUESTION + ";");
        db.execSQL("DROP TABLE " + TABLE_REPONSE + ";");
        db.execSQL("DROP TABLE " + TABLE_BONNE_REPONSE + ";");
        db.execSQL("DROP TABLE " + TABLE_SCORE + ";");
        onCreate(db);
    }
}