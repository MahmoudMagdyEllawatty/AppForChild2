package com.app.games.callback;

import com.app.games.model.User;

import java.util.ArrayList;

public interface UserCallback {
    void onSuccess(ArrayList<User> users);
    void onFail(String msg);
}
