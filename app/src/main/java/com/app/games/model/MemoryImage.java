package com.app.games.model;

public class MemoryImage {
    private String key;
    private String image;

    public MemoryImage() {
    }

    public MemoryImage(String key, String image) {
        this.key = key;
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
