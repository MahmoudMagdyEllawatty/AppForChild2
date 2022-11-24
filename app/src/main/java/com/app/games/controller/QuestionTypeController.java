package com.app.games.controller;

import androidx.annotation.NonNull;

import com.app.games.callback.QuestionTypeCallback;
import com.app.games.model.QuestionType;
import com.app.games.utils.Config;
import com.app.games.utils.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class QuestionTypeController {
    private String node = Config.QUESTION_TYPE_NODE;
    private ArrayList<QuestionType> questionType = new ArrayList<>();
    private FirebaseHelper<QuestionType> helper = new FirebaseHelper<QuestionType>();

    public void Save(final QuestionType QuestionType, final QuestionTypeCallback callback){

        QuestionType type = new QuestionType();


        if(QuestionType.getKey().equals("")){
            QuestionType.setKey(helper.getKey(node));
        }

        helper.save(node, QuestionType.getKey(), QuestionType)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            questionType.add(QuestionType);
                            callback.onSuccess(questionType);
                        }else{
                            callback.onFail(task.getException().toString());
                        }
                    }
                });
    }



    public void delete(final QuestionType category, final QuestionTypeCallback callback){
        helper.delete(node,category.getKey())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        questionType.add(category);
                        callback.onSuccess(questionType);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFail(e.toString());
            }
        });
    }



    public void getAll(final QuestionTypeCallback callback){
        helper.getAll(node, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots == null && e != null){
                    callback.onFail(e.getLocalizedMessage());
                }else {
                    questionType = new ArrayList<>();
                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                        QuestionType user = snap.toObject(QuestionType.class);
                        questionType.add(user);
                    }
                    callback.onSuccess(questionType);
                }
            }
        });
    }



}
