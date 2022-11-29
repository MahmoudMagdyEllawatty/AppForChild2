package com.app.games.callback;

import com.app.games.model.Comics;
import com.app.games.model.UserLog;

import java.util.ArrayList;

public interface UserLogCallback {
    void onSuccess(ArrayList<UserLog> userLogs);
    void onFail(String msg);
}
