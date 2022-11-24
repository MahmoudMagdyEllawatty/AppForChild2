package com.app.games.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.activities.admin.AdminMainActivity;
import com.app.games.activities.user.UserMainActivity;
import com.app.games.callback.UserCallback;
import com.app.games.controller.UserController;
import com.app.games.model.User;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText email,password;
    MaterialButton login;
    LoadingHelper loadingHelper;
    TextView forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email   = findViewById(R.id.description);
        password = findViewById(R.id.password);

        login = findViewById(R.id.login);
        forgetPassword = findViewById(R.id.forget_password);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loadingHelper = new LoadingHelper(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }


    private void login(){


        if(email.getText() == null){
            email.setError("Required");
            return;
        }else if(email.getText().toString().equals("")){
            email.setError("Required");
            return;
        }


        if(password.getText() == null){
            password.setError("Required");
            return;
        }else if(password.getText().toString().equals("")){
            password.setError("Required");
            return;
        }

        loadingHelper.showLoading("validating login");

        if(email.getText().toString().equals("admin") &&
                password.getText().toString().equals("123456")){
            SharedData.type = 1;
            saveLocale(1);
            loadingHelper.dismissLoading();
            Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            new UserController().checkLogin(email.getText().toString(), password.getText().toString(), new UserCallback() {
                @Override
                public void onSuccess(ArrayList<User> doctors) {
                    if(doctors.size() > 0){
                        SharedData.type = 2;
                        saveLocale(2);
                        SharedData.user = doctors.get(0);
                        loadingHelper.dismissLoading();
                        Intent intent = new Intent(LoginActivity.this, UserMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else{
                        loadingHelper.dismissLoading();
                        Toast.makeText(LoginActivity.this, "invalid user name or password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFail(String msg) {
                    loadingHelper.dismissLoading();
                    Toast.makeText(LoginActivity.this, "invalid user name or password", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }



    public void loadLocale() {
        String emailPref = "email";
        String passPref = "pass";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        email.setText(prefs.getString(emailPref, ""));
        password.setText(prefs.getString(passPref, ""));
        int type = prefs.getInt("type",1);
        if(!email.getText().toString().equals("")){
            SharedData.type = type;
            login();
        }
    }


    public void saveLocale(int type) {
        String emailPref = "email";
        String passPref = "pass";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(emailPref, email.getText().toString());
        editor.putString(passPref, password.getText().toString());
        editor.putInt("type",type);
        editor.apply();
    }


}
