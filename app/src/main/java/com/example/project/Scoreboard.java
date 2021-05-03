package com.example.project;

public class Scoreboard {
    private String name;
    private int score;

    public Scoreboard(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
