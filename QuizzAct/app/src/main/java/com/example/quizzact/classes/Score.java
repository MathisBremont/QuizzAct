package com.example.quizzact.classes;

public class Score {
    private int idScore;
    private int score;
    private String date;

    public Score() {
    }

    public Score(int idScore,int score,String date) {
        this.idScore=idScore;
        this.score = score;
        this.date=date;
    }

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Score{" +
                "idScore=" + idScore +
                ", score=" + score +
                ", date=" + date +
                '}';
    }
}
