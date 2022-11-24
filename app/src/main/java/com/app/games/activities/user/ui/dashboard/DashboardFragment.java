package com.app.games.activities.user.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.app.games.R;
import com.app.games.activities.user.LearnLettersActivity;
import com.app.games.activities.user.LearnNumbersActivity;
import com.app.games.activities.user.MemoryGameActivity;
import com.app.games.activities.user.ui.game.GeneralQuestionActivity;
import com.app.games.utils.SharedData;

public class DashboardFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);


        //go to comics
        root.findViewById(R.id.card_learn_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.main_to_comics);
            }
        });

        //go to videos
        root.findViewById(R.id.card_learn_animals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.main_to_videos);
            }
        });


        //go to games
        root.findViewById(R.id.card_learn_letters).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.main_to_games);
            }
        });

        //open muslim
        root.findViewById(R.id.card_learn_house).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MuslimDasboard.class);
                startActivity(intent);
            }
        });


        return root;

    }


}