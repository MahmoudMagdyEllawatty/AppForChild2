package com.app.games.callback;

public interface FileUploadCallback {

    void onSuccess(String url);
    void onFail(String msg);
}
