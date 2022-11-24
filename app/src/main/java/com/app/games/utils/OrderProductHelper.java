package com.app.games.utils;

import com.app.games.model.ShortVideo;

public class OrderProductHelper {
    private ShortVideo shortVideo;
    private int state;

    public OrderProductHelper() {
    }

    public OrderProductHelper(ShortVideo shortVideo, int state) {
        this.shortVideo = shortVideo;
        this.state = state;
    }

    public ShortVideo getShortVideo() {
        return shortVideo;
    }

    public void setShortVideo(ShortVideo shortVideo) {
        this.shortVideo = shortVideo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
