package com.app.games.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.app.games.R;
import com.app.games.adapters.ComicQuestionsAdapter;
import com.app.games.adapters.ShortVideoQuestionsAdapter;
import com.app.games.model.Comics;
import com.app.games.model.ShortVideo;
import com.app.games.utils.LocaleManager;
import com.app.games.utils.SharedData;

import java.util.ArrayList;
import java.util.Locale;

public class UserQuestionsActivity extends AppCompatActivity {

    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions);

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        if(SharedData.question_section == 1){
            //comics
            ArrayList<Comics.Question> questions = SharedData.comics.getQuestions();
            if(questions == null){
                questions = new ArrayList<>();
            }
            ArrayList<Comics.Question> filteredQuestions = new ArrayList<>();
            for (Comics.Question question : questions){
                if(question.getQuestionType().getKey().equals(SharedData.question_type.getKey())){
                    filteredQuestions.add(question);
                }
            }
            list.setAdapter(new ComicQuestionsAdapter(filteredQuestions,this));
        }else {
            //videos
            ArrayList<ShortVideo.Question> questions = SharedData.shortVideo.getQuestions();
            if(questions == null){
                questions = new ArrayList<>();
            }
            ArrayList<ShortVideo.Question> filteredQuestions = new ArrayList<>();
            for (ShortVideo.Question question : questions){
                if(question.getQuestionType().getKey().equals(SharedData.question_type.getKey())){
                    filteredQuestions.add(question);
                }
            }
            list.setAdapter(new ShortVideoQuestionsAdapter(filteredQuestions,this));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}