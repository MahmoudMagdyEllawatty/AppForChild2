package com.app.games.activities.user.ui.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.games.R;
import com.app.games.activities.user.UserMainActivity;
import com.app.games.utils.AnimalGameHelper;
import com.app.games.utils.AnimalHouseGameHelper;
import com.app.games.utils.FruitGameHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;

public class GeneralQuestionActivity extends AppCompatActivity {

    TextView question;
    ImageView answer1,answer2,answer3,answer4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_question);

        question = findViewById(R.id.question);
        answer1 = findViewById(R.id.answer_1);
        answer2 = findViewById(R.id.answer_2);
        answer3 = findViewById(R.id.answer_3);
        answer4 = findViewById(R.id.answer_4);

        if(SharedData.general_question == 1){
            setAnimal();
        }else if(SharedData.general_question == 2){
            setAnimalHouse();
        }else if(SharedData.general_question == 3) {
            setFruits();
        }
    }

    private void playAudio(int res){
        MediaPlayer mPlayer2;
        mPlayer2= MediaPlayer.create(this, res);
        mPlayer2.start();
    }

    private void showCorrectDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.correct_answer,null);

        builder.setView(view);
        builder.setCancelable(false);

        MaterialButton again = view.findViewById(R.id.again);
        MaterialButton cancel = view.findViewById(R.id.cancel);

        playAudio(R.raw.correct);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if(SharedData.general_question == 1){
                    setAnimal();
                }else if(SharedData.general_question == 2){
                    setAnimalHouse();
                }else if(SharedData.general_question == 3) {
                    setFruits();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(GeneralQuestionActivity.this, UserMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void showWrongDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.wrong_answer,null);

        builder.setView(view);
        builder.setCancelable(false);

        MaterialButton again = view.findViewById(R.id.again);
        MaterialButton cancel = view.findViewById(R.id.cancel);

        playAudio(R.raw.wrong);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(GeneralQuestionActivity.this,UserMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    private void setAnimal(){
        AnimalGameHelper animalGameHelper = new AnimalGameHelper();
        AnimalGameHelper.Question animalQuestion = animalGameHelper.getQuestion();

        question.setText(animalQuestion.getQuestion());

        answer1.setImageResource(animalQuestion.getAnswer1());
        answer2.setImageResource(animalQuestion.getAnswer2());
        answer3.setImageResource(animalQuestion.getAnswer3());
        answer4.setImageResource(animalQuestion.getAnswer4());

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 0){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });


        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 1){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 2){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 3){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });
    }

    private void setAnimalHouse(){
        AnimalHouseGameHelper animalGameHelper = new AnimalHouseGameHelper();
        AnimalHouseGameHelper.Question animalQuestion = animalGameHelper.getQuestion();

        question.setText(animalQuestion.getQuestion());

        answer1.setImageResource(animalQuestion.getAnswer1());
        answer2.setImageResource(animalQuestion.getAnswer2());
        answer3.setImageResource(animalQuestion.getAnswer3());
        answer4.setImageResource(animalQuestion.getAnswer4());

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 0){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });


        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 1){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 2){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 3){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });
    }

    private void setFruits(){
        FruitGameHelper animalGameHelper = new FruitGameHelper();
        FruitGameHelper.Question animalQuestion = animalGameHelper.getQuestion();

        question.setText(animalQuestion.getQuestion());

        answer1.setImageResource(animalQuestion.getAnswer1());
        answer2.setImageResource(animalQuestion.getAnswer2());
        answer3.setImageResource(animalQuestion.getAnswer3());
        answer4.setImageResource(animalQuestion.getAnswer4());

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 0){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });


        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 1){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 2){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animalQuestion.getCorrectAnswer() == 3){
                    showCorrectDialog();
                }else{
                    showWrongDialog();
                }
            }
        });
    }
}