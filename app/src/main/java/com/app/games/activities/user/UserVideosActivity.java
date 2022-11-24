package com.app.games.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.adapters.ShortVideosAdapter;
import com.app.games.callback.ShortVideosCallback;
import com.app.games.controller.ShortVideoController;
import com.app.games.model.ShortVideo;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.LocaleManager;

import java.util.ArrayList;
import java.util.Locale;

public class UserVideosActivity extends AppCompatActivity {

    LoadingHelper loadingHelper;
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_videos);

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());

        loadingHelper = new LoadingHelper(this);
        loadingHelper.showLoading("Loading Videos");

        new ShortVideoController()
                .getAll(new ShortVideosCallback() {
                    @Override
                    public void onSuccess(ArrayList<ShortVideo> shortVideos) {
                        loadingHelper.dismissLoading();
                        list.setAdapter(new ShortVideosAdapter(shortVideos,UserVideosActivity.this));
                    }

                    @Override
                    public void onFail(String msg) {
                        loadingHelper.dismissLoading();
                        Toast.makeText(UserVideosActivity.this, msg, Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}