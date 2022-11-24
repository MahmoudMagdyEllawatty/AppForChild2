package com.app.games.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.callback.MemoryImageCallback;
import com.app.games.controller.MemoryImageController;
import com.app.games.model.MemoryImage;
import com.app.games.utils.LocaleManager;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class UserMemoryGameActivity extends AppCompatActivity {


    MaterialButton selectDifficulty,startGame;
    RecyclerView list;
    TextView score;

    int difficulty = 0;

    ArrayList<MemoryImage> memoryImages;
    ArrayList<MemoryImage> selectedMemoryImages;
    ArrayList<MemoryImage> correctMemoryImages;
    MemoryImage selectedFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_memory_game);

        selectDifficulty = findViewById(R.id.select_difficulty);
        startGame = findViewById(R.id.start_game);
        list = findViewById(R.id.list);
        score = findViewById(R.id.score);



        new MemoryImageController()
                .getAll(new MemoryImageCallback() {
                    @Override
                    public void onSuccess(ArrayList<MemoryImage> designs) {
                        memoryImages = designs;
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });


        selectDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDifficultyDialog();
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(difficulty == 0){
                    Toast.makeText(UserMemoryGameActivity.this, "Select Difficulty First", Toast.LENGTH_SHORT).show();
                    return;
                }

                score.setText("Score : 0");
                Collections.shuffle(memoryImages);

                selectedMemoryImages = new ArrayList<>();
                correctMemoryImages = new ArrayList<>();

                //select image from main images
                for (int i = 0; i < difficulty;i++){
                    selectedMemoryImages.add(memoryImages.get(i));
                }

                //duplicate images
                for (int i = 0; i < difficulty;i++){
                    selectedMemoryImages.add(selectedMemoryImages.get(i));
                }

                Collections.shuffle(selectedMemoryImages);
                list.setLayoutManager(new GridLayoutManager(UserMemoryGameActivity.this
                        ,difficulty < 8 ? difficulty : 4));
                list.setItemAnimator(new DefaultItemAnimator());
                list.setAdapter(new GameAdapter());



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

    private void selectDifficultyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.difficulty_selector,null,false);

        RadioButton easy = view.findViewById(R.id.easy);
        RadioButton medium = view.findViewById(R.id.medium);
        RadioButton hard = view.findViewById(R.id.hard);

        builder.setView(view);


        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(easy.isChecked()){
                    difficulty = 2;
                }else if(medium.isChecked()){
                    difficulty = 3;
                }else if(hard.isChecked()){
                    difficulty = 4;
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                difficulty = 0;
            }
        }).show();

    }


    private class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(UserMemoryGameActivity.this)
                    .inflate(R.layout.game_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            MemoryImage memoryImage = selectedMemoryImages.get(position);


            if(correctMemoryImages.contains(memoryImage)){ // make it not visible
                holder.imageView.setVisibility(View.INVISIBLE);
            }else{
                holder.imageView.setVisibility(View.VISIBLE);
                Picasso
                        .get()
                        .load(R.mipmap.ic_question)
                        .into(holder.imageView);
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Picasso
                            .get()
                            .load(memoryImage.getImage())
                            .into(holder.imageView);

                    Handler handler =new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(selectedFirst == null){
                                selectedFirst = memoryImage;
                            }else{
                                if(selectedFirst.getKey().equals(memoryImage.getKey())){
                                    correctMemoryImages.add(memoryImage);
                                    correctMemoryImages.add(memoryImage);

                                    int lastScore = Integer.parseInt(score.getText().toString().substring(
                                            score.getText().toString().indexOf(":")+2
                                    ));
                                    score.setText("Score : "+(lastScore +10));
                                    //reload adapter
                                    list.setAdapter(new GameAdapter());

                                    if(correctMemoryImages.size() == selectedMemoryImages.size()){
                                        AlertDialog.Builder builder
                                                 = new AlertDialog.Builder(UserMemoryGameActivity.this);
                                        builder.setMessage("Game Over! \n Your "+score.getText())
                                                .setCancelable(false)
                                                .setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent intent = new Intent(UserMemoryGameActivity.this,
                                                                UserMemoryGameActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(UserMemoryGameActivity.this,
                                                        UserMainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).show();
                                    }
                                }else{
                                    selectedFirst = null;
                                    //reload adapter
                                    list.setAdapter(new GameAdapter());
                                }
                            }
                        }
                    },1000);


                }
            });
        }

        @Override
        public int getItemCount() {
            return selectedMemoryImages.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            ImageView imageView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
            }
        }
    }
}