package com.app.games.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.callback.UserCallback;
import com.app.games.controller.UserController;
import com.app.games.model.User;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText userName,phone,password,cPassword,age,email;
    MaterialCheckBox checkBox;
    MaterialButton createAccount;
    String vertivicationId = "";

    LoadingHelper loadingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        loadingHelper = new LoadingHelper(this);
        userName = findViewById(R.id.name);
        email =findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        cPassword = findViewById(R.id.cpassword);
        checkBox = findViewById(R.id.check_terms);
        age = findViewById(R.id.age);

        createAccount = findViewById(R.id.create_account);


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkData()){

                    final String phone1 ="";

                    User user = new User("",userName.getText().toString(),
                            password.getText().toString(),phone1,age.getText().toString(),email.getText().toString(),"");
                    SharedData.test_user = user;

                    loadingHelper.showLoading("Creating Account");

                    new UserController()
                            .Save(SharedData.test_user, new UserCallback() {
                                @Override
                                public void onSuccess(ArrayList<User> users) {

                                    loadingHelper.dismissLoading();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFail(String msg) {

                                }
                            });
//                                    PhoneAuthProvider
//                                            .getInstance()
//                                            .verifyPhoneNumber(phone1, 60, TimeUnit.SECONDS,
//                                                    RegisterActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                                        @Override
//                                                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//                                                        }
//
//                                                        @Override
//                                                        public void onVerificationFailed(FirebaseException e) {
//                                                            loadingHelper.dismissLoading();
//                                                            Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                                        }
//
//                                                        @Override
//                                                        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                                            super.onCodeSent(s, forceResendingToken);
//                                                            loadingHelper.dismissLoading();
//                                                            vertivicationId = s;
//                                                            SharedData.veificationId = s;
//                                                            Toast.makeText(RegisterActivity.this, "Code Sent!", Toast.LENGTH_SHORT).show();
//                                                            Intent intent = new Intent(RegisterActivity.this,CofirmPhoneActivity.class);
//                                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                            startActivity(intent);
//                                                        }
//                                                    });



                }
            }
        });

    }


    private boolean checkData(){

        if(userName.getText() == null){
            userName.setError("Required");
            return false;
        }else if(userName.getText().toString().equals("")){
            userName.setError("Required");
            return false;
        }

        if(email.getText() == null){
            email.setError("Required");
            return false;
        }else if(email.getText().toString().equals("")){
            email.setError("Required");
            return false;
        }

        if(password.getText() == null){
            password.setError("Required");
            return false;
        }else if(password.getText().toString().equals("")){
            password.setError("Required");
            return false;
        }

        if(cPassword.getText() == null){
            cPassword.setError("Required");
            return false;
        }else if(cPassword.getText().toString().equals("")){
            cPassword.setError("Required");
            return false;
        }

        if(phone.getText() == null){
            phone.setError("Required");
            return false;
        }else if(phone.getText().toString().equals("")){
            phone.setError("Required");
            return false;
        }


        if(age.getText() == null){
            age.setError("Required");
            return false;
        }else if(age.getText().toString().equals("")){
            age.setError("Required");
            return false;
        }


        if(!password.getText().toString().equals(cPassword.getText().toString())){
            cPassword.setError("Not correct");
            return false;
        }

        if(!checkBox.isChecked()){
            Toast.makeText(this, "You must check terms", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }

}
