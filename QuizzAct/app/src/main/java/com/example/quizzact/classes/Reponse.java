package com.example.quizzact.classes;

public class Reponse {
    private int idRep;
    private int idQuest;
    private String libRep;

    public Reponse() {
    }

    public Reponse(int idQuest, String libRep) {
        this.idQuest = idQuest;
        this.libRep = libRep;
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

    public String getLibRep() {
        return libRep;
    }

    public void setLibRep(String libRep) {
        this.libRep = libRep;
    }

    @Override
    public String toString() {
        return "Reponses{" +
                "idRep=" + idRep +
                ", idQuest=" + idQuest +
                ", label='" + libRep + '\'' +
                '}';
    }
}
