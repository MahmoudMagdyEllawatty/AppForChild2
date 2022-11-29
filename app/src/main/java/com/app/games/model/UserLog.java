package com.app.games.model;

public class UserLog {
    private String key;
    private User user;
    private int correct;
    private int wrong;
    private String date;
    private String question;

    public UserLog() {
    }

    public UserLog(String key, User user, int correct, int wrong, String date, String question) {
        this.key = key;
        this.user = user;
        this.correct = correct;
        this.wrong = wrong;
        this.date = date;
        this.question = question;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
