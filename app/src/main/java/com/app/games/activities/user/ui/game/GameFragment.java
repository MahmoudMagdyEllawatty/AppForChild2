package com.app.games.activities.user.ui.game;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.games.R;
import com.app.games.activities.user.LearnLettersActivity;
import com.app.games.activities.user.LearnNumbersActivity;
import com.app.games.activities.user.MemoryGameActivity;
import com.app.games.activities.user.UserMainActivity;
import com.app.games.callback.MemoryImageCallback;
import com.app.games.controller.MemoryImageController;
import com.app.games.model.MemoryImage;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class GameFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_memory_game, container, false);


        root.findViewById(R.id.card_memory_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MemoryGameActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.card_learn_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LearnNumbersActivity.class);
                startActivity(intent);
            }
        });


        root.findViewById(R.id.card_learn_letters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LearnLettersActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.card_learn_animals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.general_question = 1;
                Intent intent = new Intent(getActivity(), GeneralQuestionActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.card_learn_fruits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.general_question = 3;
                Intent intent = new Intent(getActivity(), GeneralQuestionActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.card_learn_house).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.general_question = 2;
                Intent intent = new Intent(getActivity(), GeneralQuestionActivity.class);
                startActivity(intent);
            }
        });



        return root;

    }


}