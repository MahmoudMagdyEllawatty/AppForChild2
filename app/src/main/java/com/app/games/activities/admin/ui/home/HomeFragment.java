package com.app.games.activities.admin.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.games.R;
import com.app.games.activities.admin.AddComicActivity;
import com.app.games.adapters.ComicsAdapter;
import com.app.games.callback.ComicsCallback;
import com.app.games.controller.ComicsController;
import com.app.games.model.Comics;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add;
    LoadingHelper loadingHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_comics, container, false);
        add = root.findViewById(R.id.add);
        recyclerView = root.findViewById(R.id.list);
        recyclerView .setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView .setItemAnimator(new DefaultItemAnimator());

        loadingHelper = new LoadingHelper(getActivity());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.comics = null;
                Intent intent = new Intent(getActivity(), AddComicActivity.class);
                startActivity(intent);
            }
        });

        loadData();

        return root;
    }





    private void loadData(){
        loadingHelper.showLoading("Loading");
        new ComicsController().getAll(new ComicsCallback() {
            @Override
            public void onSuccess(ArrayList<Comics> shops) {
                loadingHelper.dismissLoading();
                recyclerView.setAdapter(new ComicsAdapter(shops, getActivity()));
            }

            @Override
            public void onFail(String msg) {
                loadingHelper.dismissLoading();
            }
        });
    }
}