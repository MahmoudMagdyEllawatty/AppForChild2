package com.app.games.controller;

import androidx.annotation.NonNull;

import com.app.games.callback.MemoryImageCallback;
import com.app.games.model.MemoryImage;
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

public class MemoryImageController {
    private String node = Config.MEMORY_IMAGE_NODE;
    private ArrayList<MemoryImage> memoryImages = new ArrayList<>();
    private FirebaseHelper<MemoryImage> helper = new FirebaseHelper<MemoryImage>();

    public void Save(final MemoryImage Comics, final MemoryImageCallback callback){
        if(Comics.getKey().equals("")){
            Comics.setKey(helper.getKey(node));
        }

        helper.save(node, Comics.getKey(), Comics)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            memoryImages.add(Comics);
                            callback.onSuccess(memoryImages);
                        }else{
                            callback.onFail(task.getException().toString());
                        }
                    }
                });
    }



    public void delete(final MemoryImage category, final MemoryImageCallback callback){
        helper.delete(node,category.getKey())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        memoryImages.add(category);
                        callback.onSuccess(memoryImages);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFail(e.toString());
            }
        });
    }



    public void getAll(final MemoryImageCallback callback){
        helper.getAll(node, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots == null && e != null){
                    callback.onFail(e.getLocalizedMessage());
                }else {
                    memoryImages = new ArrayList<>();
                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                        MemoryImage user = snap.toObject(MemoryImage.class);
                        memoryImages.add(user);
                    }
                    callback.onSuccess(memoryImages);
                }
            }
        });
    }



}
