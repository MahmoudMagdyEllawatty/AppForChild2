package com.app.games.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.games.R;
import com.app.games.activities.admin.QuestionsActivity;
import com.app.games.activities.user.UserQuestionsActivity;
import com.app.games.model.UserLog;
import com.app.games.model.UserLog;
import com.app.games.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class UserLogsAdapter extends RecyclerView.Adapter<UserLogsAdapter.ViewHolder>{

    private ArrayList<UserLog> comics;
    private Context context;

    public UserLogsAdapter(ArrayList<UserLog> comics, Context context) {
        this.comics = comics;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.log_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserLog comics = this.comics.get(position);
        holder.date.setText(comics.getDate());
        holder.correct.setText(comics.getCorrect()+"");
        holder.wrong.setText(comics.getWrong()+"");
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView date,correct,wrong;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            date = itemView.findViewById(R.id.name);
            correct = itemView.findViewById(R.id.correct);
            wrong = itemView.findViewById(R.id.wrong);

        }
    }
}
