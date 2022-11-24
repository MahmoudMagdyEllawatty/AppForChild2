package com.app.games.controller;

import androidx.annotation.NonNull;

import com.app.games.callback.ShortVideosCallback;
import com.app.games.model.ShortVideo;
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

public class ShortVideoController {
    private String node = Config.VIDEOS_NODE;
    private ArrayList<ShortVideo> shortVideos = new ArrayList<>();
    private FirebaseHelper<ShortVideo> helper = new FirebaseHelper<ShortVideo>();

    public void Save(final ShortVideo ShortVideo, final ShortVideosCallback callback){
        if(ShortVideo.getKey().equals("")){
            ShortVideo.setKey(helper.getKey(node));
        }

        helper.save(node, ShortVideo.getKey(), ShortVideo)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            shortVideos.add(ShortVideo);
                            callback.onSuccess(shortVideos);
                        }else{
                            callback.onFail(task.getException().toString());
                        }
                    }
                });
    }



    public void delete(final ShortVideo category, final ShortVideosCallback callback){
        helper.delete(node,category.getKey())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        shortVideos.add(category);
                        callback.onSuccess(shortVideos);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFail(e.toString());
            }
        });
    }



    public void getAll(final ShortVideosCallback callback){
        helper.getAll(node, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots == null && e != null){
                    callback.onFail(e.getLocalizedMessage());
                }else {
                    shortVideos = new ArrayList<>();
                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                        ShortVideo user = snap.toObject(ShortVideo.class);
                        shortVideos.add(user);
                    }
                    callback.onSuccess(shortVideos);
                }
            }
        });
    }





}
