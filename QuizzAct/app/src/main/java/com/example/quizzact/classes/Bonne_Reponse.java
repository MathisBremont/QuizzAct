package com.example.quizzact.classes;

public class Bonne_Reponse {
    private int idRep;
    private int idQuest;

    public Bonne_Reponse(){

    }

    public Bonne_Reponse(int idRep, int idQuest) {
        this.idRep = idRep;
        this.idQuest = idQuest;
    }

    public int getIdRep() {
        return idRep;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }

    public int getIdQuest() {
        return idQuest;
    }

    public void setIdQuest(int idQuest) {
        this.idQuest = idQuest;
    }

    @Override
    public String toString() {
        return "Bonne_Reponse{" +
                "idRep=" + idRep +
                ", idQuest=" + idQuest +
                '}';
    }
}