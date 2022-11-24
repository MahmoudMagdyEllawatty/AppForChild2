package com.app.games.callback;

import com.app.games.model.QuestionType;

import java.util.ArrayList;

public interface QuestionTypeCallback {
    void onSuccess(ArrayList<QuestionType> questionTypes);
    void onFail(String msg);
}
