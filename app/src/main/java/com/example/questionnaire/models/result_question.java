package com.example.questionnaire.models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class result_question implements Serializable {
    private ArrayList<questions> questions;
    private answers answers;

    public ArrayList<com.example.questionnaire.models.questions> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<com.example.questionnaire.models.questions> questions) {
        this.questions = questions;
    }

    public com.example.questionnaire.models.answers getAnswers() {
        return answers;
    }

    public void setAnswers(com.example.questionnaire.models.answers answers) {
        this.answers = answers;
    }
}
