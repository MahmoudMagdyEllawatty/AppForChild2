package com.app.games.activities.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.adapters.QuestionTypesAdapter;
import com.app.games.callback.QuestionTypeCallback;
import com.app.games.controller.QuestionTypeController;
import com.app.games.model.QuestionType;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;

import java.util.ArrayList;

public class QuestionTypeActivity extends AppCompatActivity {

    ArrayList<QuestionType> questionTypes;
    LoadingHelper loadingHelper;
    RecyclerView recyclerView;


    Button newButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_type);


        newButton = findViewById(R.id.button);

        recyclerView = findViewById(R.id.list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadingHelper = new LoadingHelper(this);

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
                        recyclerView.setAdapter(new QuestionTypesAdapter(questionTypes,QuestionTypeActivity.this));
                    }

                    @Override
                    public void onFail(String msg) {
                        loadingHelper.dismissLoading();
                        Toast.makeText(QuestionTypeActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });


        (findViewById(R.id.add))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addType();
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


    private void addType(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Type");
        EditText editText = new EditText(this);
        editText.setHint("Enter Name");
        builder.setView(editText);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                QuestionType questionType = new QuestionType();
                questionType.setKey("");
                questionType.setSide(SharedData.question_section);
                questionType.setType(editText.getText().toString());

                new QuestionTypeController()
                        .Save(questionType, new QuestionTypeCallback() {
                            @Override
                            public void onSuccess(ArrayList<QuestionType> questionTypes) {

                            }

                            @Override
                            public void onFail(String msg) {

                            }
                        });

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}