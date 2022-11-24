package com.app.games.controller;

import androidx.annotation.NonNull;


import com.app.games.callback.UserCallback;
import com.app.games.model.User;
import com.app.games.utils.Config;
import com.app.games.utils.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import javax.annotation.Nullable;

public class UserController {

    private String node = Config.USERS_NODE;
    ArrayList<User> users = new ArrayList<>();
    FirebaseHelper<User> helper = new FirebaseHelper<User>();

    public void Save(final User user, final UserCallback callback){
        if(user.getKey().equals("")){
            user.setKey(helper.getKey(node));
        }

        helper.save(node,user.getKey(),user)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        users = new ArrayList<>();
                            if(task.isSuccessful()){
                                users.add(user);
                                callback.onSuccess(users);
                            }else{
                                callback.onFail(task.getException().toString());
                            }
                    }
                });
    }


    public void checkLogin(final String email, final String passwrod, final UserCallback callback){
        helper.getAllQuery(node)
                .whereEqualTo("name",email)
                .whereEqualTo("password",passwrod)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            users = new ArrayList<>();
                            for (QueryDocumentSnapshot snapshot : Objects.requireNonNull(task.getResult())){
                                users.add(snapshot.toObject(User.class));
                            }
                            callback.onSuccess(users);
                        }else{
                            callback.onFail(task.getException().toString());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFail(e.toString());
                    }
                });

    }


    public void getUsers(final UserCallback callback){
        helper.getAll(node, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots == null && e != null){
                    callback.onFail(e.getLocalizedMessage());
                }else {
                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                        User user = snap.toObject(User.class);
                        users.add(user);
                    }
                    callback.onSuccess(users);
                }
            }
        });
    }

}
