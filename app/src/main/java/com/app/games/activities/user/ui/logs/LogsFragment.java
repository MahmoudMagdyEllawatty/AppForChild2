package com.app.games.activities.user.ui.logs;

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
import com.app.games.adapters.UserLogsAdapter;
import com.app.games.callback.ShortVideosCallback;
import com.app.games.callback.UserLogCallback;
import com.app.games.controller.ShortVideoController;
import com.app.games.controller.UserLogController;
import com.app.games.model.ShortVideo;
import com.app.games.model.User;
import com.app.games.model.UserLog;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class LogsFragment extends Fragment {

    LoadingHelper loadingHelper;
    RecyclerView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_videos, container, false);
        list = root.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setItemAnimator(new DefaultItemAnimator());

        loadingHelper = new LoadingHelper(getActivity());
        loadingHelper.showLoading(getString(R.string.loading));

        new UserLogController()
                .getAll(new UserLogCallback() {
                    @Override
                    public void onSuccess(ArrayList<UserLog> shortVideos) {
                        loadingHelper.dismissLoading();


                        HashMap<String,List<UserLog>> AllUserLogs = new HashMap<String,List<UserLog>>();
                        for (UserLog log:shortVideos) {
                            if(!log.getUser().getKey().equals(SharedData.user.getKey())){
                                continue;
                            }
                            if(!AllUserLogs.containsKey(log.getDate())){
                                List<UserLog> logs = new ArrayList<>();
                                logs.add(log);

                                AllUserLogs.put(log.getDate(),logs);
                            }else{
                                Objects.requireNonNull(AllUserLogs.get(log.getDate())).add(log);
                            }
                        }

                        ArrayList<UserLog> userLogs = new ArrayList<>();
                        for (Map.Entry<String,List<UserLog>> log : AllUserLogs.entrySet()){

                            List<UserLog> savedLogs = log.getValue();
                            int correct = 0,wrong = 0;
                            for (UserLog log1:savedLogs) {
                                correct += log1.getCorrect();
                                wrong += log1.getWrong();
                            }

                            UserLog userLog = new UserLog();
                            userLog.setDate(log.getKey());;
                            userLog.setCorrect(correct);
                            userLog.setWrong(wrong);
                            userLogs.add(userLog);
                        }


                        //set adapter
                        list.setAdapter(new UserLogsAdapter(userLogs,getActivity()));
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