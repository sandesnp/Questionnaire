package com.example.questionnaire.models;

public class attempt {

    private int userid, questionid;
    private String answerid, justanswer, justquestion;
    private boolean status;

    public attempt(int userid, int questionid, String answerid, boolean status, String justanswer, String justquestion) {
        this.userid = userid;
        this.questionid = questionid;
        this.answerid = answerid;
        this.status = status;
        this.justanswer = justanswer;
        this.justquestion=justquestion;
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

    public String getJustanswer() {
        return justanswer;
    }

    public void setJustanswer(String justanswer) {
        this.justanswer = justanswer;
    }

    public String getJustquestion() {
        return justquestion;
    }

    public void setJustquestion(String justquestion) {
        this.justquestion = justquestion;
    }
}
