package com.example.questionnaire.models;

import java.io.Serializable;
import java.util.ArrayList;

public class answers implements Serializable {

    private ArrayList<attempt> attempt;
    private int Total;

    public ArrayList<com.example.questionnaire.models.attempt> getAttempt() {
        return attempt;
    }

    public void setAttempt(ArrayList<com.example.questionnaire.models.attempt> attempt) {
        this.attempt = attempt;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
