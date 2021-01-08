package com.example.questionnaire.models;

public class attempt {

    private int userid, questionid;
    private String answer;
    private boolean status;

    public attempt(int userid, int questionid, String answer, boolean status) {
        this.userid = userid;
        this.questionid = questionid;
        this.answer = answer;
        this.status = status;
    }

    public int getUserid() {
        return userid;
    }

    public int getQuestionid() {
        return questionid;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isStatus() {
        return status;
    }
}
