package com.app.games.activities.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.callback.ComicsCallback;
import com.app.games.callback.FileUploadCallback;
import com.app.games.controller.ComicsController;
import com.app.games.controller.ImageController;
import com.app.games.model.Comics;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddComicActivity extends AppCompatActivity implements View.OnClickListener {



    //data variables
    TextInputEditText name,description;
    TextView fileName;

    //btns
    MaterialButton save,selectPDF;


    LoadingHelper loadingHelper;
    Comics comics;

    private static final int RESULT_LOAD_IMAGES = 1;
    Uri uri;
    String fileURL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comic);

        initViews();
        loadingHelper = new LoadingHelper(this);
        setData();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initViews(){

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        fileName = findViewById(R.id.file_name);


        save = findViewById(R.id.save);
        selectPDF = findViewById(R.id.select_pdf);


        save.setOnClickListener(this);
        selectPDF.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:

                if(checkData()){
                    loadingHelper.showLoading("Saving Data");
                        uploadFile();
                }

                break;

            case R.id.select_pdf:
                if (checkReadPermission()) {
                    pickDoc();
                }
                break;
        }
    }


    private void setData(){
        if(SharedData.comics == null){
            comics = new Comics();
            comics.setKey("");
        }else{
            comics = SharedData.comics;
            name.setText(comics.getName());
            description.setText(comics.getDescription());
            fileURL = comics.getPdfURL();
        }
    }

    private boolean checkData(){

        if(name.getText() == null){
            name.setError("Required");
            return false;
        }else if(name.getText().toString().equals("")){
            name.setError("Required");
            return false;
        }


        if(description.getText() == null){
            description.setError("Required");
            return false;
        }else if(description.getText().toString().equals("")){
            description.setError("Required");
            return false;
        }


        if(uri == null && comics.getKey().equals("")){
            Toast.makeText(this, "Please,Select PDF File First", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }



    private void uploadFile(){
        if(uri == null)
            saveData();
        else {
            new ImageController().uploadImage(uri, new FileUploadCallback() {
                @Override
                public void onSuccess(String url) {
                    fileURL = url;
                    saveData();
                }

                @Override
                public void onFail(String msg) {
                    loadingHelper.dismissLoading();
                    Toast.makeText(AddComicActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveData(){
        comics.setPdfURL(fileURL);
        comics.setName(name.getText().toString());
        comics.setDescription(description.getText().toString());

        new ComicsController()
                .Save(comics, new ComicsCallback() {
                    @Override
                    public void onSuccess(ArrayList<Comics> shops) {
                        loadingHelper.dismissLoading();
                        Toast.makeText(AddComicActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFail(String msg) {
                        Toast.makeText(AddComicActivity.this, msg, Toast.LENGTH_SHORT).show();
                        loadingHelper.dismissLoading();
                    }
                });
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickDoc();
            }
        }
    }

    private void pickDoc(){
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF File"), RESULT_LOAD_IMAGES);
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGES && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            uri = data.getData();
            String fileName1 = getFileName(uri);
            fileName.setText(fileName1);


        }
    }


    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
