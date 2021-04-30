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

    public String getCorrect() {
        if(correct){
            return "Svaret är rätt!";
        }else{
            return "Svaret är fel!";
        }
    }
}
