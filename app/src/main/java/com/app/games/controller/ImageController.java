package com.app.games.controller;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.app.games.callback.FileUploadCallback;
import com.app.games.model.Comics;
import com.app.games.utils.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ImageController {

    FirebaseHelper<Comics> helper = new FirebaseHelper<>();

    public void uploadImage(Uri uri, final FileUploadCallback callback){
        helper.uploadDoc(uri.toString(),uri)
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            callback.onSuccess(task.getResult().toString());
                        }else{
                            callback.onFail(task.getException().toString());
                        }
                    }
                });
    }

}
