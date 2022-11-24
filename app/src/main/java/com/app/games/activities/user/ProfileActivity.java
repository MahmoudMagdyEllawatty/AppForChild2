package com.app.games.activities.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.activities.LoginActivity;
import com.app.games.activities.RegisterActivity;
import com.app.games.callback.FileUploadCallback;
import com.app.games.callback.UserCallback;
import com.app.games.controller.ImageController;
import com.app.games.controller.UserController;
import com.app.games.model.User;
import com.app.games.utils.CircleTransform;
import com.app.games.utils.ImagePicker;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextInputEditText userName,password,cPassword,age,email;
    MaterialButton createAccount;
    LoadingHelper loadingHelper;
    User user ;
    ImageView image;

    private static final int RESULT_LOAD_IMAGES = 25;
    String imageURL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loadingHelper = new LoadingHelper(this);
        userName = findViewById(R.id.name);
        email =findViewById(R.id.email);
        password = findViewById(R.id.password);
        cPassword = findViewById(R.id.cpassword);
        age = findViewById(R.id.age);
        image = findViewById(R.id.image);

        createAccount = findViewById(R.id.create_account);

        user = SharedData.user;
        userName.setText(user.getName());
        email.setText(user.getEmail());
        password.setText(user.getPassword());
        cPassword.setText(user.getPassword());
        age.setText(user.getAge());

        if(user.getImageURL() != null){
            if(!user.getImageURL().equals("")){
                image.setBackground(null);
                Picasso.get()
                        .load(user.getImageURL())
                        .into(image);
            }
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkReadPermission()){
                    pickImage();
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkData()){


                    user.setName(userName.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setAge(age.getText().toString());
                    user.setEmail(email.getText().toString());

                    loadingHelper.showLoading("Creating Account");

                    new UserController()
                            .Save(SharedData.user, new UserCallback() {
                                @Override
                                public void onSuccess(ArrayList<User> users) {

                                    loadingHelper.dismissLoading();
                                    Toast.makeText(ProfileActivity.this, "Profile Updated!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ProfileActivity.this,UserMainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
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

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkReadPermission(){
        int permissionWriteExternal = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},2);
            return false;
        }else{
            return true;
        }
    }

    private boolean checkCameraPermission(){
        int permissionWriteExternal = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if (permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.CAMERA},2);
            return false;
        }else{
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImage();
            }
        }
    }

    private void pickImage(){
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, RESULT_LOAD_IMAGES);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if (requestCode == PICK_IMAGE) {
        loadingHelper.showLoading("Uploading Image");
        Uri uri = ImagePicker.getUriFromResult(this, resultCode, data);;
        if(uri == null){
            Toast.makeText(this, "Cannot load image", Toast.LENGTH_SHORT).show();
            loadingHelper.dismissLoading();
        }else{
            new ImageController().uploadImage(uri, new FileUploadCallback() {
                @Override
                public void onSuccess(String url) {
                    imageURL = url;
                    loadingHelper.dismissLoading();
                    image.setBackground(null);
                    Picasso.get()
                            .load(imageURL)
                            .into(image);
                    user.setImageURL(imageURL);
                }

                @Override
                public void onFail(String msg) {
                    loadingHelper.dismissLoading();
                }
            });
        }

    }
}
