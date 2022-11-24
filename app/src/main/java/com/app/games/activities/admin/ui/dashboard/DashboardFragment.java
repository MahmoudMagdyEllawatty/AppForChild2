package com.app.games.activities.admin.ui.dashboard;

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
import com.app.games.activities.admin.AddShortVideoActivity;
import com.app.games.adapters.ShortVideosAdapter;
import com.app.games.callback.ShortVideosCallback;
import com.app.games.controller.ShortVideoController;
import com.app.games.model.ShortVideo;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton add;
    LoadingHelper loadingHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        add = root.findViewById(R.id.add);
        recyclerView = root.findViewById(R.id.list);
        recyclerView .setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView .setItemAnimator(new DefaultItemAnimator());

        loadingHelper = new LoadingHelper(getActivity());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedData.shortVideo = null;
                Intent intent = new Intent(getActivity(), AddShortVideoActivity.class);
                startActivity(intent);

            }
        });

        loadData();

        return root;
    }


    private void loadData(){
        loadingHelper.showLoading("Loading");
        new ShortVideoController().getAll(new ShortVideosCallback() {
            @Override
            public void onSuccess(ArrayList<ShortVideo> shops) {
                loadingHelper.dismissLoading();
                recyclerView.setAdapter(new ShortVideosAdapter(shops,
                        getActivity()));
            }

            @Override
            public void onFail(String msg) {
                loadingHelper.dismissLoading();
            }
        });
    }
}