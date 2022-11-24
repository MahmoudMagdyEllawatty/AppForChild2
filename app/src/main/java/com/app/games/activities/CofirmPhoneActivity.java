package com.app.games.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.callback.UserCallback;
import com.app.games.controller.UserController;
import com.app.games.model.User;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;

public class CofirmPhoneActivity extends AppCompatActivity {

    TextInputEditText code;
    MaterialButton save;

    LoadingHelper loadingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cofirm_phone);

        code = findViewById(R.id.code);
        save = findViewById(R.id.create_account);

        loadingHelper = new LoadingHelper(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText() == null) {
                    code.setError("Required");
                    return;
                } else if (code.getText().toString().equals("")) {
                    code.setError("Required");
                    return;
                }



                loadingHelper.showLoading("Verifying....");
                PhoneAuthCredential credential = PhoneAuthProvider
                        .getCredential(SharedData.veificationId, code.getText().toString());

                FirebaseAuth
                        .getInstance()
                        .signInWithCredential(credential)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(CofirmPhoneActivity.this, "Code Correct", Toast.LENGTH_SHORT).show();


                                new UserController()
                                        .Save(SharedData.test_user, new UserCallback() {
                                            @Override
                                            public void onSuccess(ArrayList<User> users) {

                                                loadingHelper.dismissLoading();
                                                Intent intent = new Intent(CofirmPhoneActivity.this, LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onFail(String msg) {

                                            }
                                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingHelper.dismissLoading();
                        Toast.makeText(CofirmPhoneActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}