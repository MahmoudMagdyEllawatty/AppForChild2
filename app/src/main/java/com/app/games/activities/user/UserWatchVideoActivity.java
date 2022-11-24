package com.app.games.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.games.R;
import com.app.games.utils.LocaleManager;
import com.app.games.utils.SharedData;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class UserWatchVideoActivity extends AppCompatActivity implements Player.EventListener {

    SimpleExoPlayer simpleExoPlayer;
    MaterialButton show_questions;
    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_watch_video);

        playerView = findViewById(R.id.exoplayerView);
        show_questions = findViewById(R.id.show_questions);

        SimpleExoPlayer.Builder builder = new SimpleExoPlayer.Builder(this);
        simpleExoPlayer = builder.build();

        playerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(this);

        MediaItem mediaItem = MediaItem.fromUri(SharedData.shortVideo.getVideoURL());
        simpleExoPlayer.setMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();





        show_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.question_section = 2;
                Intent intent = new Intent(UserWatchVideoActivity.this,
                        UserQuestionTypes.class);
                startActivity(intent);
            }
        });

    }

    public void loadLocale() {
        String language = LocaleManager.getLanguagePref(this);
        changeLang(language);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        
    }
}