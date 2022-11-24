package com.app.games.activities.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.callback.ComicsCallback;
import com.app.games.callback.FileUploadCallback;
import com.app.games.callback.ShortVideosCallback;
import com.app.games.controller.ComicsController;
import com.app.games.controller.ImageController;
import com.app.games.controller.ShortVideoController;
import com.app.games.model.Comics;
import com.app.games.model.ShortVideo;
import com.app.games.utils.AnswerType;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddQuestionActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGES = 20;
    Button enterAnswer,save;
    TextInputEditText question;
    RecyclerView answersList;
    int correctAnswer = 1;
    ArrayList<AnswerType> answerTypes;
    Comics.Question comicQuestion;
    ShortVideo.Question videoQuestion;

    String answerURL = "";

    LoadingHelper loadingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question3);

        loadingHelper = new LoadingHelper(this);

        enterAnswer = findViewById(R.id.enter_answer);
        save = findViewById(R.id.save);

        question = findViewById(R.id.question);
        answersList = findViewById(R.id.answers_list);
        answersList.setLayoutManager(new LinearLayoutManager(this));
        answersList.setItemAnimator(new DefaultItemAnimator());


        if(SharedData.question_section == 1){
            //comics
            if(SharedData.comicQuestion == null){
                comicQuestion = new Comics.Question();
                answerTypes = new ArrayList<>();
                answersList.setAdapter(new AnswersAdapter());
            }else{
                comicQuestion = SharedData.comicQuestion;
                question.setText(comicQuestion.getQuestion());
                answerTypes = comicQuestion.getAnswerTypes();
                answersList.setAdapter(new AnswersAdapter());
            }
        }else{
            //short videos
            if(SharedData.shortVideoQuestion == null){
                videoQuestion = new ShortVideo.Question();
                answerTypes = new ArrayList<>();
                answersList.setAdapter(new AnswersAdapter());
            }else{
                videoQuestion = SharedData.shortVideoQuestion;
                question.setText(videoQuestion.getQuestion());
                answerTypes = videoQuestion.getAnswerTypes();
                answersList.setAdapter(new AnswersAdapter());
            }
        }


        enterAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnswerTypeDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerTypes.size() < 2){
                    Toast.makeText(AddQuestionActivity.this, "Please,Enter At least two answers", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(SharedData.question_section == 1){
                    comicQuestion.setAnswerTypes(answerTypes);
                    comicQuestion.setQuestionType(SharedData.question_type);
                    comicQuestion.setQuestion(question.getText().toString());

                    ArrayList<Comics.Question> questions = SharedData.comics.getQuestions();
                    if(questions == null){
                        questions = new ArrayList<>();
                    }
                    if(SharedData.comicQuestion == null)
                        questions.add(comicQuestion);
                    else{
                        questions.set(getQuestionIndex(),comicQuestion);
                    }
                    SharedData.comics.setQuestions(questions);

                    new ComicsController().Save(SharedData.comics, new ComicsCallback() {
                        @Override
                        public void onSuccess(ArrayList<Comics> categories) {
                            Toast.makeText(AddQuestionActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                        @Override
                        public void onFail(String msg) {

                        }
                    });
                }else{
                    videoQuestion.setAnswerTypes(answerTypes);
                    videoQuestion.setQuestionType(SharedData.question_type);
                    videoQuestion.setQuestion(question.getText().toString());

                    ArrayList<ShortVideo.Question> questions = SharedData.shortVideo.getQuestions();
                    if(questions == null){
                        questions = new ArrayList<>();
                    }
                    if(SharedData.shortVideoQuestion == null)
                        questions.add(videoQuestion);
                    else{
                        questions.set(getShortQuestionIndex(),videoQuestion);
                    }
                    SharedData.shortVideo.setQuestions(questions);

                    new ShortVideoController().Save(SharedData.shortVideo, new ShortVideosCallback() {
                        @Override
                        public void onSuccess(ArrayList<ShortVideo> categories) {
                            Toast.makeText(AddQuestionActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                        @Override
                        public void onFail(String msg) {

                        }
                    });
                }

            }
        });


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



    private int getQuestionIndex(){
        int i = 0;
        for (Comics.Question question : SharedData.comics.getQuestions()){
            if(question.getQuestion().equals(SharedData.comicQuestion.getQuestion())){
                return i;
            }
            i++;
        }
        return 0;
    }

    private int getShortQuestionIndex(){
        int i = 0;
        for (ShortVideo.Question question : SharedData.shortVideo.getQuestions()){
            if(question.getQuestion().equals(SharedData.shortVideoQuestion.getQuestion())){
                return i;
            }
            i++;
        }
        return 0;
    }

    private void selectAnswerTypeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.answer_item,null,false);

        builder.setView(view);

        RadioButton textChoice = view.findViewById(R.id.text_choice);
        RadioButton imageChoice = view.findViewById(R.id.image_choice);

        TextInputEditText answer = view.findViewById(R.id.answer);
        TextInputLayout layout = view.findViewById(R.id.layout);
        ImageView image = view.findViewById(R.id.image);

        Button save = view.findViewById(R.id.save);
        Button cancel = view.findViewById(R.id.cancel);

        AlertDialog dialog = builder.create();

        textChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    answer.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
                    image.setVisibility(View.GONE);
                }
            }
        });

        imageChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    answer.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkReadPermission()) {
                    pickDoc();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textChoice.isChecked()){
                    if(answer.getText() == null){
                        answer.setError("Required");
                        return;
                    }else if(answer.getText().toString().equals("")){
                        answer.setError("Required");
                        return;
                    }

                    AnswerType answerType = new AnswerType();
                    answerType.setCorrect(answerTypes.size() == 0 ? 1 : 0);
                    answerType.setAnswer(answer.getText().toString());
                    answerType.setImageURL("");

                    answerTypes.add(answerType);
                    answersList.setAdapter(new AnswersAdapter());

                }else{
                    if(answerURL.equals("")){
                        Toast.makeText(AddQuestionActivity.this, "Please,Select Image", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AnswerType answerType = new AnswerType();
                    answerType.setCorrect(answerTypes.size() == 0 ? 1 : 0);
                    answerType.setAnswer("");
                    answerType.setImageURL(answerURL);

                    answerTypes.add(answerType);
                    answersList.setAdapter(new AnswersAdapter());
                    answerURL = "";
                }
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkReadPermission(){
        int permissionWriteExternal = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionWriteExternal != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},2);
            return false;
        }else{
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickDoc();
            }
        }
    }

    private void pickDoc(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), RESULT_LOAD_IMAGES);
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGES && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            loadingHelper.showLoading("Uploading Image");
            Uri uri = data.getData();
            new ImageController().uploadImage(uri, new FileUploadCallback() {
                @Override
                public void onSuccess(String url) {
                   answerURL = url;
                   loadingHelper.dismissLoading();
                }

                @Override
                public void onFail(String msg) {
                    loadingHelper.dismissLoading();
                }
            });



        }
    }

    private class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(AddQuestionActivity.this)
                    .inflate(R.layout.answer_type,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

            holder.radio.setChecked(answerType.getCorrect() == 1);

            holder.radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        setCorrect(answerType);
                        notifyDataSetChanged();
                    }
                }
            });

        }

        private void setCorrect(AnswerType answerType){
            for (AnswerType answerType1 : answerTypes){
                if(answerType.getAnswer().equals(answerType1.getAnswer())
                    && answerType.getImageURL().equals(answerType1.getImageURL())){
                    answerType1.setCorrect(1);
                }else{
                    answerType1.setCorrect(0);
                }
            }
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