package com.app.games.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.adapters.QuestionTypesAdapter;
import com.app.games.callback.QuestionTypeCallback;
import com.app.games.controller.QuestionTypeController;
import com.app.games.model.QuestionType;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.LocaleManager;
import com.app.games.utils.SharedData;

import java.util.ArrayList;
import java.util.Locale;

public class UserQuestionTypes extends AppCompatActivity {

    ArrayList<QuestionType> questionTypes;
    RecyclerView recyclerView ;
    LoadingHelper loadingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_question_types);

        loadingHelper = new LoadingHelper(this);
        recyclerView = findViewById(R.id.list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadingHelper.showLoading("Loading Data");
        new QuestionTypeController()
                .getAll(new QuestionTypeCallback() {
                    @Override
                    public void onSuccess(ArrayList<QuestionType> questionTypes1) {
                        loadingHelper.dismissLoading();
                        questionTypes = new ArrayList<>();
                        for (QuestionType questionType : questionTypes1){
                            if(questionType.getSide() == SharedData.question_section){
                                questionTypes.add(questionType);
                            }
                        }

                        //loading adapter
                        recyclerView.setAdapter(new QuestionTypesAdapter(questionTypes, UserQuestionTypes.this));
                    }

                    @Override
                    public void onFail(String msg) {
                        loadingHelper.dismissLoading();
                        Toast.makeText(UserQuestionTypes.this, msg, Toast.LENGTH_SHORT).show();
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