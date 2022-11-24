package com.app.games.callback;

import com.app.games.model.ShortVideo;

import java.util.ArrayList;

public interface ShortVideosCallback {
    void onSuccess(ArrayList<ShortVideo> shortVideos);
    void onFail(String msg);
}
