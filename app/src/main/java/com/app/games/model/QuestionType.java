package com.app.games.model;

public class QuestionType {
    private String key;
    private String type;
    private int side;

    public QuestionType() {
    }

    public QuestionType(String key, String type, int side) {
        this.key = key;
        this.type = type;
        this.side = side;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }
}
