package com.app.games.callback;

import com.app.games.model.Comics;

import java.util.ArrayList;

public interface ComicsCallback {
    void onSuccess(ArrayList<Comics> categories);
    void onFail(String msg);
}
