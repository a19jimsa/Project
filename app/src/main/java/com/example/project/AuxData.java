package com.example.project;

public class AuxData {
    private String question;
    private Answer [] answer;

    public AuxData(String question, Answer [] answer){
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public Answer [] getAnswer() {
        return answer;
    }
}
