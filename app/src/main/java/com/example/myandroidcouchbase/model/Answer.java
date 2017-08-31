package com.example.myandroidcouchbase.model;

public class Answer {

    private String _id;
    private String _rev;
    private String question_id;
    private String type;
    private String user_answer;

    public Answer(String question_id, String type, String user_answer) {
        this.question_id = question_id;
        this.type = type;
        this.user_answer = user_answer;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }
}
