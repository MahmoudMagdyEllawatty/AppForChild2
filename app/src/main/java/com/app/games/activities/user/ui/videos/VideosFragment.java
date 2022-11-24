package com.app.games.activities.user.ui.videos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.games.R;
import com.app.games.adapters.ShortVideosAdapter;
import com.app.games.callback.ShortVideosCallback;
import com.app.games.controller.ShortVideoController;
import com.app.games.model.ShortVideo;
import com.app.games.utils.LoadingHelper;

import java.util.ArrayList;

public class VideosFragment extends Fragment {

    LoadingHelper loadingHelper;
    RecyclerView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_videos, container, false);
        list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setItemAnimator(new DefaultItemAnimator());

        loadingHelper = new LoadingHelper(getActivity());
        loadingHelper.showLoading("Loading Videos");

        new ShortVideoController()
                .getAll(new ShortVideosCallback() {
                    @Override
                    public void onSuccess(ArrayList<ShortVideo> shortVideos) {
                        loadingHelper.dismissLoading();
                        list.setAdapter(new ShortVideosAdapter(shortVideos,
                                getActivity()));
                    }

                    @Override
                    public void onFail(String msg) {
                        loadingHelper.dismissLoading();
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
        return root;
    }


}