package com.app.games.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.utils.AnswerType;
import com.app.games.utils.LocaleManager;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class UserQuestionActivity extends AppCompatActivity {

    TextView question;
    RecyclerView list;
    MaterialButton save;

    boolean isCorrect = false;
    AnswerType answerType1;
    ArrayList<AnswerType> answerTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_question);

        question = findViewById(R.id.question);
        list = findViewById(R.id.list);
        save = findViewById(R.id.save);

        if(SharedData.question_section == 1){
            question.setText(SharedData.comicQuestion.getQuestion());
            answerTypes = SharedData.comicQuestion.getAnswerTypes();
        }else{
            question.setText(SharedData.shortVideoQuestion.getQuestion());
            answerTypes = SharedData.shortVideoQuestion.getAnswerTypes();
        }

        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new GridLayoutManager(this,2));
        answerType1 = answerTypes.get(0);
        list.setAdapter(new AnswersAdapter());
        

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerType1 == null){
                    Toast.makeText(UserQuestionActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedData.correct = answerType1.getCorrect() == 1;
                Intent intent = new Intent(UserQuestionActivity.this,UserAnswerActivity.class);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

        @NonNull
        @Override
        public AnswersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(UserQuestionActivity.this)
                    .inflate(R.layout.answer_type,parent,false);
            return new AnswersAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AnswersAdapter.ViewHolder holder, int position) {
            AnswerType answerType = answerTypes.get(position);
            if(!answerType.getAnswer().equals("")){
                holder.text.setVisibility(View.VISIBLE);
                holder.image.setVisibility(View.GONE);

                holder.text.setText(answerType.getAnswer());
            }else{

                holder.text.setVisibility(View.GONE);
                holder.image.setVisibility(View.VISIBLE);

                Picasso.get()
                        .load(answerType.getImageURL())
                        .into(holder.image);
            }

            holder.radio.setChecked(answerType.getAnswer().equals(answerType1.getAnswer()));

            holder.radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        answerType1 = answerType;
                        if(answerType.getCorrect() == 1)
                            isCorrect = true;
                        else
                            isCorrect = false;
                        notifyDataSetChanged();
                    }
                }
            });

        }



        @Override
        public int getItemCount() {
            return answerTypes.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            private ImageView image;
            private TextView text;
            private RadioButton radio;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.image);
                text = itemView.findViewById(R.id.text);
                radio = itemView.findViewById(R.id.radio);
            }
        }
    }
}