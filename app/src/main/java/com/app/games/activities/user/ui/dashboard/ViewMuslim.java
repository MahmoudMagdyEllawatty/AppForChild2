package com.app.games.activities.user.ui.dashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.games.R;
import com.app.games.utils.LetterHelper;
import com.app.games.utils.LocaleManager;
import com.app.games.utils.SharedData;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Locale;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class ViewMuslim extends AppCompatActivity implements Player.EventListener {

    ImageView imageView;
    Button next,previous;
    ArrayList<LetterHelper> letters;
    int index = 0;
    String videoURL = "";
    ImageButton video;

    SimpleExoPlayer simpleExoPlayer;
    PlayerView playerView;
    Button closeVideo;
    LinearLayout videoArea;
    RelativeLayout rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_muslim);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);


        rel = findViewById(R.id.rel);
        videoArea = findViewById(R.id.video_area);
        closeVideo = findViewById(R.id.close_video);
        imageView = findViewById(R.id.image);
        video = findViewById(R.id.video);

        playerView = findViewById(R.id.exoplayerView);
        SimpleExoPlayer.Builder builder = new SimpleExoPlayer.Builder(this);
        simpleExoPlayer = builder.build();

        playerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(this);



        videoArea.setVisibility(View.GONE);

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new YouTubeExtractor(ViewMuslim.this){

                    @Override
                    protected void onExtractionComplete(@Nullable SparseArray<YtFile> ytFiles, @Nullable VideoMeta videoMeta) {
                        if(ytFiles != null){
                            int itag = 22;
                            String downloadURL = ytFiles.get(itag).getUrl();

                            MediaItem mediaItem = MediaItem.fromUri(downloadURL);
                            simpleExoPlayer.setMediaItem(mediaItem);
                            simpleExoPlayer.prepare();
                            simpleExoPlayer.play();
                        }
                    }
                }.execute(videoURL);


                videoArea.setVisibility(View.VISIBLE);
                rel.setVisibility(View.GONE);


            }
        });

        closeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoArea.setVisibility(View.GONE);
                rel.setVisibility(View.VISIBLE);
                simpleExoPlayer.stop();
            }
        });


        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);


        letters = new ArrayList<>();
        if(SharedData.muslim == 1){
            //تعليم الوضوء
            videoURL = "https://youtu.be/y3Hd5srW_ak";
            fillWidoa();
            toolbar.setTitle(R.string.learn_ablution);
        }else if(SharedData.muslim == 2){
            //تعليم الصلاه
            videoURL = "https://www.youtube.com/watch?v=edL3W38ODd4&ab_channel=LearnwithZakaria-%D8%AA%D8%B9%D9%84%D9%85%D9%85%D8%B9%D8%B2%D9%83%D8%B1%D9%8A%D8%A7";
            fillPray();
            toolbar.setTitle(R.string.how_to_pray);
        }else if(SharedData.muslim == 3){
            //الرسول
            videoURL = "https://www.youtube.com/watch?v=ECJ_PzG2MAw&ab_channel=LearnwithZakaria-%D8%AA%D8%B9%D9%84%D9%85%D9%85%D8%B9%D8%B2%D9%83%D8%B1%D9%8A%D8%A7";
            fillProphet();
            toolbar.setTitle(R.string.our_prophet);
        }else if(SharedData.muslim == 4){
            //اخلاق
            videoURL = "https://youtu.be/NGTjcY_V8sk";
            fillManars();
            toolbar.setTitle(R.string.my_behaviours);
        }


        index = 0;
        setData();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index < (letters.size()-1))
                    index++;
                setData();
            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index > 0)
                    index--;
                setData();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }

    private void fillWidoa(){
        letters = new ArrayList<>();
        letters.add(new LetterHelper(R.mipmap.widoa1,"",""));
        letters.add(new LetterHelper(R.mipmap.widoa2,"",""));
        letters.add(new LetterHelper(R.mipmap.widoa3,"",""));
        letters.add(new LetterHelper(R.mipmap.widoa4,"",""));
        letters.add(new LetterHelper(R.mipmap.widoa5,"",""));
        letters.add(new LetterHelper(R.mipmap.widoa6,"",""));
        letters.add(new LetterHelper(R.mipmap.widoa7,"",""));
        letters.add(new LetterHelper(R.mipmap.widoa8,"",""));
        letters.add(new LetterHelper(R.mipmap.widoa9,"",""));
    }

    private void fillPray(){
        letters = new ArrayList<>();
        letters.add(new LetterHelper(R.mipmap.pray1,"",""));
        letters.add(new LetterHelper(R.mipmap.pray2,"",""));
        letters.add(new LetterHelper(R.mipmap.pray3,"",""));
        letters.add(new LetterHelper(R.mipmap.pray4,"",""));
        letters.add(new LetterHelper(R.mipmap.pray5,"",""));
        letters.add(new LetterHelper(R.mipmap.pray6,"",""));
        letters.add(new LetterHelper(R.mipmap.pray7,"",""));
        letters.add(new LetterHelper(R.mipmap.pray8,"",""));
        letters.add(new LetterHelper(R.mipmap.pray9,"",""));
        letters.add(new LetterHelper(R.mipmap.pray10,"",""));
        letters.add(new LetterHelper(R.mipmap.pray11,"",""));
    }

    private void fillProphet(){
        letters = new ArrayList<>();
        letters.add(new LetterHelper(R.mipmap.prophet1,"",""));
        letters.add(new LetterHelper(R.mipmap.prophet2,"",""));
        letters.add(new LetterHelper(R.mipmap.prophet3,"",""));
        letters.add(new LetterHelper(R.mipmap.prophet4,"",""));
        letters.add(new LetterHelper(R.mipmap.prophet5,"",""));
        letters.add(new LetterHelper(R.mipmap.prophet6,"",""));
    }

    private void fillManars(){
        letters = new ArrayList<>();
        letters.add(new LetterHelper(R.mipmap.manar1,"",""));
        letters.add(new LetterHelper(R.mipmap.manar2,"",""));
        letters.add(new LetterHelper(R.mipmap.manar3,"",""));
        letters.add(new LetterHelper(R.mipmap.manar4,"",""));
        letters.add(new LetterHelper(R.mipmap.manar5,"",""));
        letters.add(new LetterHelper(R.mipmap.manar6,"",""));
        letters.add(new LetterHelper(R.mipmap.manar7,"",""));
    }


    private void setData(){
        imageView.setImageResource(letters.get(index).getSource());
        if(index == 0){
            previous.setVisibility(View.GONE);
        }else{
            previous.setVisibility(View.VISIBLE);
        }

        if(index == letters.size() -1){
            next.setVisibility(View.GONE);
        }else{
            next.setVisibility(View.VISIBLE);
        }
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Player.EventListener.super.onPlayerError(error);
        Log.e("Eror",error.getSourceException().getMessage());
    }
}