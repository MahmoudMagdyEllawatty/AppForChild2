package com.app.games.activities.user.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.games.R;
import com.app.games.utils.LocaleManager;
import com.app.games.utils.SharedData;

import java.util.Locale;

public class MuslimDasboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muslim_dasboard);

        //go to comics
        findViewById(R.id.card_learn_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.muslim = 1;
                Intent intent = new Intent(MuslimDasboard.this,ViewMuslim.class);
                startActivity(intent);
            }
        });

        //go to videos
        findViewById(R.id.card_learn_animals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.muslim = 2;
                Intent intent = new Intent(MuslimDasboard.this,ViewMuslim.class);
                startActivity(intent);
            }
        });


        //go to games
        findViewById(R.id.card_learn_letters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.muslim = 3;
                Intent intent = new Intent(MuslimDasboard.this,ViewMuslim.class);
                startActivity(intent);
            }
        });

        //open muslim
        findViewById(R.id.card_learn_house).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.muslim = 4;
                Intent intent = new Intent(MuslimDasboard.this,ViewMuslim.class);
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

}