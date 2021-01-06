package com.example.questionnaire.models;

import java.util.ArrayList;

public class questions {
    private int id;
    private String code, set_id, question, correct_answer;
    private ArrayList<objectives> objectives;
    private String points;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSet_id() {
        return set_id;
    }

    public void setSet_id(String set_id) {
        this.set_id = set_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public ArrayList<com.example.questionnaire.models.objectives> getObjectives() {
        return objectives;
    }

    public void setObjectives(ArrayList<com.example.questionnaire.models.objectives> objectives) {
        this.objectives = objectives;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
