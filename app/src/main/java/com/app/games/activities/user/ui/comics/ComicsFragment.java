package com.app.games.activities.user.ui.comics;

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
import com.app.games.adapters.ComicsAdapter;
import com.app.games.callback.ComicsCallback;
import com.app.games.controller.ComicsController;
import com.app.games.model.Comics;
import com.app.games.utils.LoadingHelper;

import java.util.ArrayList;

public class ComicsFragment extends Fragment {

    RecyclerView list;
    LoadingHelper loadingHelper;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_comics, container, false);
        list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setItemAnimator(new DefaultItemAnimator());

        loadingHelper = new LoadingHelper(getActivity());
        loadingHelper.showLoading("Loading Comics");

        new ComicsController()
                .getAll(new ComicsCallback() {
                    @Override
                    public void onSuccess(ArrayList<Comics> categories) {
                        loadingHelper.dismissLoading();
                        list.setAdapter(new ComicsAdapter(categories, getActivity()));
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