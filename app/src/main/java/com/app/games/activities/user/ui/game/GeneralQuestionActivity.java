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
import com.app.games.callback.UserLogCallback;
import com.app.games.controller.UserLogController;
import com.app.games.model.UserLog;
import com.app.games.utils.AnimalGameHelper;
import com.app.games.utils.AnimalHouseGameHelper;
import com.app.games.utils.FruitGameHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GeneralQuestionActivity extends AppCompatActivity {

    TextView question;
    ImageView answer1,answer2,answer3,answer4;

    int position = 0;


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


    private void addUserLog(int correct,int wrong){
        UserLog userLog = new UserLog();
        userLog.setUser(SharedData.user);;
        userLog.setQuestion(question.getText().toString());
        userLog.setKey("");
        userLog.setCorrect(correct);
        userLog.setWrong(wrong);
        userLog.setDate(new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Calendar.getInstance().getTime()));

        new UserLogController().Save(userLog, new UserLogCallback() {
            @Override
            public void onSuccess(ArrayList<UserLog> userLogs) {

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void showCorrectDialog(){
        addUserLog(1,0);
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
        addUserLog(0,1);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.wrong_answer,null);
        ArrayList<Integer> wrongImages = new ArrayList<>();
        wrongImages.add(R.mipmap.wrong);
        wrongImages.add(R.mipmap.wrong1);
        wrongImages.add(R.mipmap.wrong2);
        wrongImages.add(R.mipmap.wrong3);

        ArrayList<Integer> wrongMessages = new ArrayList<>();
        wrongMessages.add(R.string.wrong_answer);
        wrongMessages.add(R.string.wrong_answer1);
        wrongMessages.add(R.string.wrong_answer2);
        wrongMessages.add(R.string.wrong_answer3);

        builder.setView(view);
        builder.setCancelable(false);

        MaterialButton again = view.findViewById(R.id.again);
        MaterialButton cancel = view.findViewById(R.id.cancel);
        TextView text = view.findViewById(R.id.text);
        ImageView image = view.findViewById(R.id.image);

        text.setText(wrongMessages.get(position));
        image.setBackgroundResource(wrongImages.get(position));

        playAudio(R.raw.wrong);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                position++;
                if(position > 3){
                    position = 3;
                }
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