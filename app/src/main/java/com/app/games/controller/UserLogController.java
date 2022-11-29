package com.app.games.controller;

import androidx.annotation.NonNull;

import com.app.games.callback.UserLogCallback;
import com.app.games.model.UserLog;
import com.app.games.model.UserLog;
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

public class UserLogController {
    private String node = Config.USER_LOGS_NODE;
    private ArrayList<UserLog> userLogs = new ArrayList<>();
    private FirebaseHelper<UserLog> helper = new FirebaseHelper<UserLog>();

    public void Save(final UserLog UserLog, final UserLogCallback callback){
        if(UserLog.getKey().equals("")){
            UserLog.setKey(helper.getKey(node));
        }

        helper.save(node, UserLog.getKey(), UserLog)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            userLogs.add(UserLog);
                            callback.onSuccess(userLogs);
                        }else{
                            callback.onFail(task.getException().toString());
                        }
                    }
                });
    }



    public void delete(final UserLog category, final UserLogCallback callback){
        helper.delete(node,category.getKey())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userLogs.add(category);
                        callback.onSuccess(userLogs);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFail(e.toString());
            }
        });
    }



    public void getAll(final UserLogCallback callback){
        helper.getAll(node, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots == null && e != null){
                    callback.onFail(e.getLocalizedMessage());
                }else {
                    userLogs = new ArrayList<>();
                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                        UserLog user = snap.toObject(UserLog.class);
                        userLogs.add(user);
                    }
                    callback.onSuccess(userLogs);
                }
            }
        });
    }



}
