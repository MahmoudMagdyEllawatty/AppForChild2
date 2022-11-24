package com.app.games.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.app.games.R;
import com.app.games.adapters.ComicQuestionsAdapter;
import com.app.games.adapters.ShortVideoQuestionsAdapter;
import com.app.games.model.Comics;
import com.app.games.model.ShortVideo;
import com.app.games.utils.SharedData;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions2);

        recyclerView = findViewById(R.id.list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        (findViewById(R.id.add))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(SharedData.question_section == 1){
                            SharedData.comicQuestion = null;
                        }else{
                            SharedData.shortVideoQuestion = null;
                        }

                        Intent intent = new Intent(QuestionsActivity.this,
                                AddQuestionActivity.class);
                        startActivity(intent);
                    }
                });

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
            recyclerView.setAdapter(new ComicQuestionsAdapter(filteredQuestions,this));
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
            recyclerView.setAdapter(new ShortVideoQuestionsAdapter(filteredQuestions,this));
        }
       
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


    @Override
    protected void onResume() {
        super.onResume();

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
            recyclerView.setAdapter(new ComicQuestionsAdapter(filteredQuestions,this));
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
            recyclerView.setAdapter(new ShortVideoQuestionsAdapter(filteredQuestions,this));
        }
    }
}