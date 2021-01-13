package com.example.questionnaire.models;

public class attempt {

    private int userid, questionid;
    private String answerid;
    private boolean status;

    public attempt(int userid, int questionid, String answerid, boolean status) {
        this.userid = userid;
        this.questionid = questionid;
        this.answerid = answerid;
        this.status = status;
    }

    public int getUserid() {
        return userid;
    }

    public int getQuestionid() {
        return questionid;
    }

    public String getAnswer() {
        return answerid;
    }

    public boolean isStatus() {
        return status;
    }
}
