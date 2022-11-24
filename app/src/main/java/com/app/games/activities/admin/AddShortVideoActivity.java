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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.callback.FileUploadCallback;
import com.app.games.callback.ShortVideosCallback;
import com.app.games.controller.ImageController;
import com.app.games.controller.ShortVideoController;
import com.app.games.model.ShortVideo;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddShortVideoActivity extends AppCompatActivity implements View.OnClickListener {

    SimpleExoPlayer simpleExoPlayer;
    //data variables
    TextInputEditText name,description;
    PlayerView playerView;

    //btns
    MaterialButton save,selectVideo;


    LoadingHelper loadingHelper;
    ShortVideo shortVideo;

    private static final int RESULT_LOAD_IMAGES = 1;
    Uri uri;
    String fileURL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);

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
        playerView = findViewById(R.id.video_view);


        save = findViewById(R.id.save);
        selectVideo = findViewById(R.id.select_pdf);


        SimpleExoPlayer.Builder builder = new SimpleExoPlayer.Builder(this);
        simpleExoPlayer = builder.build();

        playerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(true);



        save.setOnClickListener(this);
        selectVideo.setOnClickListener(this);

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
        if(SharedData.shortVideo == null){
            shortVideo = new ShortVideo();
            shortVideo.setKey("");
        }else{
            shortVideo = SharedData.shortVideo;
            name.setText(shortVideo.getName());
            description.setText(shortVideo.getDescription());
            fileURL = shortVideo.getVideoURL();

            MediaItem mediaItem = MediaItem.fromUri(fileURL);
            simpleExoPlayer.setMediaItem(mediaItem);
            simpleExoPlayer.prepare();
            simpleExoPlayer.play();

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


        if(uri == null && shortVideo.getKey().equals("")){
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

                }
            });
        }
    }

    private void saveData(){
        shortVideo.setVideoURL(fileURL);
        shortVideo.setName(name.getText().toString());
        shortVideo.setDescription(description.getText().toString());

        new ShortVideoController()
                .Save(shortVideo, new ShortVideosCallback() {
                    @Override
                    public void onSuccess(ArrayList<ShortVideo> shops) {
                        loadingHelper.dismissLoading();
                        Toast.makeText(AddShortVideoActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                    @Override
                    public void onFail(String msg) {
                        Toast.makeText(AddShortVideoActivity.this, msg, Toast.LENGTH_SHORT).show();
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
        intent.setType("video/*");
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
            MediaItem mediaItem = MediaItem.fromUri(uri);
            simpleExoPlayer.setMediaItem(mediaItem);
            simpleExoPlayer.prepare();
            simpleExoPlayer.play();

        }
    }

}