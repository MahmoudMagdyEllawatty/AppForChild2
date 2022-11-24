package com.app.games.controller;

import androidx.annotation.NonNull;

import com.app.games.callback.ComicsCallback;
import com.app.games.model.Comics;
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

public class ComicsController {
    private String node = Config.COMICS_NODE;
    private ArrayList<Comics> comics = new ArrayList<>();
    private FirebaseHelper<Comics> helper = new FirebaseHelper<Comics>();

    public void Save(final Comics Comics, final ComicsCallback callback){
        if(Comics.getKey().equals("")){
            Comics.setKey(helper.getKey(node));
        }

        helper.save(node, Comics.getKey(), Comics)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            comics.add(Comics);
                            callback.onSuccess(comics);
                        }else{
                            callback.onFail(task.getException().toString());
                        }
                    }
                });
    }



    public void delete(final Comics category, final ComicsCallback callback){
        helper.delete(node,category.getKey())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        comics.add(category);
                        callback.onSuccess(comics);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFail(e.toString());
            }
        });
    }



    public void getAll(final ComicsCallback callback){
        helper.getAll(node, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots == null && e != null){
                    callback.onFail(e.getLocalizedMessage());
                }else {
                    comics = new ArrayList<>();
                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                        Comics user = snap.toObject(Comics.class);
                        comics.add(user);
                    }
                    callback.onSuccess(comics);
                }
            }
        });
    }



}
