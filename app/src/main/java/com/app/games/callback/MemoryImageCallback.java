package com.app.games.callback;

import com.app.games.model.MemoryImage;

import java.util.ArrayList;

public interface MemoryImageCallback {
    void onSuccess(ArrayList<MemoryImage> designs);
    void onFail(String msg);
}
