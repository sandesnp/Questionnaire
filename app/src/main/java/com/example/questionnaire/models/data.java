package com.example.questionnaire.models;

import java.io.Serializable;
import java.util.ArrayList;

public class data implements Serializable {
    private int id;
    private String code, name, slug, quiz_id;
    private quiz quiz;
    private ArrayList<questions> questions;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public com.example.questionnaire.models.quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(com.example.questionnaire.models.quiz quiz) {
        this.quiz = quiz;
    }

    public ArrayList<com.example.questionnaire.models.questions> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<com.example.questionnaire.models.questions> questions) {
        this.questions = questions;
    }
}
