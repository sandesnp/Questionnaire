package com.example.questionnaire.models;

public class result {
    private int total_question, total_correct_answer, total_leaves, total_incorrect_answer, total_points, user_id;

    public result(int total_question, int total_correct_answer, int total_leaves, int total_incorrect_answer, int total_points, int user_id) {
        this.total_question = total_question;
        this.total_correct_answer = total_correct_answer;
        this.total_leaves = total_leaves;
        this.total_incorrect_answer = total_incorrect_answer;
        this.total_points = total_points;
        this.user_id = user_id;
    }

    public void setTotal_question(int total_question) {
        this.total_question = total_question;
    }

    public void setTotal_correct_answer(int total_correct_answer) {
        this.total_correct_answer = total_correct_answer;
    }

    public void setTotal_leaves(int total_leaves) {
        this.total_leaves = total_leaves;
    }

    public void setTotal_incorrect_answer(int total_incorrect_answer) {
        this.total_incorrect_answer = total_incorrect_answer;
    }

    public void setTotal_points(int total_points) {
        this.total_points = total_points;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
