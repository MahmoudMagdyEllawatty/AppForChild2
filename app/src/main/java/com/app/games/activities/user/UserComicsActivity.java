package com.app.games.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.adapters.ComicsAdapter;
import com.app.games.callback.ComicsCallback;
import com.app.games.controller.ComicsController;
import com.app.games.model.Comics;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.LocaleManager;

import java.util.ArrayList;
import java.util.Locale;

public class UserComicsActivity extends AppCompatActivity {

    RecyclerView list;
    LoadingHelper loadingHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comics);

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());

        loadingHelper = new LoadingHelper(this);
        loadingHelper.showLoading("Loading Comics");

        new ComicsController()
                .getAll(new ComicsCallback() {
                    @Override
                    public void onSuccess(ArrayList<Comics> categories) {
                        loadingHelper.dismissLoading();
                        list.setAdapter(new ComicsAdapter(categories,UserComicsActivity.this));
                    }

                    @Override
                    public void onFail(String msg) {
                        loadingHelper.dismissLoading();
                        Toast.makeText(UserComicsActivity.this, msg, Toast.LENGTH_SHORT).show();
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