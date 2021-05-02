package com.example.project;

public class Answer {
    private String choice;
    private boolean correct;

    public Answer(String choice, boolean correct ){
        this.choice = choice;
        this.correct = correct;
    }

    public String getChoice() {
        return choice;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public String toString() {
        if(isCorrect()){
            return "Ja det är rätt!";
        }else{
            return "Nej det är fel!";
        }
    }
}
